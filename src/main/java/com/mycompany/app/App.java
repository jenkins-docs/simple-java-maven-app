package com.mycompany.app;

/**
 * HieroNada
 */
public class App
{

    private final String message = "HieroNada";

    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    }

    private final String getMessage() {
        return message;
    }

}
