package com.ashu.filepdf.filemytax.utils;

import org.springframework.data.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

public class NewRegimeComputation {

    double salary;
    boolean optedFor12Pf;
    int basicPer;
    int hrPer;
    int startIndex = 0;

    double base = 0.0;
    public NewRegimeComputation(double salary, boolean optedFor12Pf, int basic, int hr) {
        this.salary = salary;
        this.optedFor12Pf = optedFor12Pf;
        this.basicPer = basic;
        this.hrPer = hr;
    }

    public Map<String, Double> calculateAndReturnSalaryComponents() {
        Map<String, Double> result = new LinkedHashMap<>();
        double comp = salary;

        while (comp > 0) {
            Pair<String, Double> p = remainingSalary(comp);
            result.put(p.getFirst(), p.getSecond());
            comp -= p.getSecond();
        }

        return result;
    }

    private Pair<String, Double> remainingSalary(double sal) {
        switch (startIndex++) {
            // 0 -> Basic
            case 0:
                double basic = sal * (basicPer / 100.0);
                base = basic;
                return Pair.of("Basic", basic);
            // 1 -> HRA
            case 1:
                double hra = base * (hrPer / 100.0);
                return Pair.of("HRA", hra);
            case 2:
                if (optedFor12Pf) {
                    return Pair.of("PF", base * (12 / 100.0));
                } else {
                    return Pair.of("PF", 1800.0);
                }
            case 3:
                return Pair.of("Special", sal);
        }

        return Pair.of("All", 0.0);
    }

}
