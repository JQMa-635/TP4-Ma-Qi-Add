import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HoldTest
{
	private Patron p;
	private Hold overdue;
	private Hold badBehavior;
	@Before
	public void setup()
	{
		p = new Patron("John", "T01"); //John Doe, test subject 1
		overdue = new Hold(1);
		badBehavior = new Hold(2);
	}
	
	@After
	public void teardown(){}
	
	@Test
	public void testAddHold()
	{
		p.addHold(overdue);
		assertEquals(overdue, p.getHolds().get(0));
		p.addHold(badBehavior);
		assertEquals(badBehavior, p.getHolds().get(1));
	}
	
	@Test
	public void testRemoveHold()
	{
		p.addHold(overdue);
		p.addHold(badBehavior);
		
		p.removeHold(overdue);
		assertEquals(badBehavior, p.getHolds().get(0)); // overdue is removed, only badBeahvior left.
		p.removeHold(badBehavior);
		assertEquals(0, p.getHolds().size()); // No holds
	}
}