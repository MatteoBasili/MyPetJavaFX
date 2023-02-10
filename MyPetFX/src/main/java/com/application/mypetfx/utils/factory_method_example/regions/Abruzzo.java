package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class Abruzzo implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Chieti");
        list.add("L'Aquila");
        list.add("Pescara");
        list.add("Teramo");
        return list;
    }

}
