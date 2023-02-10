package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class Lazio implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Frosinone");
        list.add("Latina");
        list.add("Rieti");
        list.add("Roma");
        list.add("Viterbo");
        return list;
    }

}
