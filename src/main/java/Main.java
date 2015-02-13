import org.apache.log4j.BasicConfigurator;

public class Main
{
	public static void main(final String[] parameters)
	{
		BasicConfigurator.configure();
		final String path = (parameters.length > 0) ? parameters[0] : "data_windows_utf8.csv";
		final Data data = Reader.read(path);
		
		Solver solver = new Solver();
		Solution approximateSolution = solver.findRelaxedSolution(data);
		
		approximateSolution.visualizeSolution(approximateSolution);
		
		System.out.println (" current cost is : " +approximateSolution.computeCost());
		
		/*if(data == null)
		{
			System.out.println("Error while parsing file \"" + path + "\"");
		}
		else
		{
			approximateSolution.visualizeAllVacations(data);
		}*/
	}
}

