package com.application.mypetfx.utils.factory_method_example.provinces;

import java.util.ArrayList;
import java.util.List;

public class ToscanaProvinces implements ProvincesListBase {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Arezzo");
        list.add("Firenze");
        list.add("Grosseto");
        list.add("Livorno");
        list.add("Lucca");
        list.add("Massa-Carrara");
        list.add("Pisa");
        list.add("Pistoia");
        list.add("Prato");
        list.add("Siena");
        return list;
    }

}
