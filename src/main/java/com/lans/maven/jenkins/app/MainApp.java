package com.lans.maven.jenkins.app;

/**
 * Main App
 */
public class MainApp {

    private final String message = "Hello World!";

    public MainApp() {
    }

    public static void main(String[] args) {
        System.out.println(new MainApp().getMessage());
    }

    private final String getMessage() {
        return message;
    }

}
