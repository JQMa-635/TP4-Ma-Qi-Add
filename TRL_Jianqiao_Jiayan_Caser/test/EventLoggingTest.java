import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EventLoggingTest
{
	private EventLogger eventLog;
	private String entry;
	private PatronStore pStore;
	private UIController uiController;
	private Copy c;
	
	
	
	@Before
	public void setup()
	{
		c = new Copy("000");
		eventLog = new EventLogger();
		pStore = new PatronStore();
		uiController = new UIController();
	}
	
	@After
	public void teardown()
	{
	}
	
	@Test
	public void testCopyCheckInLogging()
	{
		uiController.eventHeader("2");; //Logging starts
	
		String pid = "S000";
		Patron p = pStore.fetchPatron(pid);
		
		uiController.eventBody(c);
		
		
		eventLog.addEntry(uiController.eventEnd("2", p));// Logging ends.
		
		assertEquals("Copy 000 checked in from patron Eric, ID: S000.", eventLog.getLog().get(0));
		
	}
}
