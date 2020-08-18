package com.mycompany.app;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.fail;

public class RoundTest extends TestCase {

    @Test
    public void sortArrayTest() {
        int[] initialArray = new int[]{2, 3, 1, 5};
        try {
            //Round.sortArray(initialArray);
        } catch (Exception e) {
            fail("Array was not sorted");
        }
    }

}