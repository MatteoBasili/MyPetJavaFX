package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class Puglia implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Bari");
        list.add("Barletta-Andria-Trani");
        list.add("Brindisi");
        list.add("Foggia");
        list.add("Lecce");
        list.add("Taranto");
        return list;
    }

}
