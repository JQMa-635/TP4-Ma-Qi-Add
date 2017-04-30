import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CopiesTest
{
	private CopyStore cStore;
	private int cCount;
	
	@Before
	public void setup()
	{
		cStore = new CopyStore();
	}
	
	@After
	public void teardown()
	{
	}
	
	@Test
	public void testInitialCopiesCount()
	{
		cCount = 4;
		assertEquals(cCount, cStore.getCopiesCount());
	}
	
	@Test
	public void testReduceCopiesCount()
	{
		int remainingCopies = 3;
		cStore.reduceCopiesCount(1);
		assertEquals(remainingCopies, cStore.getCopiesCount());
	}
	
	@Test
	public void testCopyID()
	{
		Copy c = new Copy("001");
		assertEquals(c, cStore.fetchCopy("001"));
	}
}