package com.ashu.filepdf.filemytax.controller;

import com.ashu.filepdf.filemytax.model.address.Address;
import com.ashu.filepdf.filemytax.model.salary.SalaryRequest;
import com.ashu.filepdf.filemytax.service.salary.SalaryService;
import com.ashu.filepdf.filemytax.utils.NewRegimeComputation;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/salary/compute")
    public ResponseEntity<Map<String, Double>> computeUserSalary(@RequestBody SalaryRequest salaryRequest) {

        double lon = salaryRequest.longitude;
        double lat = salaryRequest.latitude;

        String country = "Australia";
        String city = "others";
        Map<String, Double> map = new LinkedHashMap<>();

        if (lon != 0 || lat != 0) {
            String uri = "https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=" + lat +
                    "&longitude=" +  lon +"&localityLanguage=en";

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class);
            Address address = new Gson().fromJson(result, Address.class);
            country = address.countryCode;
            city = address.city;
            map = computeSalaryStructure(country, city, salaryRequest.ctc, salaryRequest.isOptedForOldRegime(), salaryRequest.optedFor12Pf);
        }

        System.out.println(map);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private Map<String, Double> computeSalaryStructure(String countryCode, String city, double salary, boolean isOptedForOldRegime,
                                                       boolean optedFor12Pf) {

        switch (countryCode) {
            case "IN":
            // Only for emulator
            case "US":
                return calculateSalaryBreakup(salary, isOptedForOldRegime, true, optedFor12Pf, city);
            case "AU":
                return calculateSalaryBreakup(salary, isOptedForOldRegime, false, optedFor12Pf, city);
        }

        return new LinkedHashMap<>();
    }

    private Map<String, Double> calculateSalaryBreakup(double salary, boolean isOptedForOldSalary, boolean isIndian,
                                                       boolean optedFor12Pf, String city) {
        Map<String, Double> result = new LinkedHashMap<>();
        if (isIndian) {
            // compute for old and new regime
            int hra = computeHRAPercentage(city);
            if (isOptedForOldSalary) {
                return result;
            } else {
                NewRegimeComputation newRegimeComputation = new NewRegimeComputation(salary, optedFor12Pf, 50, hra);
                return newRegimeComputation.calculateAndReturnSalaryComponents();
            }

        } else {
            // Australia
            return result;
        }
    }

    private Integer computeHRAPercentage(String city) {
        switch (city) {
            case "Kolkata":
            case "Mumbai":
            case "Delhi":
            case "Chennai":
                return 50;
            default: return 40;
        }
    }
}
