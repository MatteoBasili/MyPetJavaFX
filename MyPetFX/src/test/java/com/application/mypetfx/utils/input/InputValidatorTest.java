package com.application.mypetfx.utils.input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    private InputValidator validatorTest;

    @BeforeEach
    public void setUp() {
        validatorTest = new InputValidator();
    }

    @Test
    public void isNullStringEmptyTest() {
        boolean output = validatorTest.isEmpty(null);
        assertTrue(output);
    }

    @Test
    public void isVoidStringEmptyTest() {
        String voidStringTest = "";
        boolean output = validatorTest.isEmpty(voidStringTest);
        assertTrue(output);
    }

    @Test
    public void isStringNonEmptyTest() {
        String voidStringTest = "Hello!!!";
        boolean output = validatorTest.isEmpty(voidStringTest);
        assertFalse(output);
    }

    @Test
    public void isEmailInvalidTest() {
        String testString = "mario.rossi@gmailcom";   // The dot between gmail and com is missing
        boolean output = validatorTest.isValidEmail(testString);
        assertFalse(output);
    }

    @Test
    public void isEmailValidTest() {
        String testString = "mario.rossi@gmail.com";
        boolean output = validatorTest.isValidEmail(testString);
        assertTrue(output);
    }

    @Test
    public void isPasswordLessThanEightInvalidTest() {
        String testString = "miao";
        boolean output = validatorTest.isValidPassword(testString);
        assertFalse(output);
    }

    @Test
    public void isPasswordMoreThanThirtyTwoInvalidTest() {
        String testString = "Supercalifragilisticexpialidocious";
        boolean output = validatorTest.isValidPassword(testString);
        assertFalse(output);
    }

    @Test
    public void isPasswordBetweenEightAndThirtyTwoInvalidTest() {
        String testString = "password";
        boolean output = validatorTest.isValidPassword(testString);
        assertTrue(output);
    }
}