package com.mycompany.app;

/**
 * Hello world!
 */
public class App {

<<<<<<< HEAD
    private final String message = "Hello World123!";
=======
    private static final String MESSAGE = "Hello World!";
>>>>>>> c0293ed9625adf68934ae4b75973ad2c797060da

    public App() {}

    public static void main(String[] args) {
        System.out.println(MESSAGE);
    }

    public String getMessage() {
        return MESSAGE;
    }
}
