package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class FriuliVeneziaGiulia implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Gorizia");
        list.add("Pordenone");
        list.add("Trieste");
        list.add("Udine");
        return list;
    }

}
