package com.mycompany.app;

public class Menu {

	Player[] players;
	String[][] result;

	public Menu(Player[] playerTable) {
		players = new Player[playerTable.length];
		
		for(int i=0;i<playerTable.length;i++) {
			players[i] = playerTable[i];
		}
		
		result = new String[players.length][16];
		for(int i=0;i<players.length;i++) {
			for(int j=0;j<16;j++) {
				result[i][j] = "";
			}
		}
	}

	public void update(int playerId, int row, int change) {
		
		result[playerId][row] = Integer.toString(change);

		printTable();

	}

	private void printTable() {
		System.out.print("Players: \t");
		for(int i=0;i<players.length;i++) {
			System.out.print(players[i].getName() +"\t");
		}
		System.out.println();
		printResult(1,  "1: Ettor       ");
		printResult(2,  "2: Tv�or       ");
		printResult(3,  "3: Treor       ");
		printResult(4,  "4: Fyror       ");
		printResult(5,  "5: Femor       ");
		printResult(6,  "6: Sexor       ");
		printResult(0,  "Bonus          ");
		printResult(7,  "7: Par         ");
		printResult(8,  "8: Tv�par      ");
		printResult(9,  "9: Triss       ");
		printResult(10,  "10: Fyrtal      ");
		printResult(11, "11: Liten Stege");
		printResult(12, "12: Stor Stege ");
		printResult(13, "13: K�k        ");
		printResult(14, "14: Chans      ");
		printResult(15, "15: Yatzy      ");
	}
	
	private void printResult(int row, String rowText) {
		for(int i=0;i<players.length;i++) {
			if(i==0) {
				System.out.print(rowText);
			}	
			System.out.print("\t"+result[i][row] +"\t");
		}
		System.out.println();
	}
}
