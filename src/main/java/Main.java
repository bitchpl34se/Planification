
public class Main
{
	public static void main(final String[] parameters)
	{
		final String path = (parameters.length > 0) ? parameters[0] : "data_windows_utf8.csv";
		final Data data = Reader.read(path);
		
		//Solver sol = new Solver();
		//sol.makeRelaxedLinearProgram(data);
		
		if(data == null)
		{
			System.out.println("Error while parsing file \"" + path + "\"");
		}
		else
		{
			visualizeAllVacations(data);
		}
	}
	
	public static void visualizeAllVacations(Data data)
	{
		System.out.println("Charge : " + data.charge.getNumberOfPeriods() + " periods");
		for(int period = 0; period < data.charge.getNumberOfPeriods(); ++period)
		{
			//System.out.print("\t" + data.charge.get(period));
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
	
	public static void visualizeSolution(Data data)
	{
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
		
		// créer partie où l'on affiche les vacations solutions
	}
}
