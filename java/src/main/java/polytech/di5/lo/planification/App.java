package polytech.di5.lo.planification;

import org.apache.log4j.PropertyConfigurator;

/** Main class
 * @see App#main(String[])
 */
public class App
{
    /** Main method
     * @param args Arguments passed to the program
     */
    public static void main(final String[] args)
    {
	/* Proper initialization of the "log4j" system for "JOptimizer" */
	PropertyConfigurator.configure("log4j.properties");
	
        final MainArguments paths = (args.length == 0) ? new MainArguments() : new MainArguments(args);
        
        Data data = Input.readData(paths.pathToInputData);
        System.out.println("Reading from input terminated");

        System.out.println("Solver launched");
        Solution solution = Solver.applyTo(data);
        System.out.println("Solution found");
        
        data.fitOutput();
        Output.writeVacations(solution, paths.pathToOutputVacations);
        Output.writeSolution(solution, paths.pathToOutputSolution);
        Output.writeLoad(solution, paths.pathToOutputLoad);
        System.out.println("Writing to output terminated");
    }
    
    /** Management of the arguments passed to the {@link App#main(String[])} method */
    private static class MainArguments
    {
	/** Path to the file that contains the instance */
	public final String pathToInputData;
	/** Path to the file that will contain the enumeration of all vacations per type */
	public final String pathToOutputVacations;
	/** Path to the file that will contain the solution */
	public final String pathToOutputSolution;
	/** Path to the file that will contain the effective load */
	public final String pathToOutputLoad;
	
	/** Default constructor (defines default file names) */
	public MainArguments()
	{
	    pathToInputData = "data.csv";
	    pathToOutputVacations = "vacations.txt";
	    pathToOutputSolution = "solution.txt";
	    pathToOutputLoad = "load.csv";
	}
	
	/** Constructor
	 * @param args Arguments passed to the {@link App#main(String[])} method
	 * @throws IllegalArgumentException If the extensions of the file names are not valid
	 */
	public MainArguments(final String[] args) throws IllegalArgumentException
	{
	    if(args.length != 4)
		throw new IllegalArgumentException("Invalid number of arguments [MainArguments constructor]");
	    if(!(args[0].endsWith(".csv")))
		throw new IllegalArgumentException("Extension \".csv\" is required for the input file [MainArguments constructor]");
	    if(!(args[1].endsWith(".txt")))
		throw new IllegalArgumentException("Extension \".txt\" is required for the output file 1 (vacations) [MainArguments constructor]");
	    if(!(args[2].endsWith(".txt")))
		throw new IllegalArgumentException("Extension \".txt\" is required for the output file 2 (solution) [MainArguments constructor]");
	    if(!(args[3].endsWith(".csv")))
		throw new IllegalArgumentException("Extension \".csv\" is required for the output file 3 (load) [MainArguments constructor]");
	    
	    pathToInputData = args[0];
	    pathToOutputVacations = args[1];
	    pathToOutputSolution = args[2];
	    pathToOutputLoad = args[3];
	}
    }
}
