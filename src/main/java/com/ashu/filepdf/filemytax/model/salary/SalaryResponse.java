package com.ashu.filepdf.filemytax.model.salary;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class SalaryResponse {
    public String componentName;
    public Boolean isRequired;
    public Double componentAmount;
    public Boolean isProofRequired;
}
