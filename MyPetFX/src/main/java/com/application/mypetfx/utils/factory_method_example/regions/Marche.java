package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class Marche implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Ancona");
        list.add("Ascoli Piceno");
        list.add("Fermo");
        list.add("Macerata");
        list.add("Pesaro e Urbino");
        return list;
    }

}
