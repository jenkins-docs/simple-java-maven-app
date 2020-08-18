package com.mycompany.app;

public class Round {

	Dice[] dice;
	Player player;

	public Round(Player player) {
		this.player = player;
		dice = new Dice[5];
		for(int i=0;i<dice.length;i++) {
			dice[i] = new Dice();
			dice[i].roll();
		}
		printAllDice();
	}

	public void reRoll(int[] diceToRoll) {
		for(int i=0;i<dice.length;i++) {
			if(diceToRoll[i]==0)
				dice[i].roll();
		}
		printAllDice();
	}

	
	public void setResult(int row) {
		int[] diceResult = new int[dice.length];

		for(int i=0;i<diceResult.length;i++) {
			diceResult[i] = dice[i].getResult();
		}
		player.setScore(row, sortArray(diceResult));
	}


	private int[] sortArray(int[] array) {
		int temp;
		for (int i = 1; i < array.length; i++) {
			for (int j = i; j > 0; j--) {
				if (array[j] < array [j - 1]) {
					temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
				}
			}
		}
		return array;
	}
	private void printAllDice() {
		System.out.print("Result of roll: ");
		for(int i=0;i<4;i++) {
			System.out.print(dice[i].getResult() +", ");
		}
		System.out.println(dice[4].getResult());
	}
}
