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
		
		visualizeSolution(approximateSolution);
		
		System.out.println (" current cost is : " +approximateSolution.computeCost());
		
		/*if(data == null)
		{
			System.out.println("Error while parsing file \"" + path + "\"");
		}
		else
		{
			visualizeAllVacations(data);
		}*/
	}
	
	public static void visualizeAllVacations(Data data)
	{
		System.out.println("Charge : " + data.charge.getNumberOfPeriods() + " periods");
		for(int period = 0; period < data.charge.getNumberOfPeriods(); ++period)
		{
			if(data.charge.get(period) < 10)
			{
				System.out.print(data.charge.get(period) + "   ");
			}
			else if(data.charge.get(period) < 100)
			{
				System.out.print(data.charge.get(period) + "  ");
			}
			else if(data.charge.get(period) < 1000)
			{
				System.out.print(data.charge.get(period) + " ");
			}
		}
		System.out.println();
		System.out.println("Vacation types : " + data.vacationTypeList.length);
		for(int typeIndex = 0; typeIndex < data.vacationTypeList.length; ++typeIndex)
		{
			VacationType vacationType = data.vacationTypeList[typeIndex];
			System.out.print("Vacation " + (typeIndex + 1) + " : ");
			System.out.print(vacationType.rangeMin + ", ");
			System.out.print(vacationType.rangeMax + ", ");
			System.out.print(vacationType.startMin + ", ");
			System.out.print(vacationType.stopMax + ", ");
			System.out.print(vacationType.breakDurationMin + ", ");
			System.out.print(vacationType.breakDurationMax + ", ");
			System.out.print(vacationType.startToBreakMin + ", ");
			System.out.print(vacationType.breakToStopMin + ", ");
			System.out.print(vacationType.limit + ", ");
			System.out.print(vacationType.cost + " ; ");
			System.out.println(data.vacationListPerType[typeIndex].length);
			for(int period = 0; period <= data.charge.getNumberOfPeriods(); ++period)
			{
				if(period < 10)
				{
					System.out.print(period + "   ");
				}
				else if(period < 100)
				{
					System.out.print(period + "  ");
				}
				else if(period < 1000)
				{
					System.out.print(period + " ");
				}
			}
			System.out.println();
			for(int vacationIndex = 0; vacationIndex < data.vacationListPerType[typeIndex].length; ++vacationIndex)
			{
				Vacation vacation = data.vacationListPerType[typeIndex][vacationIndex];
				System.out.println(vacation.visualizeVacation(data.charge.getNumberOfPeriods()));
			}
		}
	}
	
	public static void visualizeSolution(Solution solution)
	{
		System.out.println("Charge : " + solution.instance.charge.getNumberOfPeriods() + " periods");
		for(int period = 0; period < solution.instance.charge.getNumberOfPeriods(); ++period)
		{
			if(solution.instance.charge.get(period) < 10)
			{
				System.out.print(solution.instance.charge.get(period) + "   ");
			}
			else if(solution.instance.charge.get(period) < 100)
			{
				System.out.print(solution.instance.charge.get(period) + "  ");
			}
			else if(solution.instance.charge.get(period) < 1000)
			{
				System.out.print(solution.instance.charge.get(period) + " ");
			}
		}
		System.out.println();
		for(int period = 0; period <= solution.instance.charge.getNumberOfPeriods(); ++period)
		{
			if(period < 10)
			{
				System.out.print(period + "   ");
			}
			else if(period < 100)
			{
				System.out.print(period + "  ");
			}
			else if(period < 1000)
			{
				System.out.print(period + " ");
			}
		}
		System.out.println();
		
		for (int i=0; i<solution.numberOfTypes; i++){
			System.out.println("vacation type "+ i +":");
			for (int j = 0; j <  solution.numberOfVacationPerType[i].length; j++){
				double sol = solution.numberOfVacationPerType[i][j];
				if ((int)sol > 0){
					for(int k = ((10*(sol-(int)sol)>=1) ? (int) Math.ceil(sol) : (int) sol); k > 0; k--){
						System.out.println(solution.instance.vacationListPerType[i][j].visualizeVacation(solution.instance.charge.getNumberOfPeriods()));
					}
				}
			}
			System.out.println();
		}
	}
}

