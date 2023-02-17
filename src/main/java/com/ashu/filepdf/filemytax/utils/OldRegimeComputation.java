package com.ashu.filepdf.filemytax.utils;

import org.springframework.data.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

public class OldRegimeComputation {

    double salary;
    boolean optedFor12Pf;
    int basicPer;
    int hrPer;
    int startIndex = 0;

    double base = 0.0;
    public OldRegimeComputation(double salary, boolean optedFor12Pf, int hr) {
        this.salary = salary;
        this.optedFor12Pf = optedFor12Pf;
        this.hrPer = hr;
        this.basicPer = 50;
    }

    public Map<Map<String, Boolean>, Map<Double, Boolean>> calculateAndReturnSalaryComponents() {
        Map<Map<String, Boolean>, Map<Double, Boolean>> result = new LinkedHashMap<>();
        Map<Double, Boolean> amntValues;
        double comp = salary;

        while (comp > 0) {
            Map<Map<String, Boolean>, Pair<Double, Boolean>> map = remainingSalary(comp);
            amntValues = new LinkedHashMap<>();
            Map.Entry<Map<String, Boolean>, Pair<Double, Boolean>> entry = map.entrySet().iterator().next();
            Pair<Double, Boolean> salComponent = entry.getValue();
            amntValues.put(entry.getValue().getFirst(), entry.getValue().getSecond());
            result.put(entry.getKey(), amntValues);
            comp -= salComponent.getFirst();
        }

        return result;
    }

    private Map<Map<String, Boolean>, Pair<Double, Boolean>> remainingSalary(double sal) {
        Map<Map<String, Boolean>, Pair<Double, Boolean>> map = new LinkedHashMap<>();
        Map<String, Boolean> componentName = new LinkedHashMap<>();
        Pair<Double, Boolean> componentAmount;
        switch (startIndex++) {
            // 0 -> Basic
            case 0:
                double basic = sal * (basicPer / 100.0);
                base = basic;
                componentName.put("Basic", false);
                componentAmount = Pair.of(basic, false);
                map.put(componentName, componentAmount);
                return map;
            // 1 -> HRA
            case 1:
                double hra = base * (hrPer / 100.0);
                componentName.put("HRA", false);
                componentAmount = Pair.of(hra, true);
                map.put(componentName, componentAmount);
                return map;
            // 2 -> pf
            case 2:
                if (optedFor12Pf) {
                    componentName.put("PF", false);
                    componentAmount = Pair.of(base * (12 / 100.0), false);
                    map.put(componentName, componentAmount);
                    return map;
                } else {
                    componentName.put("PF", false);
                    componentAmount = Pair.of(1800.0 * 12, false);
                    map.put(componentName, componentAmount);
                    return map;
                }
            // 3 -> Health 8 - 10%
            case 3:
                double healthAllowance = base * (8 / 100.0);
                healthAllowance = Math.min(healthAllowance, 5000);
                componentName.put("Fitness Allowance", true);
                componentAmount = Pair.of(healthAllowance, true);
                map.put(componentName, componentAmount);
                return map;
            // 4 -> Health 8 - 10%
            case 4:
                double bookAllowance = base * (6 / 100.0);
                bookAllowance = Math.min(bookAllowance, 6500);
                componentName.put("Book Allowance", true);
                componentAmount = Pair.of(bookAllowance, true);
                map.put(componentName, componentAmount);
                return map;
            // 5 -> Telephone - 8 - 10%
            case 5:
                double telephoneAllowance = base * (6 / 100.0);
                telephoneAllowance = Math.min(telephoneAllowance, 6500);
                componentName.put("Telephone Allowance", true);
                componentAmount = Pair.of(telephoneAllowance, true);
                map.put(componentName, componentAmount);
                return map;
            // 6 -> Meal - 2200 pm
            case 6:
                double mealAllowance = 2200 * 12;
                componentName.put("Meal Allowance", true);
                componentAmount = Pair.of(mealAllowance, true);
                map.put(componentName, componentAmount);
                return map;
            //  7 -> Child Allowance
            case 7:
                componentName.put("Child Allowance", true);
                componentAmount = Pair.of(200.0, true);
                map.put(componentName, componentAmount);
                return map;
            // 8 -> Special Allowance
            case 8:
                componentName.put("Special Allowance", true);
                componentAmount = Pair.of(sal, true);
                map.put(componentName, componentAmount);
                return map;
        }

        return map;
    }
}
