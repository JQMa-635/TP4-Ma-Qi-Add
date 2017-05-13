
public class Copy
{
	private String copyID;
	private Patron outTo;
	private String dueDate;

	public Copy(String cid)
	{
		this.copyID = cid;
	}



	public Patron getOutTo()
	{
		return outTo;
	}

	public void setOutTo(Patron outTo)
	{
		this.outTo = outTo;
	}

	public String getCopyID()
	{
		return copyID;
	}

//	public String toString()
//	{
//		return "Copy w/id= " + this.copyID + " out to: " + getOutTo();
//	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof Copy))
			return false;

		return ((Copy) o).getCopyID().equals(this.copyID); // yuck.
	}
	
	public String getDueDateInFormat()
	{
		String d = this.dueDate;
		return d.substring(0,2) + "/" + d.substring(2,4) + "/" + d.substring(4,8);
	}
	
	public String getDueDate()
	{
		return this.dueDate;
	}
	
	public void setDueDate(String s)
	{
		this.dueDate = s;
	}

}
