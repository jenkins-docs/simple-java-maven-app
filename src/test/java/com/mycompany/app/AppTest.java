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
        App app = new App();
        assertEquals("Hello World!", app.getMessage());
    }

    @Test
    public void testP1GetterSetter() {
        App app = new App();
        app.setP1("value1");
        assertEquals("value1", app.getP1());
    }

    @Test
    public void testP2GetterSetter() {
        App app = new App();
        app.setP2("value2");
        assertEquals("value2", app.getP2());
    }

    @Test
    public void testP3GetterSetter() {
        App app = new App();
        app.setP3("value3");
        assertEquals("value3", app.getP3());
    }
}