package com.mycompany.app;

/**
 * Hello world! 2022
 */
public class App
{

    private final String message = "Hello World 2022!";

    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    }

    private final String getMessage() {
        return message;
    }

}
