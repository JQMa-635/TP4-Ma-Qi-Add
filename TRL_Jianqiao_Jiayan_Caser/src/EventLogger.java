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
	
	
	public ArrayList<String> getLog()
	{
		return this.eventLog;
	}
}
