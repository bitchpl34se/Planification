package polytech.di5.lo.planification;

/** Represents an instance */
public class Data
{
    /** The load for each period */
    public final int[] load;
    /** The list of {@link VacationType} available */
    public final VacationType[] vacationTypes;
    
    /** Constructor
     * @param load An array of integers, that represents the load for each period
     * @param vacationTypes An array of {@link VacationType}
     * @throws IllegalArgumentException If the parameters are not coherent
     */
    public Data(final int[] load, final VacationType[] vacationTypes) throws IllegalArgumentException
    {
        this.load = load;
        this.vacationTypes = vacationTypes;
        if(!check())
        {
            throw new IllegalArgumentException("Invalid parameters [Data constructor]");
        }
    }
    
    /** Copy constructor
     * @param anotherData The instance to copy
     */
    public Data(final Data anotherData)
    {
        load = new int[anotherData.load.length];
        for(int period = 0; period < load.length; period++)
        {
            load[period] = anotherData.load[period];
        }
        vacationTypes = new VacationType[anotherData.vacationTypes.length];
        for(int typeIndex = 0; typeIndex < vacationTypes.length; typeIndex++)
        {
            vacationTypes[typeIndex] = new VacationType(anotherData.vacationTypes[typeIndex]);
        }
    }
    
    /** Tests the coherence of a {@link Data} object
     * @return {@code true} if the object is coherent, {@code false} otherwise
     */
    public boolean check()
    {
        boolean result = true;
        for(int period = 0; result && (period < load.length); period++)
            result = (load[period] >= 0);
        for(int typeIndex = 0; result && (typeIndex < vacationTypes.length); typeIndex++)
            result = (vacationTypes[typeIndex].stopMax < load.length);
        return result;
    }
    
    /** Modifies {@link Vacation#numberOfPeriods} to adjust the output to an instance
     */
    public void fitOutput()
    {
        Vacation.numberOfPeriods = load.length;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        final String newline = "\r\n";
        String string = "";
        string += "# Periods";
        string += newline;
        for(int period = 0; period < load.length; period++)
        {
            string += period + 1;
                 if(period + 1 < 10  ) string += "   ";
            else if(period + 1 < 100 ) string += "  " ;
            else if(period + 1 < 1000) string += " "  ;
        }
        string += "    #";
        string += newline + "# Load";
        string += newline;
        for(int load : load)
        {
            string += load;
                 if(load < 10  ) string += "   ";
            else if(load < 100 ) string += "  " ;
            else if(load < 1000) string += " "  ;
        }
        string += "    #";
        int typeIndex = 0;
        for(VacationType vacationType : vacationTypes)
        {
            string += newline + "# Vacation type " + (++typeIndex) + " " + vacationType;
            for(Vacation vacation : vacationType.vacations)
            {
                string += newline + vacation + "#";
            }
        }
        return string;
    }
}
