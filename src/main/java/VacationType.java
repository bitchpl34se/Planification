
import java.util.ArrayList;
import java.util.List;

public class VacationType
{
	public int rangeMin;
    public int rangeMax;
    public int startMin;
    public int stopMax;
    public int breakDurationMin;
    public int breakDurationMax;
    public int startToBreakMin;
    public int breakToStopMin;
    public int limit;
    public int cost;
    
    public Vacation[] enumerate()
    {
    	List< Vacation > list = new ArrayList< Vacation >();
    	Vacation vacation = new Vacation();
        vacation.cost = cost;
        for(int range = rangeMin; range <= rangeMax; ++range)
        for(int start = startMin; start + range <= stopMax + 1; ++start)
        {
            vacation.start = start;
            vacation.stop = start + range - 1;
            for(int breakDuration = breakDurationMin; breakDuration <= breakDurationMax; ++breakDuration)
            for(int breakStart = vacation.start + startToBreakMin; breakStart + breakDuration + breakToStopMin <= vacation.stop + 1; ++breakStart)
            {
                vacation.breakStart = breakStart;
                vacation.breakStop = breakStart + breakDuration - 1;
                list.add(new Vacation(vacation));
            }
        }
        Vacation[] result = new Vacation[list.size()];
        int typeIndex = 0;
        for(Vacation element : list)
        {
        	result[typeIndex++] = element;
        }
        return result;
    }
    
    public boolean check()
    {
    	return
    		   (rangeMin > 0)
    		&& (rangeMax > 0)
    		&& (startMin > 0)
    		&& (stopMax > 0)
    		&& (breakDurationMin > 0)
    		&& (breakDurationMax > 0)
    		&& (startToBreakMin > 0)
    		&& (breakToStopMin > 0)
    		&& (rangeMin <= rangeMax)
    		&& (startMin + rangeMax <= stopMax + 1)
    		&& (breakDurationMin <= breakDurationMax)
    		&& (startToBreakMin + breakDurationMin + breakToStopMin <= rangeMin)
    		&& (startToBreakMin + breakDurationMax + breakToStopMin <= rangeMax);
    }
}
