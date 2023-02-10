package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class TrentinoAltoAdige implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Bolzano");
        list.add("Trento");
        return list;
    }

}
