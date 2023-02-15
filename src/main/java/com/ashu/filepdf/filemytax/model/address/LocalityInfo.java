package com.ashu.filepdf.filemytax.model.address;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LocalityInfo {
    public ArrayList<Administrative> administrative;
    public ArrayList<Informative> informative;
}
