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

    // create a method that returns a string
    public String returnMessage() {
        return MESSAGE;
    }

    // create a method to get multiple messages
    // Copilot
    public String getMultipleMessages(String message1, String message2) {
        return message1 + " " + message2;
    }
}
