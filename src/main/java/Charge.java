
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
	
	public void display()
	{
		System.out.println("Charge : " + this.getNumberOfPeriods() + " periods");
		for(int period = 0; period < this.getNumberOfPeriods(); ++period)
		{
			if(this.get(period) < 10)
			{
				System.out.print(this.get(period) + "   ");
			}
			else if(this.get(period) < 100)
			{
				System.out.print(this.get(period) + "  ");
			}
			else if(this.get(period) < 1000)
			{
				System.out.print(this.get(period) + " ");
			}
		}
		System.out.println();
	}
}
