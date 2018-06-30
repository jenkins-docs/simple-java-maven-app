package com.mycompany.app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayInputStream inContent = new ByteArrayInputStream("Sun".getBytes());

    @Before
    public void setUpStreams() {
    	System.setIn(inContent);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testAppConstructor() {
        try {
            new App();
        } catch (Exception e) {
            fail("Construction failed.");
        }
    }

    @Test
    public void testAppMain()
    {
        App.main(null);
        String expected = "Hello World!" + System.getProperty("line.separator") + 
        		"Hello, Sun!" + System.getProperty("line.separator");
        assertEquals(expected, outContent.toString());
        
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

}
