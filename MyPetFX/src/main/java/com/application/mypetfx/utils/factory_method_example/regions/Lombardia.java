package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class Lombardia implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Bergamo");
        list.add("Brescia");
        list.add("Como");
        list.add("Cremona");
        list.add("Lecco");
        list.add("Lodi");
        list.add("Mantova");
        list.add("Milano");
        list.add("Monza e Brianza");
        list.add("Pavia");
        list.add("Sondrio");
        list.add("Varese");
        return list;
    }

}
