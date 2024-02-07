package com.mycompany.app;

/**
 * Hello world!
 */
public class App {


    private final String message = "Hello World!";
    private final String message2 = "Welcome to JEnkins web!";

    private static final String MESSAGE = "Hello World!";


    public App() {}

    public static void main(String[] args) {
        System.out.println(MESSAGE);
    }

    public String getMessage() {
        return MESSAGE;
    }
}
