package com.mycompany.app;

public class Player {

	String name;
	Score score;
	
	public Player(String name) {
		this.name = name;
		score = new Score();
	}
	
	public String getName() {
		return name;
	}
	
	public void setScore(int row, int[] dice) {
		score.setScore(row, dice);
	}
	
	public int getLatestChange() {
		
		return score.getLatestChange();
	}
	
}
