package com.mycompany.app;

/**
 * Hello world!
 */
public class App {
     Logger logger = Logger.getLogger(getClass().getName());


    private static final String MESSAGE = "Hello World!";

    public App() {}

    public static void main(String[] args) {
        System.out.println(MESSAGE);
        logger.info("My Message");  // Compliant, output via logger
    }

    public String getMessage() {
        return MESSAGE;
    }
}
