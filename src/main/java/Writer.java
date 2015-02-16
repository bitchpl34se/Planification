import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Writer {
	Solution sol;
	
	public Writer(Solution newSol)
	{
		sol = newSol;
	}
	
	/**
	 * Permet de créer un fichier contenant toutes les vacations possibles du problème
	 * @param filename le nom du fichier de sortie à créer
	 */
	public void writePossibleVacations(final String filename)
	{
		File output=new File(filename);
		FileWriter fw = null;
		 
		try
		{
			output.createNewFile();
		    fw = new FileWriter (output);
		    
			fw.write("Vacation types : " + sol.instance.vacationTypeList.length);
			fw.write("\r\n");
			for(int typeIndex = 0; typeIndex < sol.instance.vacationTypeList.length; ++typeIndex)
			{
				// écriture des informations relatives au type de vacation
				VacationType vacationType = sol.instance.vacationTypeList[typeIndex];
				fw.write("Vacation " + (typeIndex + 1) + " : ");
				fw.write(vacationType.rangeMin + ", ");
				fw.write(vacationType.rangeMax + ", ");
				fw.write(vacationType.startMin + ", ");
				fw.write(vacationType.stopMax + ", ");
				fw.write(vacationType.breakDurationMin + ", ");
				fw.write(vacationType.breakDurationMax + ", ");
				fw.write(vacationType.startToBreakMin + ", ");
				fw.write(vacationType.breakToStopMin + ", ");
				fw.write(vacationType.limit + ", ");
				fw.write(vacationType.cost + " ; ");
				fw.write(Integer.toString(sol.instance.vacationListPerType[typeIndex].length)); 
				fw.write("\r\n");
				
				// ligne des périodes
				for(int period = 1; period <= sol.instance.charge.getNumberOfPeriods(); ++period)
				{
					if(period < 10)
					{
						fw.write(period + "   ");
					}
					else if(period < 100)
					{
						fw.write(period + "  ");
					}
					else if(period < 1000)
					{
						fw.write(period + " ");
					}
				}
				fw.write("\r\n");
				
				// écriture des vacations possibles pour ce type avec le format suivant :
				// |---|---|~~~|---|---|
				// |---| correspond à une période travaillée
				// |~~~| correspond à une période de pause
				for(int vacationIndex = 0; vacationIndex < sol.instance.vacationListPerType[typeIndex].length; ++vacationIndex)
				{
					Vacation vacation = sol.instance.vacationListPerType[typeIndex][vacationIndex];
					fw.write(vacation.visualizeVacation(sol.instance.charge.getNumberOfPeriods()));
					fw.write("\r\n");
				}
			}			
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de l'écriture du fichier : " + exception.getMessage());
		}
		finally
		{
			try
			{
				 fw.close();
			}
			catch(IOException exception)
			{
				 System.out.println ("Erreur lors de la fermeture du fichier : " + exception.getMessage());
			}
		}
	}

	/**
	 * Permet de créer un fichier contenant les vacations de la solution du problème
	 * @param filename le nom du fichier de sortie à créer
	 */
	public void writeVisualisationVacations(final String filename)
	{
		File output=new File(filename);
		FileWriter fw = null;
		 
		try
		{
			output.createNewFile();
		    fw = new FileWriter (output);
		 
		    // ligne des périodes
			for(int period = 1; period <= sol.instance.charge.getNumberOfPeriods(); ++period)
			{
				if(period < 10)
				{
					fw.write(period + "   ");
				}
				else if(period < 100)
				{
					fw.write(period + "  ");
				}
				else if(period < 1000)
				{
					fw.write(period + " ");
				}
			}
			fw.write("\r\n");
			
			// écriture des vacations apparaissant dans la solution
			for (int i=0; i<sol.numberOfTypes; i++){
				fw.write("vacation type "+ i +":");
				fw.write("\r\n");
				for (int j = 0; j <  sol.numberOfVacationPerType[i].length; j++){
					int solInt = (int)sol.numberOfVacationPerType[i][j];
					if (solInt > 0){
						fw.write(sol.instance.vacationListPerType[i][j].visualizeVacation(sol.instance.charge.getNumberOfPeriods()) + "quantity : " + solInt);
						fw.write("\r\n");
					}
				}
				fw.write("\r\n");
			}			
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de l'écriture du fichier : " + exception.getMessage());
		}
		finally
		{
			try
			{
				 fw.close();
			}
			catch(IOException exception)
			{
				 System.out.println ("Erreur lors de la fermeture du fichier : " + exception.getMessage());
			}
		}		
	}

	/**
	 * Permet de créer un fichier contenant les valeurs de charge du problème
	 * ainsi que la charge effectuée dans notre solution
	 * @param filename le nom du fichier de sortie à créer
	 */
	public void writeVisualisationCharge(final String filename)
	{
		File output=new File(filename);
		FileWriter fw = null;
		 
		try
		{
			output.createNewFile();
		    fw = new FileWriter (output);
			
		    // écriture des valeurs de charges attendues
			fw.write("Charge :;" + sol.instance.charge.getNumberOfPeriods() + " periods");
			fw.write("\r\n");
			for(int period = 0; period < sol.instance.charge.getNumberOfPeriods(); ++period)
			{
				fw.write(sol.instance.charge.get(period) + ";");
			}	
			fw.write("\r\n");
			
			int numberOfPeriods = sol.instance.charge.getNumberOfPeriods();
			Charge loadSolution = new Charge(numberOfPeriods);
			for (int period=0;  period<numberOfPeriods; period++){
				int currentPeriodLoad = 0;
				for (int row =0; row<sol.numberOfVacationPerType.length; row++)
					for (int col =0; col<sol.numberOfVacationPerType[row].length; col++){
						if (sol.instance.vacationListPerType[row][col].isWorkedAt(period))
							currentPeriodLoad+=sol.numberOfVacationPerType[row][col];
					}
				loadSolution.set(period, currentPeriodLoad);
			}
			//TODO
			// écriture des valeurs de charge de la solution
			/*fw.write("Charge de la solution :;");
			fw.write("\r\n");
			for(int period = 0; period < sol.instance.charge.getNumberOfPeriods(); ++period)
			{
				fw.write(sol.instance.charge.get(period) + ";");
			}*/
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de l'écriture du fichier : " + exception.getMessage());
		}
		finally
		{
			try
			{
				 fw.close();
			}
			catch(IOException exception)
			{
				 System.out.println ("Erreur lors de la fermeture du fichier : " + exception.getMessage());
			}
		}		
	}
}
