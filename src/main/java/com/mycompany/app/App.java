package com.mycompany.app;

import String;

/**
 * Hello world!
 */
public class App
{

    private final String message = "Hello Wally World!";

    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    }

    private final String getMessage() {
        return message;
    }

}
