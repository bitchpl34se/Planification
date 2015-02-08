
public class Data
{
	public final Charge charge;
	public final VacationType[] vacationTypeList;
	public final Vacation[][] vacationListPerType;
	
	public Data(final Charge charge, final VacationType[] vacationTypeList)
	{
		this.charge = charge;
		this.vacationTypeList = vacationTypeList;
		this.vacationListPerType = new Vacation[vacationTypeList.length][];
		for(int typeIndex = 0; typeIndex < vacationListPerType.length; ++typeIndex)
		{
			this.vacationListPerType[typeIndex] = this.vacationTypeList[typeIndex].enumerate();
		}
	}
}
