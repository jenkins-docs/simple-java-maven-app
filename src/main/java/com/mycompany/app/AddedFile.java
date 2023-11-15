package com.mycompany.app;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Hello world!
 */
public class AddedFile {

    public AddedFile() {}

    public static void main(String[] args) {
        System.out.println("Test");
    }

    public String getMessage(String message) {
        Random random = new Random();
        int rValue = random.nextInt();
        System.out.println(rValue);
        
        SecureRandom sr = new SecureRandom();
        sr.setSeed(123456L);
        int v = sr.nextInt(32);
        System.out.println(v);

        return message;
    } 
}
