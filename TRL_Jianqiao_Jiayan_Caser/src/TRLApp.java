import java.util.InputMismatchException;
import java.util.Scanner;

public class TRLApp
{
	private static CopyStore cStore;
	private static PatronStore pStore;
	private static OutController outController;
	private static InController inController;
	private static SellController sellController;
	private static UIController uiController;
	private static EventLogger eventLog;

	
	private static String cmd;

	public static void main(String[] args) throws InterruptedException
	{
		cStore = new CopyStore();
		pStore = new PatronStore();

		outController = new OutController(pStore, cStore);
		inController = new InController(pStore, cStore);
		sellController = new SellController(pStore, cStore);
		uiController = new UIController();
		
		eventLog = new EventLogger();

		Scanner input = new Scanner(System.in);
		StdOut.println("Welcome to TRLApp.");
		uiController.showHelp();
		boolean quitting = false;

		while (!quitting)
		{
			uiController.printMenu();

			cmd = input.next();

			switch (cmd)
			{
			case "1":
				doCheckOut();
				break;
			case "2":
				doCheckIn();
				break;
			case "3":
				doDisplayPatronInfo();
				break;
			case "4":
				doSelling();
				break;
			case "5":
				doAddHold();
				break;
			case "6":
				doRemoveHold();
				break;
			case "7":
				doAddDueDate();
				break;
			case "8":
				displayEventLog();
				break;
			case "0":
				StdOut.println("exiting...");
				quitting = true;
				break;
			default:
				StdOut.println("Enter a valid command.");
				break;
			}
		}
		StdOut.println("Thank you for choosing TRLApp.");
	}

	/*
	 * Static methods
	 */
	
	private static void doCheckOut()
	{
		// Event logging.
		uiController.eventHeader(cmd);
		
		StdOut.println("Checking copies out...");
		StdOut.println("Enter Patron ID:");
		String pid = StdIn.readString();
		StdOut.println("You entered: " + pid);


		Patron p = outController.enterPatronForCheckOut(pid);
		if (!p.noHolds())
		{
			StdOut.println("****************ALERT!!! This patron has holds****************");
			StdOut.println("Checking out copies to patron: " + p);
		}

		while (true)
		{
			StdOut.println("Enter copyID to check out, 0 to finish:");
			String copyID = StdIn.readString();

			if (copyID.equals("0"))
				break;

			Copy c = null;
			c = outController.enterCopyGoingOut(copyID); // change
			if (c != null)								 // change
			{
				StdOut.println("Checking out copy: ");
				uiController.showCopy(c);
				
				uiController.eventBody(c);
			}
			else
				StdOut.println("Bad copy: reenter:");

		}

		outController.endOutTransaction();
		System.out.println("****Remember to add due dates for these books, otherwise you will be FINED!!!*****");

		eventLog.addEntry(uiController.eventEnd(cmd, p));// Logging ends
		
	}
	
	private static void doCheckIn()
	{
		uiController.eventHeader(cmd);
		StdOut.println("Checking copies in...");
		StdOut.println("Enter Patron ID:");
		String pid = StdIn.readString();

		StdOut.println("You entered: " + pid);


		Patron p = inController.enterPatronForCheckIn(pid);
		if (!p.noHolds())
		{
			StdOut.println("****************ALERT!!! This patron has holds****************");
			StdOut.println("Checking in copies to patron: " + p);
		}
		StdOut.println("Checking in copies from patron: " + p);

		while (true)
		{
			StdOut.println("Enter copyID to check in, 0 to finish:");
			String copyID = StdIn.readString();
			if (copyID.equals("0"))
				break;

			Copy c = inController.enterCopyGoingIn(copyID); // how to indicate
															// copy is already
															// checked out?

			if (c != null)
			{
				c = inController.enterCopyGoingIn(copyID);
				StdOut.println("Checking in copy: ");
				uiController.showCopy(c);
				uiController.eventBody(c);
			}
			else
				StdOut.println("Bad copy: reenter:");
		}

		inController.endInTransaction();

		StdOut.println("End of doCheckIn()");

		eventLog.addEntry(uiController.eventEnd(cmd, p));// Logging ends.
		
	}
	

	private static void doDisplayPatronInfo()
	{
		StdOut.println("Enter patron ID: ");
		String pid = StdIn.readString();

		Patron p = outController.getPatronInfo(pid);
		if (!p.noHolds())
		{
			StdOut.println("****************ALERT!!! This patron has holds****************");
			//StdOut.println("Checking out copies to patron: " + p);
		}
		uiController.showPatron(p);
	}
	
	/*
	 * Sell copies to patron. Copies are considered identical.
	 */
	
	private static void doSelling()
	{

		uiController.eventHeader(cmd);
		Scanner input = new Scanner(System.in);
		int copyNumbers = 0;
		StdOut.println("Selling copies...");
		StdOut.println("Enter Patron ID:");
		String pid = StdIn.readString();
		StdOut.println("You entered: " + pid);
		
		Patron p = sellController.enterPatronIDForSale(pid);
		if (!p.noHolds())
			StdOut.println("****************ALERT!!! This patron has holds****************");
		
		StdOut.println("Selling copies to patron: " + uiController.showPatron(p));
		int copiesSold = 0;

		while (true)
		{
			System.out.println("Enter the number of copies, 0 to finish: ");
			if (cStore.getCopiesCount() > 0)
				System.out.println(cStore.getCopiesCount() + " copies in stock.");
			else
			{
				System.out.println("Currently No stock.");
			}
			try
			{
				copyNumbers = input.nextInt();
			}
			catch (InputMismatchException e)
			{
				System.out.print("Invalid input. Enter again. ");	
				input.next();
			}
			if (copyNumbers == 0)
				break;
			else
			{
				int test = sellController.enterCopyNumsToSell(copyNumbers);
				
				if (test == 1)
					System.out.println("Currently no stock");
				else if (test == 2)
					System.out.println("Not enough copies in stock. Have only " + cStore.getCopiesCount() + " copies left.");
				else
				{
					cStore.reduceCopiesCount(copyNumbers);
					copiesSold += copyNumbers;
				}
			}
		}
		
		System.out.println("Sold " + copiesSold + ". Sale end. NO RETURNS!!!");
		
		uiController.eventBody(copiesSold);
		eventLog.addEntry(uiController.eventEnd(cmd, p));// Logging ends
	}
	

	private static void doAddHold()
	{

		uiController.eventHeader(cmd);
		StdOut.print("Enter patron ID:");
		String pid = StdIn.readString();
		Patron p = outController.enterPatronForCheckOut(pid);
		StdOut.print("Holds of this patron:");
		if (p.noHolds())
			System.out.print(" None");
		for (Hold h : p.getHolds())
			StdOut.print(h.getHoldName() + "   ");
		System.out.println();System.out.println();
		while (true)
		{
			StdOut.println("Enter hold type:1. Overdue  2. Bad behavior. 0 to finish");
			int i = StdIn.readInt();
			if (i == 0)
				break;
			Hold h = new Hold(i);
			p.addHold(h);

			uiController.eventBody(h);
		}

		eventLog.addEntry(uiController.eventEnd(cmd, p));// Logging ends
	}
	
	
	public static void doRemoveHold()
	{

		uiController.eventHeader(cmd);
		Hold hold = null;
		String fine = "0";
		System.out.println("Enter patron ID: ");
		
		String pid = StdIn.readString();
		Patron p = outController.enterPatronForCheckOut(pid); //Got lazy
		StdOut.print("Holds of this patron:");
		if (p.noHolds())
		{
			System.out.print("No holds on record, enter any key to exit");
			//String x = StdIn.readString();
		}
		else
		{
			for (Hold h : p.getHolds())
				StdOut.print(h.getHoldName() + "   \n\n");
			while (true)
			{
				StdOut.println("Enter hold type:1. Overdue  2. Bad behavior. 0 to finish");
				int holdType = StdIn.readInt();
				if (holdType == 0)
					break;
				for (int j = 0; j < p.getHolds().size(); j++)
				{
					
					if (p.getHolds().get(j).getHoldName().equals("Overdue")) //Find hold by name, way to long
						hold = p.getHolds().get(j);
				}
				p.removeHold(hold);
				System.out.print("Enter amout of fine to be paid: ");
				fine = StdIn.readString();
				System.out.println("Hold " + hold.getHoldName() + "removed. $" + Double.parseDouble(fine) + " paid.");

				uiController.eventBody(hold);
			}
		}

		eventLog.addEntry(uiController.eventEnd(p, fine));// Logging ends
	}
	
	
	public static void doAddDueDate()
	{

		uiController.eventHeader(cmd);
		String c, date;
		Copy copy;
		boolean exit = false;
		while(!exit)
		{
			System.out.print("Enter copy ID (0 to exit): ");
			
			c = StdIn.readString();
			if (c.equals("0"))
				exit = true;
			copy = cStore.fetchCopy(c);
			
			if (copy != null)
			{
				System.out.print("Enter due date (mmddyyyy); ");
				date = StdIn.readString();
				copy.setDueDate(date);

				uiController.eventBodyDueDate(copy);
			}
			else
				System.out.println("Bad copy, reenter.");
			
		}
		

		eventLog.addEntry(uiController.eventEnd());//Logging ends
	}
	
	public static void displayEventLog()
	{
		uiController.showEventLog(eventLog);
	}
	
}
