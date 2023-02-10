package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class Sardegna implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Cagliari");
        list.add("Nuoro");
        list.add("Oristano");
        list.add("Sassari");
        list.add("Sud Sardegna");
        return list;
    }

}
