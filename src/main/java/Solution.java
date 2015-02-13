
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
	
}
