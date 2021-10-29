package com.mycompany.app;

/**
 * Hello world!
 */
public class App
{

<<<<<<< HEAD
    private final String message = "Hello Stranger";
=======

    private final String message = "Hello People";


>>>>>>> 0df063289004447a98bbf688d796ee274f4a4d06

    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    }

    private final String getMessage() {
        return message;
    }

}
