import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.reflect.*;

public class Humidity implements Runnable{

	float sensorHumidity = 0.0f;
	float outsideHumidity = 0.0f;
	float estimatedHumidity = 70.0f;
	
	public void setSensorHumidity(float sensorHumidity) {
		this.sensorHumidity = sensorHumidity;
	}
	
	String API_KEY = "1aa8184ca8efba9ee17ecd5aa9ba6bab";
  String LOCATION = "Queretaro,mx";
//	String LOCATION = "Sonora,US";
    String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&APPID=" + API_KEY + "&units=metric";
	
	
    
    public static Map<String,Object> jsonToMap(String str){
        Map<String,Object> map = new Gson().fromJson(str,new 
    TypeToken<HashMap<String,Object>> () {}.getType());
        return map;
    }
    
    Metrics metrics;
	
	void set_metrics(Metrics metrics) {
		this.metrics = metrics;
	}

    //todo afecta la tempuratura la matriz
	@Override
	public void run() {
		try {
			System.out.println("H: Humidity initialized");
		    Thread.sleep(500);
		    
		    StringBuilder result = new StringBuilder();
	        URL url = new URL(urlString);
	        URLConnection conn = url.openConnection();
	    	BufferedReader rd = new BufferedReader(new InputStreamReader (conn.getInputStream()));
	        String line;
	        while ((line = rd.readLine()) != null){
	            result.append(line);
	        }
			rd.close();
		    
		    
		    while(true) {
		    	
		    	Map<String, Object > respMap = jsonToMap (result.toString());
		        Map<String, Object > mainMap = jsonToMap (respMap.get("main").toString());
//		        System.out.println("Current Humidity: " + mainMap.get("humidity")  );
		        Double temp = (Double) mainMap.get("humidity");
		        outsideHumidity = temp.floatValue();
		        
		        
		    	Thread.sleep(500);
		    	// Simulated
				float min = 68;
			    float max = 80;
			    float randomHumidity = (float)Math.floor(Math.random()*(max-min+1)+min);
			    sensorHumidity = randomHumidity;
			    Thread.sleep(500);
		    	
		    	// Humidity Simulation
		    	if(outsideHumidity > estimatedHumidity+8 || outsideHumidity < estimatedHumidity-8) {
		    		if(outsideHumidity > estimatedHumidity+8) {
		    			float differenceOfHumidity = outsideHumidity - (estimatedHumidity+8);
	    		    	metrics.syncChangeLeslieMatrixTemperature(1, differenceOfHumidity);
		    			if(differenceOfHumidity > 8.0f) {
				    		System.out.println("H: Be careful outside its pretty humid it has "+differenceOfHumidity+"  upper");
		    			}
		    		}else {
		    			float differenceOfHumidity = (estimatedHumidity-8) - outsideHumidity;
	    		    	metrics.syncChangeLeslieMatrixTemperature(1, differenceOfHumidity);
		    			if(differenceOfHumidity > 8.0f) {
				    		System.out.println("H: Be careful outside its not that humid it has "+differenceOfHumidity+"  under");
		    			}
		    		}
		    	}
		    	if(estimatedHumidity+8 < sensorHumidity || estimatedHumidity-8 > sensorHumidity) {
		    		// Must regulate urgently or insects will die
		    		if(sensorHumidity > estimatedHumidity+8) {
		    			// Must regulate urgently its pretty hot
		    			float differenceOfHumidity = sensorHumidity - (estimatedHumidity+8);
		    			if(differenceOfHumidity > 8.0f) {
		    				var upper = estimatedHumidity-8;
		    		    	metrics.syncChangeLeslieMatrixTemperature(1, differenceOfHumidity);
				    		System.out.println("H: Urgent you must regulate inside Humidity to  "+upper+" right  now is "+sensorHumidity+ " a diff of "+ differenceOfHumidity);
		    			}
		    		}else {
		    			float differenceOfHumidity = (estimatedHumidity-8) - sensorHumidity;
		    			if(differenceOfHumidity > 8.0f) {
		    				var under = estimatedHumidity-8;
		    		    	metrics.syncChangeLeslieMatrixTemperature(1, differenceOfHumidity);
				    		System.out.println("H: Urgent you must regulate inside Humidity to  "+under+" right  now is "+sensorHumidity+ " a diff of "+ differenceOfHumidity);
		    			}
		    		}
		    	}else {
		    		System.out.println("H: Humidity is in the right number "+sensorHumidity);
		    	}
		    	
		    }
		}
		catch (InterruptedException e) {

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
