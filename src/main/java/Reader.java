
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Reader
{
	public static Data read(final String fileToParse)
	{
		Data data = null;
		BufferedReader fileReader = null;
		final String delimiter = ";";
		try
		{
			String line = null;
			String[] tokens = null;
			Charge charge = null;
			VacationType[] vacationTypeList = null;
			
			fileReader = new BufferedReader(new FileReader(fileToParse));
			
			/* Step 1. Read the number of period */
			line = fileReader.readLine(); // Read line #1
			tokens = line.split(delimiter);
			charge = new Charge(Integer.parseInt(tokens[1]));
			
			/* Step 2. Read the charge for each period */
			line = fileReader.readLine(); // Read line #2
			line = fileReader.readLine(); // Read line #3
			tokens = line.split(delimiter);
			for(int period = 0; period < charge.getNumberOfPeriods(); ++period)
			{
				charge.set(period, Integer.parseInt(tokens[period]));
			}
			
			/* Step 3. Read the number of vacation types */
			line = fileReader.readLine(); // Read line #4
			line = fileReader.readLine(); // Read line #5
			tokens = line.split(delimiter);
			vacationTypeList = new VacationType[Integer.parseInt(tokens[1])];
			for(int typeIndex = 0; typeIndex < vacationTypeList.length; ++typeIndex)
			{
				vacationTypeList[typeIndex] = new VacationType();
			}
			
			/* Step 4. Read data for each vacation type */
			line = fileReader.readLine(); // Read line #6
			for(VacationType vacationType : vacationTypeList)
			{
				line = fileReader.readLine(); // Read next line
				tokens = line.split(delimiter);
				vacationType.rangeMin = Integer.parseInt(tokens[1]);
				vacationType.rangeMax = Integer.parseInt(tokens[2]);
				vacationType.startMin = Integer.parseInt(tokens[3]);
				vacationType.stopMax = Integer.parseInt(tokens[4]);
				vacationType.breakDurationMin = Integer.parseInt(tokens[5]);
				vacationType.breakDurationMax = Integer.parseInt(tokens[6]);
				vacationType.startToBreakMin = Integer.parseInt(tokens[7]);
				vacationType.breakToStopMin = Integer.parseInt(tokens[8]);
				vacationType.limit = Integer.parseInt(tokens[9]);
				vacationType.cost = Integer.parseInt(tokens[10]);
			}

			data = new Data(charge, vacationTypeList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fileReader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return data;
	}
}
