import java.util.ArrayList;

public class Patron
{
	private String name;
	private String patronID;
	private ArrayList<Copy> copiesOut;
	private ArrayList<Hold> holds; // Some holds for this patron

	public Patron(String n, String id)
	{
		this.name = n;
		this.patronID = id;
		this.copiesOut = new ArrayList<Copy>();
		this.holds = new ArrayList<Hold>();
	}

	@Override
	public boolean equals(Object p)
	{
		if (!(p instanceof Patron))
			return false;
		
		Patron p2 = (Patron)p;
		
		if (this.patronID.equals(p2.patronID))
				return true;
		else
			return false;
	}
	
	public ArrayList<Copy> getCopiesOut()
	{
		return this.copiesOut;
	}
	
	public ArrayList<Hold> getHolds()
	{
		return this.holds;
	}
	
	public void addHold(Hold h)
	{
		this.holds.add(h);
	}
	
	public void removeHold(Hold h)
	{
		int i = this.holds.indexOf(h);
		this.holds.remove(i);
	}
	public boolean checkCopyOut(Copy c)
	{
		c.setOutTo(this);
		copiesOut.add(c);
		return true;
	}

	public boolean checkCopyIn(Copy c)
	{
		c.setOutTo(null);
		if (copiesOut.contains(c))
		{
			copiesOut.remove(c);
			return true;
		}
		else
			return false;
	}
	
	public boolean noHolds()
	{
		return this.holds.isEmpty();
		
	}

	
	public String getPatronName()
	{
		return this.name;
	}
	
	public String getPatronID()
	{
		return this.patronID;
	}

	

}
