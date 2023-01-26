package com.application.mypetfx.utils.factory_method_example.provinces;

import java.util.ArrayList;
import java.util.List;

public class LiguriaProvinces implements ProvincesListBase {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Genova");
        list.add("Imperia");
        list.add("La Spezia");
        list.add("Savona");
        return list;
    }

}
