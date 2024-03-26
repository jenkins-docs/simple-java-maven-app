package com.mycompany.app;

/**
 * Hello world!
 */
public class App {

    private static final String MESSAGE = "Hello World!";

    public App() {}

    public static void main(String[] args) {
        System.out.println(MESSAGE);
    }

    public String getMessage() {
        return MESSAGE;
    }

    public void printMessage() {
        System.out.println(MESSAGE);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    //Copilot: generate a method for fibonacci series
    public int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
