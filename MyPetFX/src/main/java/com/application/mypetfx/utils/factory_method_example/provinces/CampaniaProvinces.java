package com.application.mypetfx.utils.factory_method_example.provinces;

import java.util.ArrayList;
import java.util.List;

public class CampaniaProvinces implements ProvincesListBase {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Avellino");
        list.add("Benevento");
        list.add("Caserta");
        list.add("Napoli");
        list.add("Salerno");
        return list;
    }

}
