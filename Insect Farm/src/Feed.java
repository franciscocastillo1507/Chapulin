import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

public class Feed implements Runnable {
	
	boolean dryFood = false;
	boolean food = false;
	int vegetables = 2;
	int dry = 1;
	Metrics metrics;
	
	void set_metrics(Metrics metrics) {
		this.metrics = metrics;
	}

	
    //todo afecta la tempuratura la matriz
	@Override
	public void run() {
		try {

		    Thread.sleep(500);
			int min = 3600*2;
			int max; 
			if(metrics.insectsA+metrics.insectsH+metrics.insectsN > 350) {
				max = 4500*2;
			}else {
			    max = 7200*2;
			}
		    int sleepTime = ((int)Math.floor(Math.random()*(max-min+1)+min)*100)/60;
			System.out.println("F: Feed initialized");

		    Thread.sleep(500);
		    while(true) {
		    	Thread.sleep(500);
		    	Thread.sleep(sleepTime);
		    	dry = dry-1;
		    	vegetables = vegetables -1;
		    	dryFood = true;
		    	food = true;
			    if(food == true) {
			    	System.out.println("F: Crickets need a vegetable they already eat one");
			    	Thread.sleep(1000);
			    	if(vegetables % 2 == 0)
			    	vegetables++;
			    }
			    if(dryFood == true) {
			    	System.out.println("F: Crickets need a dry vegetable they already eat one");
			    	Thread.sleep(1000);
			    	if(dry % 2 == 0)
			    	dry++;
			    }
			    
			    metrics.syncChangeLeslieMatrixFeed(0, dry+vegetables);
			    
		    }
		}
		catch (InterruptedException e) {

		}
		
	}

}
