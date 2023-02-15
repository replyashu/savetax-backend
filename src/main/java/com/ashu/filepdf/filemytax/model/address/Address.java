package com.ashu.filepdf.filemytax.model.address;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Address {
    public double latitude;
    public double longitude;
    public String continent;
    public String lookupSource;
    public String continentCode;
    public String localityLanguageRequested;
    public String city;
    public String countryName;
    public String countryCode;
    public String postcode;
    public String principalSubdivision;
    public String principalSubdivisionCode;
    public String plusCode;
    public String locality;
    public LocalityInfo localityInfo;
}
