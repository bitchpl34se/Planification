package polytech.di5.lo.planification;

import java.util.ArrayList;
import java.util.List;

/** Represents a type of vacation */
public class VacationType
{
    /** The minimum number of periods from the beginning to the end of a vacation */
    public final int durationMin;
    /** The maximum number of periods from the beginning to the end of a vacation */
    public final int durationMax;
    /** The first period that can be worked */
    public final int startMin;
    /** The last period that can be worked */
    public final int stopMax;
    /** The minimum number of periods of the break in a vacation */
    public final int breakDurationMin;
    /** The maximum number of periods of the break in a vacation */
    public final int breakDurationMax;
    /** The minimum number of periods before the break in a vacation */
    public final int startToBreakMin;
    /** The minimum number of periods after the break in a vacation */
    public final int breakToStopMin;
    /** The maximum number of vacations that can be used */
    public final int limit;
    /** The cost of the vacations */
    public final int cost;
    
    /** All possible vacations that correspond to the vacation type */
    public final Vacation[] vacations;
    
    /** Constructor
     * @param durationMin The minimum number of periods from the beginning to the end of a vacation
     * @param durationMax The maximum number of periods from the beginning to the end of a vacation
     * @param startMin The first period that can be worked
     * @param stopMax The last period that can be worked
     * @param breakDurationMin The minimum number of periods of the break in a vacation
     * @param breakDurationMax The maximum number of periods of the break in a vacation
     * @param startToBreakMin The minimum number of periods before the break in a vacation
     * @param breakToStopMin The minimum number of periods after the break in a vacation
     * @param limit The maximum number of vacations that can be used
     * @param cost The cost of the vacations
     * @throws IllegalArgumentException If the parameters are not coherent
     */
    public VacationType(int durationMin, int durationMax, int startMin, int stopMax, int breakDurationMin, int breakDurationMax, int startToBreakMin, int breakToStopMin, int limit, int cost) throws IllegalArgumentException
    {
        this.durationMin = durationMin;
        this.durationMax = durationMax;
        this.startMin = startMin;
        this.stopMax = stopMax;
        this.breakDurationMin = breakDurationMin;
        this.breakDurationMax = breakDurationMax;
        this.startToBreakMin = startToBreakMin;
        this.breakToStopMin = breakToStopMin;
        this.limit = limit;
        this.cost = cost;
        if(!check())
        {
            throw new IllegalArgumentException("Invalid parameters [VacationType constructor]");
        }
        this.vacations = enumerate();
    }
    
    /** Copy constructor
     * @param anotherVacationType The type of vacation to copy
     */
    public VacationType(final VacationType anotherVacationType)
    {
        durationMin = anotherVacationType.durationMin;
        durationMax = anotherVacationType.durationMax;
        startMin = anotherVacationType.startMin;
        stopMax = anotherVacationType.stopMax;
        breakDurationMin = anotherVacationType.breakDurationMin;
        breakDurationMax = anotherVacationType.breakDurationMax;
        startToBreakMin = anotherVacationType.startToBreakMin;
        breakToStopMin = anotherVacationType.breakToStopMin;
        limit = anotherVacationType.limit;
        cost = anotherVacationType.cost;
        vacations = new Vacation[anotherVacationType.vacations.length];
        for(int vacationIndex = 0; vacationIndex < vacations.length; vacationIndex++)
        {
            vacations[vacationIndex] = new Vacation(anotherVacationType.vacations[vacationIndex]);
        }
    }

    /** Tests the coherence of a {@link VacationType} object
     * @return {@code true} if the parameters that define the vacation type are coherent, {@code false} otherwise
     */
    public boolean check()
    {
        return
               (0 <= durationMin) && (durationMin <= durationMax)
            && (0 <= startMin) && (startMin <= stopMax)
            && (startMin + durationMax - 1 <= stopMax)
            && (0 <= breakDurationMin) && (breakDurationMin <= breakDurationMax)
            && (0 <= startToBreakMin) && (0 <= breakToStopMin)
            && (startToBreakMin + breakDurationMin + breakToStopMin <= durationMin)
            && (startToBreakMin + breakDurationMax + breakToStopMin <= durationMax)
            && (0 < limit) && (0 < cost);
    }
    
    /** Enumerates all possible vacations that correspond to a vacation type
     * @return All possible vacations that correspond to the vacation type
     */
    private Vacation[] enumerate()
    {
        List< Vacation > vacationList = new ArrayList< Vacation >();
        for(int duration = durationMin; duration <= durationMax; duration++)
        for(int start = startMin; start + duration - 1 <= stopMax; start++)
        for(int breakDuration = breakDurationMin; breakDuration <= breakDurationMax; breakDuration++)
        for(int breakStart = start + startToBreakMin; breakStart + breakDuration + breakToStopMin - 1 <= start + duration - 1; breakStart++)
        {
            vacationList.add(new Vacation(start, start + duration - 1, breakStart, breakStart + breakDuration - 1));
        }
        return (Vacation[]) vacationList.toArray(new Vacation[vacationList.size()]);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        String string = "(";
        string += "durationMin = " + durationMin + ", ";
        string += "durationMax = " + durationMax + ", ";
        string += "startMin = " + (startMin + 1) + ", ";
        string += "stopMax = " + (stopMax + 1) + ", ";
        string += "breakDurationMin = " + breakDurationMin + ", ";
        string += "breakDurationMax = " + breakDurationMax + ", ";
        string += "startToBreakMin = " + startToBreakMin + ", ";
        string += "breakToStopMin = " + breakToStopMin + ", ";
        string += "limit = " + limit + ", ";
        string += "cost = " + cost + ")";
        return string;
    }
}