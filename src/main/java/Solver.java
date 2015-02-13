import com.joptimizer.optimizers.LPOptimizationRequest;
import com.joptimizer.optimizers.LPPrimalDualMethod;


public class Solver {
	
	public Solution findRelaxedSolution (Data instance){
		// instancier la solution de départ 
		Solution relaxedSolution = new Solution (instance);
		// construct the linear program
		LPOptimizationRequest LP = makeRelaxedLinearProgram(instance);
		// instancier un optimizer 
		LPPrimalDualMethod optimizer = new LPPrimalDualMethod();
		// associate the ptimizer to the problem instance (linear program)
		optimizer.setLPOptimizationRequest(LP);
		try {
			int returnCode = optimizer.optimize();
			double[] sol = optimizer.getOptimizationResponse().getSolution();
			// copy the optimizer solution  
			int i = 0;
			for (int row = 0; row< instance.vacationListPerType.length; row++){
				for (int col = 0; col < instance.vacationListPerType[row].length; col++){
					relaxedSolution.setNumberOfVacationPerType(sol[i++], row, col);
				}
			}
			
		} catch (Exception e) {
			System.out.println("Problem encountered when trying to solve the stuff");
			e.printStackTrace();
		}
		
		return relaxedSolution;
	}
	
	
	public LPOptimizationRequest makeRelaxedLinearProgram (Data instance){
				
		int numberOfTypes = instance.vacationListPerType.length;
		int numberOfVacationPerType [][] = new int [numberOfTypes][instance.vacationListPerType[0].length];
		int Tmin = 0, Tmax = instance.charge.getNumberOfPeriods();
		// calculer le nombre de variables
		int numberOfVariables = 0;
		for (int i=0; i<numberOfTypes; i++)
			numberOfVariables += instance.vacationListPerType[i].length;  
		// daclarer la matrice des contraintes
		double G[][] = new double[Tmax+numberOfTypes][numberOfVariables];
		double[] h = new double [Tmax+numberOfTypes];
		int constraintRow = 0;
		// remplir les coeeficients de G et de h
		// parcourir tout les instants et ajouter les contraintes
		for (int t=Tmin; t<Tmax; t++){
			int varIndex = 0;
			// remplir h(constraintRow) d'abord
			h[constraintRow] = -instance.charge.get(t);
			// chercher pour toutes les variables au moments déclaré
			for(Vacation[] vacationList : instance.vacationListPerType)
			{
				for(Vacation vacation : vacationList)
				{
					G[constraintRow][varIndex] = vacation.isWorkedAt(t)? -1: 0;
					varIndex++;
				}
			}
			constraintRow++;
		}
		// remplir les contraintes de respect du max de vacations possibles
		int blocStart = 0;
		for (int n=0; n<numberOfTypes; n++){
			int blocEnd = blocStart+instance.vacationListPerType[n].length;
			h[constraintRow]=instance.vacationTypeList[n].limit;
			for (int i=0; i<numberOfVariables; i++)
				G[constraintRow][i] = 0;
			for (int i=blocStart; i < blocEnd; i++)
				G[constraintRow][i] = 1;
			blocStart = blocEnd;
			constraintRow++;
		}
		blocStart = 0;
		double[] C = new double [numberOfVariables];		
		double[] lb = new double [numberOfVariables];
		// remplir les coefficients de la fonction objectif et les lower bounds
		for (int n=0; n<numberOfTypes; n++){
			int blocEnd = blocStart+instance.vacationListPerType[n].length;
			for (int i=blocStart; i < blocEnd; i++){
				C[i] = instance.vacationTypeList[n].cost;
				lb[i] = 0.0;
			}
			blocStart = blocEnd;
		}
		LPOptimizationRequest or = new LPOptimizationRequest();
		or.setC(C);
		or.setG(G);
		or.setH(h);
		or.setLb(lb);
		return or;
	}

}
