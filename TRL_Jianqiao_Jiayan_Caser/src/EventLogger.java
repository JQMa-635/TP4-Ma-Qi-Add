import java.util.ArrayList;

class EventLogger
{
	private ArrayList<String> eventLog;
	
	public EventLogger()
	{
		this.eventLog = new ArrayList<String>();
	}
	
	public void addEntry(String s)
	{
		eventLog.add(s);
	}
	
	public void showLog()
	{
		for (int i = 0; i < eventLog.size(); i++)
		{
			System.out.println("Entry " + i + " :" + eventLog.get(i) +"\n");
		}
	}
	
	public ArrayList<String> getLog()
	{
		return this.eventLog;
	}
}