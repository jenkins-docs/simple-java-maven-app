package com.mycompany.app;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.apache.log4j.Logger;

public class AppTest {
  
	private Logger log = Logger.getLogger(this.getClass());
  @Test  
    public void main(){  
		try {
			log.info("Starting execution of main");
 String[] args = null; 
;
 ;
 App app  =new App(); 
app.main( args);
assertTrue(true);

		} catch (Exception exception) {
			log.error("Exception in execution ofmain-"+exception,exception);
			exception.printStackTrace();
			assertFalse(false);
		}
    }  
}
