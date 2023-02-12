package com.ashu.filepdf.filemytax.controller;

import com.ashu.filepdf.filemytax.model.salary.SalaryRequest;
import com.ashu.filepdf.filemytax.service.salary.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/salary/compute")
    public ResponseEntity<Boolean> computeUserSalary(@RequestBody SalaryRequest salaryRequest) {

        System.out.println(salaryRequest.userId);
        System.out.println(salaryRequest.ctc);
        System.out.println(salaryRequest.toString());
        System.out.println(salaryRequest.longitude);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
