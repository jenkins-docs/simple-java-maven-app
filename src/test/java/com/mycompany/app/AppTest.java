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
        assertEquals(app1.getMessage("Test"), app2.getMessage("Test"));
    }

    @Test
    public void testAppMessage()
    {
        App app = new App();
        assertEquals("Hello World!", app.getMessage("Hello World!"));
        assertEquals("Hello World!", app.getAnotherMessage("Hello World!"));
    }

     @Test
    public void testAppMessagew()
    {
        App app = new App();
        assertEquals("Hello World!", app.getMessage("Hello World!"));
        assertEquals("Hello World!", app.getAnotherMessage("Hello World!"));
    }
}
