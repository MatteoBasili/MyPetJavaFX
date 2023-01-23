package com.application.mypetfx.utils.factory_method_example;

import java.util.ArrayList;
import java.util.List;

public class Regions {

    public List<String> createRegionList() {
        List<String> list = new ArrayList<>();
        list.add("Abruzzo");
        list.add("Basilicata");
        list.add("Calabria");
        list.add("Campania");
        list.add("Emilia-Romagna");
        list.add("Friuli-Venezia Giulia");
        list.add("Lazio");
        list.add("Liguria");
        list.add("Lombardia");
        list.add("Marche");
        list.add("Molise");
        list.add("Piemonte");
        list.add("Puglia");
        list.add("Sardegna");
        list.add("Sicilia");
        list.add("Toscana");
        list.add("Trentino-Alto Adige");
        list.add("Umbria");
        list.add("Valle d'Aosta");
        list.add("Veneto");
        return list;
    }

}
