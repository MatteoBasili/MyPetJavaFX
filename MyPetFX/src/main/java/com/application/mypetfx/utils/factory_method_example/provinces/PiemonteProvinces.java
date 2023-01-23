package com.application.mypetfx.utils.factory_method_example.provinces;

import java.util.ArrayList;
import java.util.List;

public class PiemonteProvinces implements ProvincesListBase {

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
