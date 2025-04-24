package com.mycompany.app;

/**
 * Hello world!
 */
public class App {

    private static final String MESSAGE = "Hello World!";

    public App() {}

    public static void main(String[] args) throws InterruptedException {
    while (true) {
        System.out.println(MESSAGE);
        Thread.sleep(5000); // Print every 5 seconds
    }
    }

    public String getMessage() {
        return MESSAGE;
    }
}
