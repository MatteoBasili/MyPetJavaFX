package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class Basilicata implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Matera");
        list.add("Potenza");
        return list;
    }

}
