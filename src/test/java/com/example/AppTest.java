import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void testApp() {
        App app = new App();
        // Assuming App has a method called getGreeting that returns a greeting message
        String expectedGreeting = "Hello, World!";
        assertEquals(expectedGreeting, app.getGreeting());
    }
}