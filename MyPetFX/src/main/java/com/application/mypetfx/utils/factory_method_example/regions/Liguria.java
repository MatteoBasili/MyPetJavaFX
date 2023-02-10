package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class Liguria implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Genova");
        list.add("Imperia");
        list.add("La Spezia");
        list.add("Savona");
        return list;
    }

}
