
public class Charge
{
	private int[] chargePerPeriod;

	public Charge(final int numberOfPeriods)
	{
		chargePerPeriod = new int[numberOfPeriods];
	}
	
	public int getNumberOfPeriods()
	{
		return chargePerPeriod.length;
	}
	
	public int get(final int period)
	{
		return chargePerPeriod[period];
	}
	
	public void set(final int period, final int value)
	{
		chargePerPeriod[period] = value;
	}
}
