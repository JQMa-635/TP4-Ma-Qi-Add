import java.util.ArrayList;
import java.util.Scanner;

public class Scratch
{

	public static void main(String[] args)
	{
		Hold hold1 = new Hold(1);
		Hold hold2 = new Hold(1);
		
		Patron p = new Patron("1", "2");
		
		p.addHold(hold1);
		System.out.println(p.getHolds().get(0).getHoldName().equals("Overdue"));
		System.out.println(p.getHolds().indexOf(hold2));
		Hold h = p.getHolds().get(0);
	
		p.getHolds().get(0);
		
		String b = "20.01";
		System.out.println(Double.parseDouble(b));
	}

}
