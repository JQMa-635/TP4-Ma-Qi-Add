import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EventLoggingTest
{
	private EventLogger eventLog;
	private String entry;
	private PatronStore pStore;
	
	
	
	@Before
	public void setup()
	{
		eventLog = new EventLogger();
		pStore = new PatronStore();
	}
	
	@After
	public void teardown()
	{
	}
	
	@Test
	public void testCopyCheckInLogging()
	{
		entry = "Copy "; //Logging starts
	
		String pid = "S000";
		Patron p = pStore.fetchPatron(pid);
		String copyID = "000";

		entry += copyID + " "; // Writing entry
		entry += "checked in from patron " + p.getPatronName() + ", ID: " + pid + "."; //Entry written;
		eventLog.addEntry(entry);// Logging ends.
		
		assertEquals("Copy 000 checked in from patron Eric, ID: S000.", eventLog.getLog().get(0));
		
	}
}