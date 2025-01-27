package com.mycompany.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest2
{
    @Test
    public void testAppConstructor2() {
        App app1 = new App();
        App app2 = new App();
        assertEquals(app1.getMessage(), app2.getMessage());
    }

    @Test
    public void testAppMessage2()
    {
        App app = new App();
        assertEquals("Hello World!", app.getMessage());
    }
}
