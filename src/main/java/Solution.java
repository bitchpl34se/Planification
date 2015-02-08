
public class Solution {
	
	public final Data instance;
	public double numberOfVacationPerType [][];
	
	public Solution (Data instance){
		this.instance = instance;
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
	
	
}
