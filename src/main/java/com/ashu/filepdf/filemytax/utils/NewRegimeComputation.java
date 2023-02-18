package com.ashu.filepdf.filemytax.utils;

import org.springframework.data.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

public class NewRegimeComputation {

    double salary;
    boolean optedFor12Pf;
    int hrPer;
    int startIndex = 0;

    double base = 0.0;
    public NewRegimeComputation(double salary, boolean optedFor12Pf, int hr) {
        this.salary = salary;
        this.optedFor12Pf = optedFor12Pf;
        this.hrPer = hr;
    }

    public Map<Map<String, Boolean>, Map<Double, Boolean>> calculateAndReturnSalaryComponents() {
        Map<Map<String, Boolean>, Map<Double, Boolean>> result = new LinkedHashMap<>();
        double comp = salary;
        Map<Double, Boolean> amountVal;

        while (comp > 0) {
            Map<Map<String, Boolean>, Pair<Double, Boolean>> map = remainingSalary(comp);
            amountVal = new LinkedHashMap<>();
            Map.Entry<Map<String, Boolean>, Pair<Double, Boolean>> entry = map.entrySet().iterator().next();
            Pair<Double, Boolean> salComponent = entry.getValue();
            amountVal.put(entry.getValue().getFirst(), entry.getValue().getSecond());
            result.put(entry.getKey(), amountVal);
            comp -= salComponent.getFirst();
        }

        Map<String, Boolean> cName = new LinkedHashMap<>();
        cName.put("Total", false);
        Map<Double, Boolean> cAmnt = new LinkedHashMap<>();
        cAmnt.put(salary, false);
        result.put(cName, cAmnt);

        return result;
    }

    private Map<Map<String, Boolean>, Pair<Double, Boolean>> remainingSalary(double sal) {
        Map<Map<String, Boolean>, Pair<Double, Boolean>> map = new LinkedHashMap<>();
        Map<String, Boolean> componentName = new LinkedHashMap<>();
        Pair<Double, Boolean> componentAmount;
        switch (startIndex++) {
            // 0 -> Basic
            case 0:
                double basic = sal * (50 / 100.0);
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
            // 3 -> Salary
            case 3:
                componentName.put("Special Allowance", true);
                componentAmount = Pair.of(sal, true);
                map.put(componentName, componentAmount);
                return map;
        }

        return map;
    }

}
