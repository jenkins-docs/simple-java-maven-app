package com.mycompany.app;

public class Score {

	int[] score;
	int latestChange;

	public Score() {
		score = new int[15];
		latestChange = 0;
		for(int i=0;i<score.length;i++) {
			score[i] = 0;
		}
	}

	/* 0 bonus
	 * 1 1or
	 * 2 2or
	 * 3 3or
	 * 4 4or
	 * 5 5or
	 * 6 6or
	 * 7 par
	 * 8 2par
	 * 9 triss
	 * 10 fyrtal
	 * 11 liten stege
	 * 12 stor steg
	 * 13 k�k
	 * 14 chans
	 * 15 yatzy  
	 */
	public void setScore(int row, int[] diceResult) {
		//the score depending on the row and the dice
		int newScore = 0;

		if(row==0) {
			newScore=50;
		}
		if(row<=6) {
			for(int i=0;i<diceResult.length;i++) {
				if(diceResult[i]==row)
					newScore += diceResult[i];
			}
		}
		else if(row==7 && isValid(row,diceResult)) {
			if(diceResult[4]==diceResult[3]) {
				newScore = diceResult[4]*2;
			} else if (diceResult[3]==diceResult[2]) {
				newScore = diceResult[3]*2;
			} else if (diceResult[2]==diceResult[1]) {
				newScore = diceResult[2]*2;
			} else {
				newScore = diceResult[1]*2;
			}
		}
		else if(row==8 && isValid(row,diceResult)) {
			for(int i=0;i<diceResult.length;i++) {
				newScore += diceResult[i];
			}			
			if(diceResult[4]!=diceResult[3]) {
				newScore -= diceResult[4];
			} else if (diceResult[0]!=diceResult[1]) {
				newScore -= diceResult[0];
			} else {
				newScore -= diceResult[2];
			}
		}
		else if(row==9 && isValid(row,diceResult)) {
			if(diceResult[4]==diceResult[2]) {
				newScore = diceResult[4]*3;
			} else if (diceResult[3]==diceResult[1]) {
				newScore = diceResult[3]*3;
			} else {
				newScore = diceResult[2]*3;
			}
		}
		else if(row==10 && isValid(row,diceResult)) {
			if(diceResult[4]==diceResult[1]) {
				newScore = diceResult[4]*4;
			} else {
				newScore = diceResult[3]*4;
			}
		}
		else if(row==15 && isValid(row,diceResult)) {
			newScore = 50;
		}
		else if(row>10 && isValid(row,diceResult)) {
			for(int i=0;i<diceResult.length;i++) {
				newScore += diceResult[i];
			}
		}
		
		latestChange = newScore;
		
		System.out.println("New score " +newScore);
		score[row] = newScore;
	}

	public int getScore(int row) {
		return score[row];
	}

	public boolean bonus() {
		int subTot = 0;
		for(int i=1; i<=6 ;i++) {
			subTot += score[i];
		}
		return subTot>=63;
	}

	public int getTotalScore() {
		int total=0;
		valuateBonus();
		for(int i=0; i<score.length;i++) {
			total += score[i];
		}
		return total;
	}

	private void valuateBonus() {
		if(bonus()) {
			score[0] = 50;
		}
	}

	private boolean isValid(int row,int[] diceResult) {
		boolean valid = true;

		if(row==7) {
			int count = 0;
			for(int i=0;i<diceResult.length-1;i++) {
				if(diceResult[i]==diceResult[i+1]) {
					count++;
				}
			}
			if(count==0) {
				valid = false;
			}
		}

		if(row==8) {
			int count = 0;
			for(int i=0;i<diceResult.length-1;i++) {
				if(diceResult[i]==diceResult[i+1]) {
					count++;
					i++;
				}
			}
			if(count!=2) {
				valid = false;
			}
		}
		if(row==9) {
			int count = 0;
			for(int i=0;i<diceResult.length-2;i++) {
				if(diceResult[i]==diceResult[i+2]) {
					count++;
				}
			}
			if(count==0) {
				valid = false;
			}
		}
		if(row==10) {
			int count = 0;
			for(int i=0;i<diceResult.length-3;i++) {
				if(diceResult[i]==diceResult[i+3]) {
					count++;
				}
			}
			if(count==0) {
				valid = false;
			}
		}


		//each is ascending
		if(row==11 || row==12) {
			if(row==11 && diceResult[0]!=1) {
				valid = false;
			} else if(row==12 && diceResult[0]!=2) {
				valid = false;
			} else {
				for(int i=1;i<diceResult.length;i++) {
					if(diceResult[i-1]!=(diceResult[i]-1)) {
						valid=false;
					}
				}
			}			
		}
		//two first are equal, two last are equal, middle is either equal to first or last
		else if(row==13) {
			if(diceResult[0]!=diceResult[1]) {
				valid=false;
			}
			if(diceResult[3]!=diceResult[4]) {
				valid=false;
			}
			if(!(diceResult[2]==diceResult[3] || diceResult[2]==diceResult[1])) {
				valid=false;
			}
		}
		//all the same
		else if(row==15) {
			for(int i=0;i<diceResult.length-1;i++) {
				if(diceResult[i]!=diceResult[i+1]) {
					valid=false;
				}
			}
		}
		return valid;
	}

	public int getLatestChange() {
		return latestChange;
	}
}
