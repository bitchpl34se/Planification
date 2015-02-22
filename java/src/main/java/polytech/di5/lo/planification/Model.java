package polytech.di5.lo.planification;

import com.joptimizer.optimizers.*;

/** Represents the relaxed problem */
public class Model
{
    /** Finds the solution of the relaxed model
     * @param data An instance to solve
     * @return The solution of the relaxed model, applied to the instance
     */
    public static double[][] applyTo(final Data data)
    {
        Model model = new Model(data);
        model.build();
        model.solve();
        model.extract();
        return model.numberOfVacationsPerType;
    }
    
    /** The instance to solve */
    private final Data data;
    /** The solution of the relaxed problem */
    private double[][] numberOfVacationsPerType;
    /** The "JOptimizer" representation of the problem */
    private LPOptimizationRequest request;
    /** The values of the variables returned by "JOptimizer" */
    private double[] answer;
    
    /** Constructor (private)
     * @param data The instance to solve
     */
    private Model(final Data data)
    {
        this.data = data;
        numberOfVacationsPerType = new double[data.vacationTypes.length][];
        for(int typeIndex = 0; typeIndex < numberOfVacationsPerType.length; typeIndex++)
        {
            numberOfVacationsPerType[typeIndex] = new double[data.vacationTypes[typeIndex].vacations.length];
        }
    }
    
    /** Declares the variables, constraints and objective of the problem */
    private void build()
    {
        final int numberOfPeriods = data.load.length;
        final int numberOfVacationTypes = data.vacationTypes.length;
        /* Step 1. Determine the number of variables and constraints */
        final int[] firstVacationIndexPerType = new int[numberOfVacationTypes + 1];
        firstVacationIndexPerType[0] = 0;
        for(int typeIndex = 0; typeIndex < numberOfVacationTypes; typeIndex++)
        {
            firstVacationIndexPerType[typeIndex + 1] = firstVacationIndexPerType[typeIndex] + data.vacationTypes[typeIndex].vacations.length;
        }
        final int numberOfVariables = firstVacationIndexPerType[numberOfVacationTypes];
        final int numberOfConstraints = numberOfPeriods + numberOfVacationTypes;
        /* Step 2. Declaration of the matrixes and vectors that define the problem */
        final double[][] G = new double[numberOfConstraints][numberOfVariables];
        final double[] h = new double[numberOfConstraints];
        final double[] c = new double[numberOfVariables];
        final double[] lb = new double[numberOfVariables];
        /* Step 3. Set coefficients of 'G' and 'h' (constraints) */
        int row = 0, col;
        /* Step 3.1. Constraints on the load, for each period */
        for(int period = 0; period < numberOfPeriods; period++)
        {
            /* 'G' */
            col = 0;
            for(VacationType vacationType : data.vacationTypes)
            for(Vacation vacation : vacationType.vacations)
            {
                G[row][col] = (vacation.isWorkedAt(period)) ? -1 : 0;
                col++;
            }
            /* 'h' */
            h[row] = - data.load[period];
            
            row++;
        }
        /* Step 3.2. Constraints on the limit of the number of vacations per type, for each type */
        for(int typeIndex = 0; typeIndex < numberOfVacationTypes; typeIndex++)
        {
            /* 'G' */
            for(col = 0; col < numberOfVariables; col++)
            {
                G[row][col] = ((firstVacationIndexPerType[typeIndex] <= col) && (col < firstVacationIndexPerType[typeIndex + 1])) ? 1 : 0;
            }
            /* 'h' */
            h[row] = data.vacationTypes[typeIndex].limit;
            
            row++;
        }
        /* Step 4. Set coefficients of 'c' (objective) */
        for(int typeIndex = 0; typeIndex < numberOfVacationTypes; typeIndex++)
        for(col = firstVacationIndexPerType[typeIndex]; col < firstVacationIndexPerType[typeIndex + 1]; col++)
        {
            c[col] = data.vacationTypes[typeIndex].cost;
        }
        /* Step 5. Set coefficients of 'lb' (domains of variables) */
        for(col = 0; col < numberOfVariables; col++)
        {
            lb[col] = 0;
        }
        /* Step 6. Instantiate the "JOptimizer" class that represents the problem */
        request = new LPOptimizationRequest();
        request.setG(G);
        request.setH(h);
        request.setC(c);
        request.setLb(lb);
        /* DEBUG : Show problem */
        /* System.out.println("c");
        for(double coefficient : request.getC().toArray())
        {
            System.out.print("" + ((int)coefficient) + '\t');
        }
        System.out.println();
        System.out.println("G");
        for(double[] rowG : request.getG().toArray())
        {
            for(double coefficient : rowG)
            {
        	System.out.print("" + ((int)coefficient) + '\t');
            }
            System.out.println();
        }
        System.out.println("h");
        for(double coefficient : request.getH().toArray())
        {
            System.out.print("" + ((int)coefficient) + '\t');
        }
        System.out.println();
        System.out.println("lb");
        for(double coefficient : request.getLb().toArray())
        {
            System.out.print("" + ((int)coefficient) + '\t');
        }
        System.out.println(); */
    }
    
    /** Launches the resolution */
    private void solve()
    {
        /* Step 1. Instantiate a solver of the problem */
        LPPrimalDualMethod optimizer = new LPPrimalDualMethod();
        optimizer.setLPOptimizationRequest(request);
        /* Step 2. Launch resolution */
        try
        {
            /* final int returnCode = */ optimizer.optimize();
            answer = optimizer.getOptimizationResponse().getSolution();
        }
        catch(Exception e)
        {
            System.out.println("Problem encountered when applying relaxed model to the instance [Model#solve]");
            e.printStackTrace();
        }
    }
    
    /** Retrieves the value of the variables obtained after resolution */
    private void extract()
    {
        int col = 0;
        for(int typeIndex = 0; typeIndex < data.vacationTypes.length; typeIndex++)
        for(int vacationIndex = 0; vacationIndex < data.vacationTypes[typeIndex].vacations.length; vacationIndex++)
        {
            numberOfVacationsPerType[typeIndex][vacationIndex] = answer[col];
            col++;
        }
    }
}
