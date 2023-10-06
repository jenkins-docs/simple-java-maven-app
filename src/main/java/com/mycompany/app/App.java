package com.mycompany.app;

import javax.swing.*;
import java.awt.*;

/**
 * Hello world!
 */
public class App {

    private static final String MESSAGE = "\tHello World!";

    public App() {}

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setSize(700,700);
        window.getContentPane().setBackground(Color.LIGHT_GRAY);
        window.setResizable(false);
        window.setLayout(null);

        JTextField message = new JTextField(MESSAGE);
        message.setBounds(210 , 210 , 250 , 250);
        message.setBackground(Color.DARK_GRAY);
        message.setForeground(Color.WHITE);
        message.setVisible(true);

        window.add(message);
        window.setVisible(true);
    }

}
