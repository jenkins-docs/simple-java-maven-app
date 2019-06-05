package com.mycompany.app;

//Java Pipeline!

public class App
{

    private final String message = "Java Pipelin!";

    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    }

    private final String getMessage() {
        return message;
    }

}
