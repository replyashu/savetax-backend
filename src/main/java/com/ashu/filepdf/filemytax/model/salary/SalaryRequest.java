package com.ashu.filepdf.filemytax.model.salary;

import lombok.Data;

@Data
public class SalaryRequest {
    public String userId;
    public Long ctc;
    public double longitude = 0;
    public double latitude = 0;
    public boolean optedForOldRegime;
    public boolean optedFor12Pf;
}
