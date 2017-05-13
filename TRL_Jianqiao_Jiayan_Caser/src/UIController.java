class UIController
{
	private String event;
	
	public String showCopy(Copy c)
	{
		return "Copy w/id= " + c.getCopyID() + " out to: " + c.getOutTo();
	}
	
	public void showEventLog(EventLogger e)
	{
		for (int i = 0; i < e.getLog().size(); i++)
		{
			System.out.println("Entry " + i + " :" + e.getLog().get(i) +"\n");
		}
	}
	
	public String showPatron(Patron p)
	{
		String toReturn = "Patron w/ name: " + p.getPatronName() + ", id: " + p.getPatronID();

		if (p.getCopiesOut().isEmpty())
		{
			toReturn = toReturn + "\nNo copies checked out.\n";
		}
		else
		{
			toReturn = toReturn + "\nCopies checked out:";
			for (Copy copy : p.getCopiesOut())
			{	
				toReturn = toReturn + "\n\t" + copy.getCopyID() + "\n";
			}
		}
		
		if (p.getHolds().isEmpty())
			toReturn = toReturn + "\nNo hold.\n";
		else
		{
			toReturn = toReturn + "\nHolds:";
					for (Hold hold: p.getHolds())
						toReturn = toReturn + "\n\t" + hold.getHoldName() + "\n";
		}
		return toReturn;
	}
	
	public void eventHeader(String cmd)
	{
		this.event = "";
		if (cmd.equals("1") || cmd.equals("2") || cmd.equals("4")){
			event = "Copy ";
		}
		else if (cmd.equals("5") || cmd.equals("6"))
			event = "Hold ";
		else if (cmd.equals("7"))
			event = "Due date ";
	}
	

	public void eventBody(Copy c) // Overload
	{
			event += c.getCopyID() + " ";
	}
	
	public void eventBody(int copiesSold) // Overload
	{
			event += copiesSold + " ";
	}
	
	public void eventBody(Hold h) // Overload
	{
		event += h.getHoldName() + " ";
	}
	
	public void eventBodyDueDate(Copy c)
	{
		event += c.getDueDateInFormat() +" to copy " + c.getCopyID() +" ";
	}
	
	
	public String eventEnd(String cmd, Patron p) // Overload
	{
		if (cmd.equals("1"))
			event += "checked out to patron " + p.getPatronName() + ", ID: " + p.getPatronID() + ".";
		else if (cmd.equals("2"))
			event += "checked in from patron " + p.getPatronName() + ", ID: " + p.getPatronID() + ".";
		else if (cmd.equals("4"))
			event += " sold to patron " + p.getPatronName() +", ID: " + p.getPatronID() + ".";
		else if (cmd.equals("5"))
			event += "added to patron " + p.getPatronName() + ", ID: " + p.getPatronID() + ".";
		else if (cmd.equals("7"))
			event += ".";
		
		return this.event;
	}
	
	public String eventEnd() // Overload
	{
		event += ".";
		return event;
	}
	
	public String eventEnd(Patron p, String fine) // Overload
	{

		event += "removed from patron " + p.getPatronName() + ", ID: " + p.getPatronID() + ". Paid $" + fine + " fine.";
		return this.event;
	}
	
	public String getEvent()
	{
		return event;
	}
	
	public void showHelp()
	{
		System.out.println("\n| This is the help document for testing");
		System.out.println("| Student ID for testing:S000, S001, S002, S003");
		System.out.println("| Copy ID for testing: 001, 002, 003, 004");
		System.out.println("| Hold ID for testing(already included):1, 2");
		System.out.println("| These should be enough.");
		System.out.println("-------------------------------------------------\n");
	}
	
	public void printMenu()
	{
		StdOut.println("Select an option:\n");
		StdOut.println("1 => Start check out transaction");
		StdOut.println("2 => Start check in transaction");
		StdOut.println("3 => Display Patron Info");
		StdOut.println("4 => Sell copy to patron");
		StdOut.println("5 => Add hold to patron");
		StdOut.println("6 => Remove hold from patron");
		StdOut.println("7 => Add due date to cpoy");
		StdOut.println("8 => Display event log.");
		StdOut.println("0 => Quit");
	}
}


















