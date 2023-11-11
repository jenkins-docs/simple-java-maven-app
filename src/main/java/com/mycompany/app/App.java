package com.mycompany.app;
import java.util.Random;

/**
 * Hello world!
 */
public class App {

    public App() {}

    public static void main(String[] args) {
        System.out.println("Test");
    }

    public String getMessage(String message) {
        Random random = new Random();
        int rValue = random.nextInt();
        System.out.println(rValue);
        return message;
    }
}
