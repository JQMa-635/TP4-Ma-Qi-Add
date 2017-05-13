import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PatronTest
{
	private Copy c;
	private Copy c2;
	private Patron p, p2;
	private Hold h;
	private UIController uiController;
	private InController inController;
	private CopyStore cs;
	private PatronStore ps;
	
	@Before
	public void setUp() throws Exception
	{
		p = new Patron("Uncle Bob","4747");
		p2 = new Patron("Uncle Sam", "2017");
		c = new Copy("001");
		c2 = new Copy("002");
		uiController = new UIController();
		inController = new InController(ps, cs);
		h = new Hold(1);
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testCheckCopyOut()
	{
		
		p.checkCopyOut(c);
		
		assertEquals("check if single copy out to patron", p, c.getOutTo());
		
		assertTrue("single copy out from patron's view", p.getCopiesOut().contains(c));

	}

	@Test
	public void testCheckSecondCopyOut()
	{
		
		p.checkCopyOut(c);
		p.checkCopyOut(c2);
		
		assertEquals("check if 2nd copy out to patron", p, c2.getOutTo());

	}

	@Test
	public void testCheckCopyIn()
	{
		p.checkCopyOut(c);
		p.checkCopyIn(c);
		assertTrue("Copies checked in from patron", c.getOutTo() == null);
		assertTrue("Patron doesn't have copy", p.getCopiesOut().isEmpty());
	}
	
	@Test
	public void testIsSamePatron()
	{
		assertTrue("If is same patron", p == p);
		assertTrue("If is different patron", !(p == p2));
	}
	
	@Test
	public void testGetCopiesOut()
	{
		p.checkCopyOut(c);
		assertEquals("Get the first out copy", p.getCopiesOut().get(0), c);
	}
	
	
	@Test
	public void testAddAndRemoveHold()
	{
		p.addHold(h);
		assertEquals("One hold", p.getHolds().get(0).getHoldName(), h.getHoldName());
		p.removeHold(h);
		assertTrue("If no holds", p.getHolds().isEmpty());
	}
	
	

}
