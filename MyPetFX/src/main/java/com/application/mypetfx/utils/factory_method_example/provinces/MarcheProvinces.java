package com.application.mypetfx.utils.factory_method_example.provinces;

import java.util.ArrayList;
import java.util.List;

public class MarcheProvinces implements ProvincesListBase {

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
