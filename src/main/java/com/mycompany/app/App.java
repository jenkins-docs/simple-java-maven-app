package com.mycompany.app;

/**
 * Hello world!
 */

public class CryptoUtil 
{

    private static string password = "P@%5w0r]>"; /*Literal*/

    private static int ivLengthInBytes = 16;

    private static int keyLengthInBits = 128;
 
    private static int KeyLengthInBytes;
}

public class App
{

    private final String message = "Hello World!";

    public App() {}

    public static void main(String[] args) {
        System.out.println(new App().getMessage());
    }

    private final String getMessage() {
        return message;
    }

}
