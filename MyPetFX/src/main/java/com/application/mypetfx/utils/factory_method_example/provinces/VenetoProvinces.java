package com.application.mypetfx.utils.factory_method_example.provinces;

import java.util.ArrayList;
import java.util.List;

public class VenetoProvinces implements ProvincesListBase {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Belluno");
        list.add("Padova");
        list.add("Rovigo");
        list.add("Treviso");
        list.add("Venezia");
        list.add("Verona");
        list.add("Vicenza");
        return list;
    }

}
