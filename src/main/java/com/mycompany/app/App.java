package com.mycompany.app;

/**
 * Hello world!
 */
public class App{

     
    private final String message = "Hello world!";//CLASS APP

    public App() { } //APP FOR HELLOWORLD

    public static void main(String[] args) {          //Main method
        System.out.println(new App().getMessage());
    }

    private final String getMessage() {
        return message;
    }

}
