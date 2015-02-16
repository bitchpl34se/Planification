
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
				cost+=((int)sol)*r;
				
				//cost+=(((10*(sol-(int)sol)>=1) ? (int) Math.ceil(sol) : (int) sol))*r;
			}
		}
		return cost;
	}
	
	public void visualizeSolution()
	{
		this.instance.charge.display();
		
		int numberOfPeriods = this.instance.charge.getNumberOfPeriods();
		Charge loadSolution = new Charge(numberOfPeriods);
		for (int period=0;  period<numberOfPeriods; period++){
			int currentPeriodLoad = 0;
			for (int row =0; row<this.numberOfVacationPerType.length; row++)
				for (int col =0; col<this.numberOfVacationPerType[row].length; col++){
					if (this.instance.vacationListPerType[row][col].isWorkedAt(period))
						currentPeriodLoad+=this.numberOfVacationPerType[row][col];
				}
			loadSolution.set(period, currentPeriodLoad);
		}
		
		loadSolution.display();
		
		System.out.println();
		for(int period = 1; period <= this.instance.charge.getNumberOfPeriods(); ++period)
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
		
		for (int i=0; i<this.numberOfTypes; i++){
			System.out.println("vacation type "+ i +":");
			for (int j = 0; j <  this.numberOfVacationPerType[i].length; j++){
				int sol = (int)this.numberOfVacationPerType[i][j];
				if (sol > 0){
					System.out.println(this.instance.vacationListPerType[i][j].visualizeVacation(this.instance.charge.getNumberOfPeriods()) + "quantity : " + sol);
				}
			}
			System.out.println();
		}
	}
	
	public void visualizeAllVacations()
	{
		System.out.println("Charge : " + this.instance.charge.getNumberOfPeriods() + " periods");
		for(int period = 0; period < this.instance.charge.getNumberOfPeriods(); ++period)
		{
			if(this.instance.charge.get(period) < 10)
			{
				System.out.print(this.instance.charge.get(period) + "   ");
			}
			else if(this.instance.charge.get(period) < 100)
			{
				System.out.print(this.instance.charge.get(period) + "  ");
			}
			else if(this.instance.charge.get(period) < 1000)
			{
				System.out.print(this.instance.charge.get(period) + " ");
			}
		}
		System.out.println();
		System.out.println("Vacation types : " + this.instance.vacationTypeList.length);
		for(int typeIndex = 0; typeIndex < this.instance.vacationTypeList.length; ++typeIndex)
		{
			VacationType vacationType = this.instance.vacationTypeList[typeIndex];
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
			System.out.println(this.instance.vacationListPerType[typeIndex].length);
			for(int period = 1; period <= this.instance.charge.getNumberOfPeriods(); ++period)
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
			for(int vacationIndex = 0; vacationIndex < this.instance.vacationListPerType[typeIndex].length; ++vacationIndex)
			{
				Vacation vacation = this.instance.vacationListPerType[typeIndex][vacationIndex];
				System.out.println(vacation.visualizeVacation(this.instance.charge.getNumberOfPeriods()));
			}
		}
	}
}
