package com.application.mypetfx.utils.factory_method_example.regions;

import java.util.ArrayList;
import java.util.List;

public class Piemonte implements ProvincesBaseList {

    @Override
    public List<String> createProvincesList() {
        List<String> list = new ArrayList<>();
        list.add("Alessandria");
        list.add("Asti");
        list.add("Biella");
        list.add("Cuneo");
        list.add("Novara");
        list.add("Torino");
        list.add("Verbano-Cusio-Ossola");
        list.add("Vercelli");
        return list;
    }

}
