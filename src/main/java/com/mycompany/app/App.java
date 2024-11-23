package com.mycompany.app;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {

    private static final String MESSAGE = "Hello World!";

    public App() {}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer un texte :");
        String texte = scanner.nextLine();

        System.out.println("Vous avez entré : " + texte);
        scanner.close();
    }

    public String getMessage() {
        return MESSAGE;
    }
}
