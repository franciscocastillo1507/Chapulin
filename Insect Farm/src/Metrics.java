import java.lang.reflect.Array;

public class Metrics implements Runnable{
	
	int insectsH;
	int insectsN;
	int insectsA;
	int width;
	int height;
	int depth;
	
	void set_insectsH(int insectsH) {
		this.insectsH = insectsH;
	}
	void set_insectsN(int insectsN) {
		this.insectsN = insectsN;
	}
	void set_insectsA(int insectsA) {
		this.insectsA = insectsA;
	}
	
	void set_width(int width) {
		this.width = width;
	}
	void set_height(int height) {
		this.height = height;
	}
	void set_depth(int depth) {
		this.depth = depth;
	}
	
	int huevosNinfaInitial = 18;
	int huevosAdultoInitial = 31;
	
	// Proporcion de vida matriz de leslie con base en estudios hechos orientado a un año
	// -- calculos a varios años
	
	float [][] preLeslieMatrix = new float [3][3];
	float array[] = new float [3];
	
	public synchronized void syncChangeLeslieMatrixTemperature(int type, float change) {
		//percentage
		int huevosNinfa = 18;
		int huevosAdulto = 31;
		float percentageNinfa = (float) .6;
		float percentageAdult = (float) .5;
		if(type == 0) {
			// Temperature Changes
			if(change > 0 && change < 10) {
				percentageNinfa = (float) (percentageNinfa - .01);
				percentageAdult = (float) (percentageAdult - .005);
			}
			if(change > 11 && change < 30) {

				percentageNinfa = (float) (percentageNinfa - .02);
				percentageAdult = (float) (percentageAdult - .01);
			}
			
			
		}else {
			// Humidity Changes

			if(change > 0 && change < 10) {

				percentageNinfa = (float) (percentageNinfa - .01);
				percentageAdult = (float) (percentageAdult - .005);
			}
			if(change > 11 && change < 30) {

				percentageNinfa = (float) (percentageNinfa - .015);
				percentageAdult = (float) (percentageAdult - .075);
			}
			
		}

		System.out.println("");
		System.out.println("");
		System.out.println("Antes del cambio: ");
		getLeslieMatrix(array,preLeslieMatrix,insectsH,insectsN,insectsA);
		
		preLeslieMatrix[1][0]= (float) percentageNinfa;
		preLeslieMatrix[2][1]= (float) percentageAdult;

		System.out.println("");
		System.out.println("");
		System.out.println("Despues del cambio: ");

		getLeslieMatrix(array,preLeslieMatrix,insectsH,insectsN,insectsA);
		printLeslieMatrix(preLeslieMatrix);

		System.out.println("");
		
	}
	public synchronized void syncChangeLeslieMatrixFeed(int type, int change) {
		//percentage
		int huevosNinfa = huevosNinfaInitial;
		int huevosAdulto = huevosAdultoInitial;
		float percentageNinfa = (float) .6;
		float percentageAdult = (float) .5;
		
		if(type == 0) {
			//feed changes
			if(change < 3) {
				huevosAdulto = huevosAdultoInitial-2;
				huevosNinfa = huevosNinfaInitial-2;
			}else{
				huevosNinfaInitial= 18;
				huevosAdultoInitial = 31;
			}
		
		}else{
			// Light changes
			if(change == 0) {
				huevosNinfa = 21;
				huevosAdulto = 39;
			}else {

				huevosAdulto = 17;
				huevosNinfa = 28;
				
			}
		}
		

		System.out.println("");
		System.out.println("");
		System.out.println("Antes del cambio: ");

		getLeslieMatrix(array,preLeslieMatrix,insectsH,insectsN,insectsA);
		
		preLeslieMatrix[0][1]= huevosNinfa;
		preLeslieMatrix[0][2]= huevosAdulto;
		preLeslieMatrix[1][0]= (float) percentageNinfa;
		preLeslieMatrix[2][1]= (float) percentageAdult;
		System.out.println("");
		System.out.println("");
		System.out.println("Despues del cambio: ");

		getLeslieMatrix(array,preLeslieMatrix,insectsH,insectsN,insectsA);
		printLeslieMatrix(preLeslieMatrix);

		System.out.println("");
		
		
	}
	
	void getLeslieMatrix (float array[], float [][] leslie, int insectsH, int insectsN, int insectsA) {
		array[0]= (leslie[0][0]*insectsH)+(leslie[0][1]*insectsN)+(leslie[0][2]*insectsA);
		array[1]= (leslie[1][0]*insectsH)+(leslie[1][1]*insectsN)+(leslie[1][2]*insectsA);
		array[2]= (leslie[2][0]*insectsH)+(leslie[2][1]*insectsN)+(leslie[2][2]*insectsA);
		
		System.out.println("M: La cantidad de insectos esperados para este año es huevo: "+array[0]+", ninfa: "+array[1]+", adulto: "+array[2]);
		
	}
	
	void printLeslieMatrix(float [][] leslie) {
		System.out.println("Leslie Matrix");
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(leslie[i][j]+" ");
			}
			System.out.println("");
		}
	}
	
	@Override
	public void run() {
		preLeslieMatrix[0][0]= 0;
		preLeslieMatrix[0][1]= 18;
		preLeslieMatrix[0][2]= 31;
		preLeslieMatrix[1][0]= (float) 0.6;
		preLeslieMatrix[1][1]= 0;
		preLeslieMatrix[1][2]= 0;
		preLeslieMatrix[2][0]= 0;
		preLeslieMatrix[2][1]= (float) 0.5;
		preLeslieMatrix[2][2]= 0;
		
		while(true) {
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
	    }
		
		
	}

}
