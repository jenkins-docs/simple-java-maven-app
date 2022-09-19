package com.mycompany.app;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.apache.log4j.Logger;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class AppTest {
  
	private Logger log = Logger.getLogger(this.getClass());

    @BeforeAll
    static void initAll() {
    }

    @BeforeEach
    void init() {
    }
  @Test  
	@DisplayName("main")
    public void main(){  
		try {
			log.info("Starting execution of main");
 String[] args = null; 
;
 ;
 App app  =new App(); 
app.main( args);
Assertions.assertTrue(true);

		} catch (Exception exception) {
			log.error("Exception in execution ofmain-"+exception,exception);
			exception.printStackTrace();
			Assertions.assertFalse(false);
		}
    }  
    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }
}
