package uk.ac.lancaster.prov.reasoning.test;

import uk.ac.lancaster.prov.reasoning.InferMissingClasses;
import junit.framework.TestCase;

public class testProvReasoning extends TestCase {
	
	InferMissingClasses inferencer = new InferMissingClasses();
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testIsProvClass() {
		try {
			assertTrue(inferencer.isProvClass("http://ns.taverna.org.uk/2011/run/97dc1a94-8639-476e-a6e6-9fe8abfacf56/", "http://www.w3.org/ns/prov#Activity"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	
	
	String Instance1 = ":taverna-prov-export";
	
	

}
