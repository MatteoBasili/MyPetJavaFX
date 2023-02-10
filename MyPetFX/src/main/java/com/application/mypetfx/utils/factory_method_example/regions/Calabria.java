package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class Calabria implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Catanzaro");
        list.add("Cosenza");
        list.add("Crotone");
        list.add("Reggio Calabria");
        list.add("Vibo Valentia");
        return list;
    }

}
