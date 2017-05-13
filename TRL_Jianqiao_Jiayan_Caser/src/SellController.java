class SellController
{
	private PatronStore pStore;
	private CopyStore cStore;

	private Patron patron;
	public SellController(PatronStore ps, CopyStore cs)
	{
		this.pStore = ps;
		this.cStore = cs;
	}


	public int enterCopyNumsToSell(int i)
	{
		if (cStore.getCopiesCount() == 0)
		{
			return 1;
		}
		else if (cStore.getCopiesCount() < i)
		{
			return 2;
		}
		else
		{
			return 0;
		}
	}

	public Patron enterPatronIDForSale(String patronID)
	{
		this.patron = pStore.fetchPatron(patronID);
		return this.patron;
	}

	
}
