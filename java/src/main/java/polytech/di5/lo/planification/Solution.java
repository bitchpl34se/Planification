package polytech.di5.lo.planification;

/** Represents a solution */
public class Solution
{
    /** The instance which the solution is related to */
    public final Data data;
    /** The number of vacations (one array for each vacation type) */
    public final int[][] numberOfVacationsPerType;
    
    /** Constructor
     * @param data The instance which the solution is related to
     */
    public Solution(final Data data)
    {
        this.data = data;
        numberOfVacationsPerType = new int[data.vacationTypes.length][];
        for(int typeIndex = 0; typeIndex < numberOfVacationsPerType.length; typeIndex++)
        {
            numberOfVacationsPerType[typeIndex] = new int[data.vacationTypes[typeIndex].vacations.length];
        }
    }
    
    /** Copy constructor
     * @param anotherSolution The solution to copy
     */
    public Solution(final Solution anotherSolution)
    {
        data = anotherSolution.data;
        numberOfVacationsPerType = new int[data.vacationTypes.length][];
        for(int typeIndex = 0; typeIndex < numberOfVacationsPerType.length; typeIndex++)
        {
            numberOfVacationsPerType[typeIndex] = new int[data.vacationTypes[typeIndex].vacations.length];
            for(int vacationIndex = 0; vacationIndex < data.vacationTypes[typeIndex].vacations.length; vacationIndex++)
            {
                numberOfVacationsPerType[typeIndex][vacationIndex] = anotherSolution.numberOfVacationsPerType[typeIndex][vacationIndex];
            }
        }
    }
    
    /** Tests whether a solution is feasible
     * @return {@code true} if the solution is feasible, {@code false} otherwise
     */
    public boolean check()
    {
        return respectsLoad() && respectsLimits();
    }

    /** Tests whether a solution respects the constraints on the load
     * @return {@code true} if the solution respects the constraints on the load, {@code false} otherwise
     */
    public boolean respectsLoad()
    {
        boolean result = true;
        for(int period = 0; result && (period < data.load.length); period++)
        {
            result = (computeLoad(period) >= data.load[period]);
        }
        return result;
    }
    
    /** Tests whether a solution respects the constraints on the number of vacations per type
     * @return {@code true} if the solution respects the constraints on the number of vacations per type, {@code false} otherwise
     */
    public boolean respectsLimits()
    {
        boolean result = true;
        for(int typeIndex = 0; typeIndex < numberOfVacationsPerType.length; typeIndex++)
        {
            result = (countVacationType(typeIndex) <= data.vacationTypes[typeIndex].limit);
        }
        return result;
    }
    
    /** Computes the load of a solution for one period
     * @param period A period
     * @return The load of the solution for the given period
     */
    public int computeLoad(final int period)
    {
        int load = 0;
        for(int typeIndex = 0; typeIndex < numberOfVacationsPerType.length; typeIndex++)
        for(int vacationIndex = 0; vacationIndex < numberOfVacationsPerType[typeIndex].length; vacationIndex++)
        {
            if(data.vacationTypes[typeIndex].vacations[vacationIndex].isWorkedAt(period))
                load += numberOfVacationsPerType[typeIndex][vacationIndex];
        }
        return load;
    }
    
    /** Counts the vacations of one type in a solution
     * @param typeIndex An index of a vacation type
     * @return The number of vacations of the given type in the solution
     */
    public int countVacationType(final int typeIndex)
    {
        int counter = 0;
        for(int numberOfVacations : numberOfVacationsPerType[typeIndex])
        {
            counter += numberOfVacations;
        }
        return counter;
    }
    
    /** Computes the cost of a solution
     * @return The cost of the solution
     */
    public int computeCost()
    {
        int cost = 0;
        for(int typeIndex = 0; typeIndex < numberOfVacationsPerType.length; typeIndex++)
        for(int vacationIndex = 0; vacationIndex < numberOfVacationsPerType[typeIndex].length; vacationIndex++)
        {
            cost += numberOfVacationsPerType[typeIndex][vacationIndex] * data.vacationTypes[typeIndex].cost;
        }
        return cost;
    }
}
