package com.mycompany.app;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App
{

    private final String message = "Hello World!";

    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
        
        Scanner scanner = new Scanner(System.in);
    	String name = scanner.nextLine();
    	
        System.out.println(new App().getMessageWithName(name));
        
        scanner.close();
    }

    private final String getMessage() {
        return message;
    }
    
    private final String getMessageWithName(String name) {
    	return "Hello, " + name + "!";
    }

}
