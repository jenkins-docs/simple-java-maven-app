package com.mycompany.app;

/**
 * Hello world!
 */
// commit test
public class App
{
    //Test
    private final String message = "Hello World!";

    public App() {}

    public static void main(String[] args) {
        String test = new App().getMessage();
        System.out.println(new App().getMessage());
        System.out.println(test + "\r\n");
    }

    private final String getMessage() {
        return message;
    }

}
