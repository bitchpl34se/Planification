
public class Vacation
{
	public int start;
	public int stop;
	public int breakStart;
	public int breakStop;
	public int cost;

	public Vacation()
	{
		start = stop = breakStart = breakStop = stop = 0;
	}
	
	public Vacation(final Vacation anotherVacation)
	{
		start = anotherVacation.start;
		stop = anotherVacation.stop;
		breakStart = anotherVacation.breakStart;
		breakStop = anotherVacation.breakStop;
		cost = anotherVacation.cost;
	}

	public boolean isWorkedAt (int t){
		return ((start <= t) && (t < breakStart)) || ((breakStart <= t) && (t < breakStop));
	}
	
	public boolean check()
	{
		return
			   (start <= breakStart)
			&& (breakStart <= breakStop)
			&& (breakStop <= stop);
	}
	
	public String visualizeVacation(final int nbPeriods)
	{
		String result = "";
		for(int period = 1; period < start; period++)
		{
			result += "    ";
		}
		for(int period = start; period <= stop; period++)
		{
			if(period >= breakStart && period <= breakStop)
			{
				result += "|~~~";
			}
			else
			{
				result += "|---";
			}
		}
		result += "|   ";
		for(int period = stop+2; period <= nbPeriods; period++)
		{
			result += "    ";
		}
		return result;
	}
}
