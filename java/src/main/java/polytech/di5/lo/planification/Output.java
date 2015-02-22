package polytech.di5.lo.planification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/** Management of output */
public class Output
{
    /** Writes all possible vacations of an instance to a text file
     * @param solution A solution related to the instance
     * @param path Path to the output text file
     */
    static void writeVacations(final Solution solution, final String path)
    {
	BufferedWriter fileWriter = null;
	final String newline = "\r\n";
        try
        {
            fileWriter = new BufferedWriter(new FileWriter(path));
            fileWriter.write(solution.data.toString());
            fileWriter.write(newline);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fileWriter.close();
            }
            catch (IOException e)
            {
                // e.printStackTrace();
            }
        }
    }

    /** Writes chosen vacations of a solution and their quantities to a text file
     * @param solution A solution
     * @param path Path to the output text file
     */
    static void writeSolution(final Solution solution, final String path)
    {
	BufferedWriter fileWriter = null;
	final String newline = "\r\n";
        try
        {
            fileWriter = new BufferedWriter(new FileWriter(path));
            for(int typeIndex = 0; typeIndex < solution.numberOfVacationsPerType.length; typeIndex++)
            {
        	fileWriter.write("# Vacation type " + (typeIndex + 1));
        	if(solution.countVacationType(typeIndex) == 0)
        	{
        	    fileWriter.write(" (no vacation of this type)");
        	}
        	else
        	{
        	    for(int vacationIndex = 0; vacationIndex < solution.numberOfVacationsPerType[typeIndex].length; vacationIndex++)
                {
                    if(solution.numberOfVacationsPerType[typeIndex][vacationIndex] > 0)
                    {
                        fileWriter.write(newline);
                        fileWriter.write(solution.data.vacationTypes[typeIndex].vacations[vacationIndex].toString());
                        fileWriter.write("# quantity = " + solution.numberOfVacationsPerType[typeIndex][vacationIndex]);
                    }
                }
        	}
        	fileWriter.write(newline);
            }
            fileWriter.write("# Cost");
            fileWriter.write(newline);
            fileWriter.write(Integer.toString(solution.computeCost()));
            fileWriter.write(newline);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fileWriter.close();
            }
            catch (IOException e)
            {
                // e.printStackTrace();
            }
        }
    }

    /** Writes the load satisfied by a solution to a ".csv" file
     * @param solution A solution
     * @param path Path to the output ".csv" file
     */
    static void writeLoad(final Solution solution, final String path)
    {
	BufferedWriter fileWriter = null;
	final String newline = "\r\n";
        final String delimiter = ";";
        try
        {
            fileWriter = new BufferedWriter(new FileWriter(path));
            
            fileWriter.write("Periods");
            fileWriter.write(delimiter);
            for(int period = 0; period < solution.data.load.length; period++)
            {
        	fileWriter.write(Integer.toString(period + 1));
                fileWriter.write(delimiter);
            }
            fileWriter.write(newline);
            
            fileWriter.write("Load (Data)");
            fileWriter.write(delimiter);
            for(int period = 0; period < solution.data.load.length; period++)
            {
        	fileWriter.write(Integer.toString(solution.data.load[period]));
                fileWriter.write(delimiter);
            }
            fileWriter.write(newline);
            
            fileWriter.write("Load (Solution)");
            fileWriter.write(delimiter);
            for(int period = 0; period < solution.data.load.length; period++)
            {
        	fileWriter.write(Integer.toString(solution.computeLoad(period)));
                fileWriter.write(delimiter);
            }
            fileWriter.write(newline);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fileWriter.close();
            }
            catch (IOException e)
            {
                // e.printStackTrace();
            }
        }
    }
}
