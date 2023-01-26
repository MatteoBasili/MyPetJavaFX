package com.application.mypetfx.utils.exceptions;

public class ConnectionFailedException extends Exception {
    public ConnectionFailedException() {
        super("Connection to server failed. Check your Internet access");
    }
}
