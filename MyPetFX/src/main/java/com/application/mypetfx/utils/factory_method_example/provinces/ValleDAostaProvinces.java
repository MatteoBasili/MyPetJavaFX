package com.application.mypetfx.utils.factory_method_example.provinces;

import java.util.ArrayList;
import java.util.List;

public class ValleDAostaProvinces implements ProvincesListBase {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Aosta/Aoste");
        return list;
    }

}
