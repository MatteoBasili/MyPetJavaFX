package com.application.mypetfx.utils.factory_method_example;

import com.application.mypetfx.utils.factory_method_example.provinces.*;

public class ProvincesFactory {

    public ProvincesListBase createProvinceBaseList(int type) throws Exception {
        return switch (type) {
            case 1 -> new AbruzzoProvinces();
            case 2 -> new BasilicataProvinces();
            case 3 -> new CalabriaProvinces();
            case 4 -> new CampaniaProvinces();
            case 5 -> new EmiliaRomagnaProvinces();
            case 6 -> new FriuliVeneziaGiuliaProvinces();
            case 7 -> new LazioProvinces();
            case 8 -> new LiguriaProvinces();
            case 9 -> new LombardiaProvinces();
            case 10 -> new MarcheProvinces();
            case 11 -> new MoliseProvinces();
            case 12 -> new PiemonteProvinces();
            case 13 -> new PugliaProvinces();
            case 14 -> new SardegnaProvinces();
            case 15 -> new SiciliaProvinces();
            case 16 -> new ToscanaProvinces();
            case 17 -> new TrentinoAltoAdigeProvinces();
            case 18 -> new UmbriaProvinces();
            case 19 -> new ValleDAostaProvinces();
            case 20 -> new VenetoProvinces();
            default -> throw new Exception("Invalid type: " + type);
        };
    }

}
