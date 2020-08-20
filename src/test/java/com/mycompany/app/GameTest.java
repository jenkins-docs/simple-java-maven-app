package com.mycompany.app;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class GameTest
{
	@Test
    public void testRoll() {
        int dice = 0;
        int high = 6;
        int low = 1;
        
        try {
        	Dice testDice = new Dice();
        	for (int i = 0; i < 1000; i++) {
        		testDice.roll();
            	dice = testDice.getResult();
            	if (dice > high) {
					fail("Value too high");
				}
            	else if (dice < low) {
					fail("Value too low");
				}
            	
			}
        } catch (Exception e) {
            fail("Dice not thrown :/");
        }
	}
	
    @Test
    public void sortArrayTest() {
        int[] initialArray = new int[]{2, 3, 1, 5, 4};
        int[] expected = new int[] {1, 2, 3 ,4 ,5};
        try {
        	Round round = new Round(null); //This will print "result of roll" but don't mind that
            initialArray = round.sortArray(initialArray);
            
            assertArrayEquals(expected, initialArray);
        } catch (Exception e) {
            fail("Array was not sorted");
        }
    }
    @Test
    public void testScoreOnes() {
    	
        int[] dices = new int[] {1, 1, 3 ,4 ,5};
        int result;
        int expected = 2;
        
        try {
        	Score score = new Score();
        	
          	score.setScore(1,dices);
          	result = score.getScore(1);
          	
          	assertEquals(expected, result);
          	
        } catch (Exception e) {
            fail("Array was not sorted");
        }
    }
    
}
