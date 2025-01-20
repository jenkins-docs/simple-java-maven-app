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
        assertTrue(app1.getMessage(), app2.getMessage()); //changed assertEquals to assertTrue
    }

    @Test
    public void testAppMessage()
    {
        App app = new App();
        assertFalse("Hello World!", app.getMessage()); //changed assertEquals to assertFalse
    }
}
