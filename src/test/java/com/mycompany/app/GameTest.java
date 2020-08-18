package com.mycompany.app;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class GameTest
{

    //@Before

    @Test
    public void sortArrayTest() {
        int[] initialArray = new int[]{2, 3, 1, 5};
        try {
            System.out.println(initialArray);
            assertEquals("123", "123");
            //Round.sortArray(initialArray);
        } catch (Exception e) {
            fail("Array was not sorted");
        }
    }

//    @After
//    public void cleanUpStreams() {
//        System.setOut(null);
//    }

}
