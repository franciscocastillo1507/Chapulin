import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	
	

	public static void main(String[] args) throws InterruptedException {
		
		Metrics metrics = new Metrics();
        System.out.println("Bienvenido a Chapulin ingresa los datos de tu granja y veamos como esta ");
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Escribe el estimado de insectos que tienes en la fase de desarrollo (Huevo) ");
        
        int insectsH = scan.nextInt();
        metrics.set_insectsH(insectsH);


        System.out.println("Escribe el estimado de insectos que tienes en la fase de desarrollo (Ninfa) ");
        
        int insectsN = scan.nextInt();
        metrics.set_insectsN(insectsN);
        
        
        System.out.println("Escribe el estimado de insectos que tienes en la fase de desarrollo (Huevo) ");
        
        int insectsA = scan.nextInt();
        metrics.set_insectsA(insectsA);
   
        System.out.println("Haciendo el Set Up para los calculos");
        Thread.sleep(100);
        
        
		ExecutorService pool = Executors.newFixedThreadPool(5); 
		Temperature temp = new Temperature();
		Humidity humidity = new Humidity();
		Light light = new Light();
		Feed feed = new Feed();
		
		temp.set_metrics(metrics);
		feed.set_metrics(metrics);
		humidity.set_metrics(metrics);
		light.set_metrics(metrics);
		
		
		
		
		pool.execute(temp);
		pool.execute(humidity);
		pool.execute(light);
		pool.execute(metrics);
		pool.execute(feed);
		

	}

}
