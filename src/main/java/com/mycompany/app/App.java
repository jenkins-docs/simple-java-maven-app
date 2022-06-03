package com.mycompany.app;

/**
 * Hello world!
 */
// commit test
public class App
{
    //Test 123
    private final String message = "Hello World!";

    public App() {}

    public static void main(String[] args) {
        String test = new App().getMessage();
        System.out.println(new App().getMessage());
        System.out.println(test + System.getProperty("line.separator"));
        System.out.println("test multiple branch pipeline");
    }

    private final String getMessage() {
        return message;
    }

}
