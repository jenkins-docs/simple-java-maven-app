package com.mycompany.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void testAppConstructor() {
        assertDoesNotThrow(App::new);
    }

    @Test
    public void testAppMessage()
    {
        App app = new App();
        assertEquals("Hello World!", app.getMessage());
    }
}
