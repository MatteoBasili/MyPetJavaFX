package com.application.mypetfx.utils.input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    private InputValidator inputValidatorTest;

    @BeforeEach
    public void setUp() {
        inputValidatorTest = new InputValidator();
    }

    @Test
    public void isNullStringEmptyTest() {
        boolean output = inputValidatorTest.isEmpty(null);
        assertTrue(output);
    }

    @Test
    public void isVoidStringEmptyTest() {
        String voidStringTest = "";
        boolean output = inputValidatorTest.isEmpty(voidStringTest);
        assertTrue(output);
    }

    @Test
    public void isStringNonEmptyTest() {
        String voidStringTest = "Hello!!!";
        boolean output = inputValidatorTest.isEmpty(voidStringTest);
        assertFalse(output);
    }

    @Test
    public void isEmailInvalidTest() {
        String emailTest = "mario.rossi@gmailcom";   // The dot between gmail and com is missing
        boolean output = inputValidatorTest.isValidEmail(emailTest);
        assertFalse(output);
    }

    @Test
    public void isEmailValidTest() {
        String emailTest = "mario.rossi@gmail.com";
        boolean output = inputValidatorTest.isValidEmail(emailTest);
        assertTrue(output);
    }

    @Test
    public void isPasswordLessThanEightInvalidTest() {
        String passwordTest = "miao";
        boolean output = inputValidatorTest.isValidPassword(passwordTest);
        assertFalse(output);
    }

    @Test
    public void isPasswordMoreThanThirtyTwoInvalidTest() {
        String passwordTest = "Supercalifragilisticexpialidocious";
        boolean output = inputValidatorTest.isValidPassword(passwordTest);
        assertFalse(output);
    }

    @Test
    public void isPasswordBetweenEightAndThirtyTwoInvalidTest() {
        String passwordTest = "password";
        boolean output = inputValidatorTest.isValidPassword(passwordTest);
        assertTrue(output);
    }
}