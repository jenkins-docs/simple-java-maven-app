package com.ustglobal.app;

/**
 * Hello world!
 */
public class App
{

    private final String message = "Hello from UST!";

    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    }

    private String getMessage() {
        return message;
    }

}
