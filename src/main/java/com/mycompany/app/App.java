package com.mycompany.app;

/**
 * Hello world ,this is teja!
 */
public class App
{

    private final String message = "Hello Viswa!";

    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    }

    private final String getMessage() {
        return message;
    }

}
