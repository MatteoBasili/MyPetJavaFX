package com.application.mypetfx.utils.factory_method_example;

import com.application.mypetfx.utils.factory_method_example.provinces.ProvincesListBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProvincesFactoryTest {

    ProvincesFactory factoryTest;

    @BeforeEach
    void setUp() {
        factoryTest = new ProvincesFactory();
    }

    @Test
    void createProvinceBaseListLazioTest() throws Exception {
        List<String> provinces = Arrays.asList("Frosinone", "Latina", "Rieti", "Roma", "Viterbo");
        int lazioType = 7;
        ProvincesListBase provincesListBaseTest = factoryTest.createProvinceBaseList(lazioType);
        List<String> output = provincesListBaseTest.createProvincesList();
        assertEquals(provinces, output);
    }

    @Test
    void createWrongProvinceBaseListTest() throws Exception {
        List<String> provinces = Arrays.asList("Frosinone", "Latina", "Rieti", "Roma", "Viterbo");
        int regionTypeTest = 6;
        ProvincesListBase provincesListBaseTest = factoryTest.createProvinceBaseList(regionTypeTest);
        List<String> output = provincesListBaseTest.createProvincesList();
        assertThat(provinces, is(not(output)));
    }

    @Test
    void createProvinceBaseListExceptionErrorTest() {
        int regionTypeTest = 0;
        Exception exception = assertThrows(Exception.class, () -> factoryTest.createProvinceBaseList(regionTypeTest));
        assertEquals("Invalid type: " + regionTypeTest, exception.getMessage());
    }

}