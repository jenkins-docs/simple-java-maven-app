package com.mycompany.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void testAppConstructor() {
        App app1 = new App();
        App app2 = new App();
        assertEquals(app1.getMessage(), app2.getMessage());
    }

    @Test
    public void testAppMessage()
    {

        App.main(null);
        try {
            assertEquals("Hello World!" + System.getProperty("line.separator"), outContent.toString());
        } catch (AssertionError e) {
            fail("\"message\" is not \"Hello World!\"");
        }
    }
    
    @Test
    public void testAppMain_sub()
    {
        App.main(null);
        try {
            assertEquals("Welcome to Jenkins Webinar!" + System.getProperty("line.separator"), outContent.toString());
        } catch (AssertionError e) {
            fail("\"message\" is not \"Welcome to Jenkins Webinar!\"");
        }
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);

        App app = new App();
        assertEquals("Hello World!", app.getMessage());

    }
}
