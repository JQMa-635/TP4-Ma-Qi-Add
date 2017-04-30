import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SellTest
{
	private PatronStore pStore;
	private CopyStore cStore;
	private SellController sell;
	
	@Before
	public void setup()
	{
		pStore = new PatronStore();
		cStore = new CopyStore();
		sell = new SellController(pStore, cStore);
	}
	
	@After
	public void teardown(){}
	
	@Test
	public void testEnterCopyNumberLargerThanStock()
	{	
		cStore.reduceCopiesCount(1);
		assertEquals(2, sell.enterCopyNumsToSell(4));
	}
	
	@Test
	public void testEnterCopyNumberWhenNoStock()
	{
		cStore.reduceCopiesCount(4); // Clear all stock first
		assertEquals(1, sell.enterCopyNumsToSell(1));
	}
	
	
}