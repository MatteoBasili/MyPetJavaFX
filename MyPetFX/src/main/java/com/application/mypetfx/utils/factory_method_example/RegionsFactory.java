package com.application.mypetfx.utils.factory_method_example;

import com.application.mypetfx.utils.factory_method_example.regions.*;

public class RegionsFactory {

    public ProvincesBaseList createProvinceBaseList(int type) throws Exception {
        return switch (type) {
            case 1 -> new Abruzzo();
            case 2 -> new Basilicata();
            case 3 -> new Calabria();
            case 4 -> new Campania();
            case 5 -> new EmiliaRomagna();
            case 6 -> new FriuliVeneziaGiulia();
            case 7 -> new Lazio();
            case 8 -> new Liguria();
            case 9 -> new Lombardia();
            case 10 -> new Marche();
            case 11 -> new Molise();
            case 12 -> new Piemonte();
            case 13 -> new Puglia();
            case 14 -> new Sardegna();
            case 15 -> new Sicilia();
            case 16 -> new Toscana();
            case 17 -> new TrentinoAltoAdige();
            case 18 -> new Umbria();
            case 19 -> new ValleDAosta();
            case 20 -> new Veneto();
            default -> throw new Exception("Invalid type: " + type);
        };
    }

}
