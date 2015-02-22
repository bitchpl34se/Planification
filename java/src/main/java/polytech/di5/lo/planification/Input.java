package polytech.di5.lo.planification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/** Management of input */
class Input
{
    /** Reads an instance in a ".csv" file
     * @param path Path to the input ".csv" file to read and parse
     * @return A {@link Data} object that represents the instance
     */
    static public Data readData(final String path)
    {
        Data data = null;
        BufferedReader fileReader = null;
        final String delimiter = ";";
        try
        {
            String line = null;
            String[] tokens = null;
            int[] load = null;
            VacationType[] vacationTypes = null;
            
            fileReader = new BufferedReader(new FileReader(path));
            
            /* Step 1. Read the number of period */
            line = fileReader.readLine(); // Read line #1
            tokens = line.split(delimiter);
            load = new int[Integer.parseInt(tokens[1])];
            
            /* Step 2. Read the charge for each period */
            line = fileReader.readLine(); // Read line #2
            line = fileReader.readLine(); // Read line #3
            tokens = line.split(delimiter);
            for(int period = 0; period < load.length; period++)
            {
                load[period] = Integer.parseInt(tokens[period]);
            }
            
            /* Step 3. Read the number of vacation types */
            line = fileReader.readLine(); // Read line #4
            line = fileReader.readLine(); // Read line #5
            tokens = line.split(delimiter);
            vacationTypes = new VacationType[Integer.parseInt(tokens[1])];
            
            /* Step 4. Read data for each vacation type */
            line = fileReader.readLine(); // Read line #6
            for(int typeIndex = 0; typeIndex < vacationTypes.length; typeIndex++)
            {
                line = fileReader.readLine(); // Read next line
                tokens = line.split(delimiter);
                vacationTypes[typeIndex] = new VacationType(
                    Integer.parseInt(tokens[1]),
                    Integer.parseInt(tokens[2]),
                    Integer.parseInt(tokens[3]) - 1,
                    Integer.parseInt(tokens[4]) - 1,
                    Integer.parseInt(tokens[5]),
                    Integer.parseInt(tokens[6]),
                    Integer.parseInt(tokens[7]),
                    Integer.parseInt(tokens[8]),
                    Integer.parseInt(tokens[9]),
                    Integer.parseInt(tokens[10])
                );
            }

            data = new Data(load, vacationTypes);
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
                // e.printStackTrace();
            }
        }
        return data;
    }
}