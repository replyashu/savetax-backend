package com.ashu.filepdf.filemytax.controller;

import com.ashu.filepdf.filemytax.model.address.Address;
import com.ashu.filepdf.filemytax.model.salary.SalaryRequest;
import com.ashu.filepdf.filemytax.model.salary.SalaryResponse;
import com.ashu.filepdf.filemytax.service.salary.SalaryService;
import com.ashu.filepdf.filemytax.utils.NewRegimeComputation;
import com.ashu.filepdf.filemytax.utils.OldRegimeComputation;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/salary/compute")
    public ResponseEntity<List<SalaryResponse>> computeUserSalary(@RequestBody SalaryRequest salaryRequest) {

        double lon = salaryRequest.longitude;
        double lat = salaryRequest.latitude;

        String country = "Australia";
        String city = "others";
        Map<Map<String, Boolean>, Map<Double, Boolean>> map = new LinkedHashMap<>();

        List<SalaryResponse> responses = new LinkedList<>();
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

        SalaryResponse salaryResponse;
        for (Map<String, Boolean> keys: map.keySet()) {
            salaryResponse = new SalaryResponse();
            Map.Entry<String, Boolean> k = keys.entrySet().iterator().next();
            salaryResponse.componentName = k.getKey();
            salaryResponse.isRequired = k.getValue();
            Map<Double, Boolean> vals = map.get(keys);
            Map.Entry<Double, Boolean> v = vals.entrySet().iterator().next();
            salaryResponse.componentAmount = v.getKey();
            salaryResponse.isProofRequired = v.getValue();
            responses.add(salaryResponse);
        }

        System.out.println(responses);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    private Map<Map<String, Boolean>, Map<Double, Boolean>> computeSalaryStructure(String countryCode, String city, double salary, boolean isOptedForOldRegime,
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

    private Map<Map<String, Boolean>, Map<Double, Boolean>> calculateSalaryBreakup(double salary, boolean isOptedForOldSalary, boolean isIndian,
                                                                                    boolean optedFor12Pf, String city) {
        Map<Map<String, Boolean>, Map<Double, Boolean>> result = new LinkedHashMap<>();
        if (isIndian) {
            // compute for old and new regime
            int hra = computeHRAPercentage(city);
            if (isOptedForOldSalary) {
                OldRegimeComputation oldRegimeComputation = new OldRegimeComputation(salary, optedFor12Pf, hra);
                return oldRegimeComputation.calculateAndReturnSalaryComponents();
            } else {
                NewRegimeComputation newRegimeComputation = new NewRegimeComputation(salary, optedFor12Pf, hra);
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
