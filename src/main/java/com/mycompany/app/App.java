package com.mycompany.app;

/**
 * Hello world!
 */
public class App
{

    private final String message = "Hello World!";

    public App() {}

    public static void main(String[] args) {
        
        System.out.println(new App().getMessage());
        for (int i = 1; i <= 10000; ++i) {
            System.out.println("printing...");
        }
    }

    private final String getMessage() {
        return message;
    }
}
