package com.mycompany.app;

/**
 * Hello world!
 */
public class App
{
	private final String template = "Hello, %s";
    private final String message = "Hello World!";
	
    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    	System.out.println(new App().getGreeting("zhaoshenglong"));
	}

    private final String getMessage() {
        return message;
    }

	private final String getGreeting(String somebody) {
		return String.format(template, somebody);
	}
}
