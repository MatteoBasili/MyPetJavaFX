package com.application.mypetfx.utils.factory_method_example.provinces;

import java.util.ArrayList;
import java.util.List;

public class SiciliaProvinces implements ProvincesListBase {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Agrigento");
        list.add("Caltanissetta");
        list.add("Catania");
        list.add("Enna");
        list.add("Messina");
        list.add("Palermo");
        list.add("Ragusa");
        list.add("Siracusa");
        list.add("Trapani");
        return list;
    }

}
