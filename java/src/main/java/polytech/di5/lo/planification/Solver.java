package polytech.di5.lo.planification;

/** Implements the heuristic */
public class Solver
{
    /** Finds a feasible solution
     * @param data An instance to solve
     * @return A solution that corresponds to the instance
     */
    public static Solution applyTo(final Data data)
    {
        Solver solver = new Solver(data);
        solver.applyModelTwice();
        solver.adjust();
        return solver.solution;
    }
    
    /** The instance to solve */
    private final Data data;
    /** The solution found with the heuristic */
    private Solution solution;
    /** A solution, maybe not feasible, that defines the minimum number of vacations */
    private Solution solutionMin;
    /** A solution, maybe not feasible, that defines the maximum number of vacations */
    private Solution solutionMax;
    
    /** Constructor (private)
     * @param data The instance to solve
     */
    private Solver(final Data data)
    {
        this.data = data;
        solution = null;
        solutionMin = null;
        solutionMax = null;
    }
    
    /** Gets the solution of the relaxed problem */
    private void applyModelTwice()
    {
	double[][] numberOfVacationsPerType = null;
	/* Step 1. Apply model on original data */
        numberOfVacationsPerType = Model.applyTo(data);
        /* Step 2. Deduce the minimum number of vacations */
	solutionMin = new Solution(data);
        for(int typeIndex = 0; typeIndex < data.vacationTypes.length; typeIndex++)
        for(int vacationIndex = 0; vacationIndex < data.vacationTypes[typeIndex].vacations.length; vacationIndex++)
        {
            solutionMin.numberOfVacationsPerType[typeIndex][vacationIndex] = (int) Math.floor(numberOfVacationsPerType[typeIndex][vacationIndex]);
        }
        /* Step 3. Compute missing load */
        final Data missingData = new Data(data);
        for(int period = 0; period < data.load.length; period++)
        {
            missingData.load[period] = Math.max(0, data.load[period] - solutionMin.computeLoad(period));
        }
        /* Step 4. Apply model on new data (missing load) */
        numberOfVacationsPerType = Model.applyTo(missingData);
        /* Step 5. Deduce the maximum number of vacations */
        solutionMax = new Solution(solutionMin);
        for(int typeIndex = 0; typeIndex < data.vacationTypes.length; typeIndex++)
        for(int vacationIndex = 0; vacationIndex < data.vacationTypes[typeIndex].vacations.length; vacationIndex++)
        {
            solutionMax.numberOfVacationsPerType[typeIndex][vacationIndex] += (int) Math.ceil(numberOfVacationsPerType[typeIndex][vacationIndex]);
        }
        /* DEBUG : Verify that 'solutionMin' respects the limits and 'solutionMax' respects the load */
        if(!(solutionMin.respectsLimits() && solutionMax.respectsLoad()))
            throw new RuntimeException("Error detected during resolution (bounds)");
    }
    
    /** Transforms the solution of the relaxed problem into a feasible solution */
    private void adjust()
    {
        solution = new Solution(solutionMin);
        while(!(solution.check()))
        {
            insertVacation();
        }
    }

    /** Selects one vacation to add in the solution */
    private void insertVacation()
    {
	/* Step 1. For each available vacation, compute the number of "interesting periods" (cardinal of the set that contains the periods worked for which the load is not satisfied) */
	int[][] coveredPeriods = new int[data.vacationTypes.length][];
	for(int typeIndex = 0; typeIndex < data.vacationTypes.length; typeIndex++)
	{
	    coveredPeriods[typeIndex] = new int[data.vacationTypes[typeIndex].vacations.length];
	    for(int vacationIndex = 0; vacationIndex < data.vacationTypes[typeIndex].vacations.length; vacationIndex++)
	    {
		coveredPeriods[typeIndex][vacationIndex] = 0;
		if(solution.numberOfVacationsPerType[typeIndex][vacationIndex] <= solutionMax.numberOfVacationsPerType[typeIndex][vacationIndex]) // availability
		{
		    for(int period = 0; period < data.load.length; period++)
		    {
			if(data.vacationTypes[typeIndex].vacations[vacationIndex].isWorkedAt(period) && (data.load[period] - solution.computeLoad(period) > 0))
			    ++(coveredPeriods[typeIndex][vacationIndex]);
		    }
		}	
	    }
	}
	/* Step 2. For each type, select an available vacation (if it exists) that covers the greatest number of "interesting periods" */
	int[] maxCoveredPeriods = new int[data.vacationTypes.length];
	int[] maxVacationIndex = new int[data.vacationTypes.length];
	for(int typeIndex = 0; typeIndex < data.vacationTypes.length; typeIndex++)
	{
	    maxCoveredPeriods[typeIndex] = -1;
	    for(int vacationIndex = 0; vacationIndex < data.vacationTypes[typeIndex].vacations.length; vacationIndex++)
	    {
		if(solution.numberOfVacationsPerType[typeIndex][vacationIndex] <= solution.numberOfVacationsPerType[typeIndex][vacationIndex]) // availability
		{
		    if(maxCoveredPeriods[typeIndex] < coveredPeriods[typeIndex][vacationIndex])
		    {
			maxCoveredPeriods[typeIndex] = coveredPeriods[typeIndex][vacationIndex];
			maxVacationIndex[typeIndex] = vacationIndex;
		    }
		}
	    }
	}
	/* Step 3. Select the type of vacation (requires the availability of at least one vacation) */
	int maxMaxCoveredPeriods = -1;
	int maxTypeIndex = -1;
	for(int typeIndex = 0; typeIndex < data.vacationTypes.length; typeIndex++)
	{
	    if(maxCoveredPeriods[typeIndex] >= 0) // availability
	    {
		if(maxTypeIndex < 0) // no type selected yet
		{
		    maxMaxCoveredPeriods = maxCoveredPeriods[typeIndex];
		    maxTypeIndex = typeIndex;
		}
		else
		{
		    if(    /* 1st criterion = number of "interesting periods" */ (maxMaxCoveredPeriods < maxCoveredPeriods[typeIndex])
                        || /* 2nd criterion = cost */ ((maxMaxCoveredPeriods == maxCoveredPeriods[typeIndex]) && (data.vacationTypes[maxTypeIndex].cost > data.vacationTypes[typeIndex].cost)))
		    {
			maxMaxCoveredPeriods = maxCoveredPeriods[typeIndex];
			maxTypeIndex = typeIndex;
		    }
		}
	    }
	}
	/* Step 4. Insert the selected vacation */
	if(maxTypeIndex < 0) // No vacation available
	    throw new RuntimeException("No solution found");
	++(solution.numberOfVacationsPerType[maxTypeIndex][maxVacationIndex[maxTypeIndex]]);
    }
}
