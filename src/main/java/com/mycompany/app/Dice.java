package com.mycompany.app;

public class Dice {

	private int result;

	public Dice() {

		result=0;
	}

	public void roll() {

		int max = 6; 
		int min = 1; 
		int range = max - min + 1; 
		result = (int)(Math.random() * range) + min;
	}

	public int getResult() {
		return result;
	}
}
