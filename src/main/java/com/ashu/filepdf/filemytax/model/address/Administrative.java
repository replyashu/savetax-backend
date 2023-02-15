package com.ashu.filepdf.filemytax.model.address;

import lombok.Data;

@Data
public class Administrative {
    public String name;
    public String description;
    public int order;
    public int adminLevel;
    public String isoCode;
    public String wikidataId;
    public int geonameId;
}
