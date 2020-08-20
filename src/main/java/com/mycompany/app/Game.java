package com.mycompany.app;

import java.util.Scanner;

public class Game {
	//test

	public static void main(String[] args) {
		
		System.out.println("Welcome to Yatzy!");

		System.out.println("Please enter number of players");
		
		Scanner scan = new Scanner(System.in);
		int nbrPlayers = 1; //= scan.nextInt();
		String[] playerNames = new String[nbrPlayers];
		//scan.nextLine();
		for(int i=0;i<nbrPlayers;i++) {
			System.out.print("Player " +(i+1) +"'s name:");
			playerNames[i] = "Player 1";//scan.nextLine();
			if(i<nbrPlayers-1)
				scan.nextLine();
		}
		
		Player[] player = new Player[nbrPlayers];
		
		for(int i=0;i<nbrPlayers;i++) {
			player[i] = new Player(playerNames[i]);
		}
		
		Menu menu = new Menu(player);
		boolean playing = true;
		int currentPlayer = 0;
		
		for(int roundNr = 0; roundNr < 5; roundNr++) {
			System.out.println("It is " +player[currentPlayer].getName() +"'s turn");
			System.out.println();
			
			Round round = new Round(player[currentPlayer]);
			int[] diceToRoll = new int[5];
			System.out.println("You have selected {0, 1, 1, 1, 0} to reroll and keep");
			//System.out.println("Keep: 1, Re-roll: 0");
			System.out.println("Use 1 to Keep and 0 to Reroll");
			for(int j=1;j<3;j++) {
				for(int i=0;i<5;i++) {
					System.out.print("Keep " +(i+1) +"? ");
					diceToRoll[i] = 0; //scan.nextInt();
				}
				round.reRoll(diceToRoll);
			}
			
			
			System.out.println("Which row do you want to use? (1-15)");
			int row = 2; //scan.nextInt();
			round.setResult(row);
			menu.update(currentPlayer,row,player[currentPlayer].getLatestChange());
			currentPlayer++;
			currentPlayer = currentPlayer%nbrPlayers;	
		}
		
		System.out.println("Thanks for playing!");
	}
}
