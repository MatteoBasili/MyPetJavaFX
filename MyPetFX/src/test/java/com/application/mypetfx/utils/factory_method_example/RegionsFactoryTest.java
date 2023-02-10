package com.application.mypetfx.utils.factory_method_example;

import com.application.mypetfx.utils.factory_method_example.regions.ProvincesBaseList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegionsFactoryTest {

    private RegionsFactory factoryTest;

    @BeforeEach
    public void setUp() {
        factoryTest = new RegionsFactory();
    }

    @Test
    public void createLazioProvinceBaseListTest() throws Exception {
        List<String> provinces = Arrays.asList("Frosinone", "Latina", "Rieti", "Roma", "Viterbo");
        int lazioType = 7;   // Code for Lazio region
        ProvincesBaseList provincesBaseListTest = factoryTest.createProvinceBaseList(lazioType);
        List<String> output = provincesBaseListTest.createProvincesList();
        assertEquals(provinces, output);
    }

    @Test
    public void createWrongProvinceBaseListTest() throws Exception {
        List<String> provinces = Arrays.asList("Frosinone", "Latina", "Rieti", "Roma", "Viterbo");
        int regionTypeTest = 6;
        ProvincesBaseList provincesBaseListTest = factoryTest.createProvinceBaseList(regionTypeTest);
        List<String> output = provincesBaseListTest.createProvincesList();
        assertThat(provinces, is(not(output)));
    }

    @Test
    public void throwExceptionErrorWhenCreateProvinceBaseListTest() {
        int regionTypeTest = 0;   // No region is associated to this code
        Exception exception = assertThrows(Exception.class, () -> factoryTest.createProvinceBaseList(regionTypeTest));
        assertEquals("Invalid type: " + regionTypeTest, exception.getMessage());
    }

}