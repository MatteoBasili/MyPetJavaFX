package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class EmiliaRomagna implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Bologna");
        list.add("Ferrara");
        list.add("Forlì-Cesena");
        list.add("Modena");
        list.add("Parma");
        list.add("Piacenza");
        list.add("Ravenna");
        list.add("Reggio Emilia");
        list.add("Rimini");
        return list;
    }

}
