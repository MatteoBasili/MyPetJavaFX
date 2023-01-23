package com.application.mypetfx.utils.factory_method_example.provinces;

import java.util.ArrayList;
import java.util.List;

public class PugliaProvinces implements ProvincesListBase {

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
