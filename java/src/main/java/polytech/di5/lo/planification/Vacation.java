package polytech.di5.lo.planification;

/** Represents a vacation */
public class Vacation
{
    /** The period when the vacation starts */
    private final int start;
    /** The period when the vacation stops */
    private final int stop;
    /** The period when the break of the vacation starts */
    private final int breakStart;
    /** The period when the break of the vacation stops */
    private final int breakStop;
    
    /** The number of periods used to print {@link Vacation} objects */
    public static int numberOfPeriods = 0;
    
    /** Constructor
     * @param start The period when the vacation starts
     * @param stop The period when the vacation stops
     * @param breakStart The period when the break of the vacation starts
     * @param breakStop The period when the break of the vacation stops
     * @throws IllegalArgumentException If the periods are not coherent
     */
    public Vacation(int start, int stop, int breakStart, int breakStop) throws IllegalArgumentException
    {
        this.start = start;
        this.stop = stop;
        this.breakStart = breakStart;
        this.breakStop = breakStop;
        if(!check())
        {
            throw new IllegalArgumentException("Invalid periods [Vacation constructor]");
        }
    }
    
    /** Copy constructor
     * @param anotherVacation The vacation to copy
     */
    public Vacation(final Vacation anotherVacation)
    {
	start = anotherVacation.start;
	stop = anotherVacation.stop;
	breakStart = anotherVacation.breakStart;
	breakStop = anotherVacation.breakStop;
    }
    
    /** Tests the coherence of a {@link Vacation} object
     * @return {@code true} if the periods that define the vacation are coherent, {@code false} otherwise
     */
    public boolean check()
    {
        return (0 <= start) && (start <= breakStart) && (breakStart <= breakStop) && (breakStop <= stop);
    }
    
    /** Identifies which periods are worked in a vacation
     * @param period A period
     * @return {@code true} if the period is worked in the vacation, {@code false} otherwise
     */
    public boolean isWorkedAt(int period)
    {
        return ((start <= period) && (period < breakStart)) || ((breakStop < period) && (period <= stop));
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public final String toString()
    {
        String string = "";
        int period;
        for(period = 0; period < start; ++period)
            string += "    ";
        for(/* period = start */; period < breakStart; ++period)
            string += "|---";
        for(/* period = breakStart */; period <= breakStop; ++period)
            string += "||||";
        for(/* period = breakStop + 1 */; period <= stop; ++period)
            string += "|---";
        // period == stop + 1
            string += "|   ";
        for(++period /* period = stop + 2 */; period <= numberOfPeriods; ++period)
            string += "    ";
        return string;
    }
}
