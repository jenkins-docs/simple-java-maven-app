package com.mycompany.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
public class App2Test
{
    @Test
    public void testAppConstructor2() {
        App2 app1 = new App2();
        App2 app2 = new App2();
        assertEquals(app1.getMessage(), app2.getMessage());
    }

    @Test
    public void testAppMessage2()
    {
        App2 app = new App2();
        assertEquals("Hello World!", app.getMessage());
    }
}
