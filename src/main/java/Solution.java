
public class Solution {
	
	public int numberOfTypes;
	public final Data instance;
	public double numberOfVacationPerType [][];
	
	public Solution (Data instance){
		this.instance = instance;
		numberOfTypes = instance.vacationListPerType.length;
		numberOfVacationPerType = new double [numberOfTypes][];
		for (int i=0; i<numberOfTypes; i++){
			numberOfVacationPerType[i] = new double [instance.vacationListPerType[i].length];
		}
		
	}

	public Data getInstance() {
		return instance;
	}

	public double getNumberOfVacationPerType(int i, int j) {
		return numberOfVacationPerType [i][j];
	}

	public void setNumberOfVacationPerType(double numberOfVacationPerType, int i, int j) {
		this.numberOfVacationPerType[i][j] = numberOfVacationPerType;
	}
	
	public int computeCost(){
		int cost = 0;
		for (int i=0; i<numberOfTypes; i++){
			int r = instance.vacationTypeList[i].cost;
			for (double sol : numberOfVacationPerType[i]){
				//cost+=((int)sol)*r;
				
				cost+=(((10*(sol-(int)sol)>=1) ? (int) Math.ceil(sol) : (int) sol))*r;
			}
		}
		return cost;
	}
	
	public void visualizeSolution(Solution solution)
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
		for(int period = 1; period <= solution.instance.charge.getNumberOfPeriods(); ++period)
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
				int sol = (int)solution.numberOfVacationPerType[i][j];
				if (sol > 0){
					System.out.println(solution.instance.vacationListPerType[i][j].visualizeVacation(solution.instance.charge.getNumberOfPeriods()) + "quantity : " + sol);
				}
			}
			System.out.println();
		}
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
			for(int period = 1; period <= data.charge.getNumberOfPeriods(); ++period)
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
}
