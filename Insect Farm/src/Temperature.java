import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.reflect.*;

public class Temperature implements Runnable{
	
	float sensorTemperature = 0.0f;
	float outsideTemperature = 0.0f;
	float estimatedTemperature = 28.0f;
	
	String API_KEY = "1aa8184ca8efba9ee17ecd5aa9ba6bab";
    String LOCATION = "Queretaro,mx";
//    String LOCATION = "Sonora,US";
    String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&APPID=" + API_KEY + "&units=metric";
	
	
    
    public static Map<String,Object> jsonToMap(String str){
        Map<String,Object> map = new Gson().fromJson(str,new 
    TypeToken<HashMap<String,Object>> () {}.getType());
        return map;
    }
    public void setSensorTemperature(float sensorTemperature) {
		this.sensorTemperature = sensorTemperature;
	}
    int count = 0;
    
    Metrics metrics;
	
	void set_metrics(Metrics metrics) {
		this.metrics = metrics;
	}
    //todo afecta la tempuratura la matriz
	@Override
	public void run() {
		try {
			System.out.println("T: Temperature initialized");
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
		    	 count++;

		        Map<String, Object > respMap = jsonToMap (result.toString());
		        Map<String, Object > mainMap = jsonToMap (respMap.get("main").toString());
		        Map<String, Object > windMap = jsonToMap (respMap.get("wind").toString());

//		        System.out.println(count + " Current Temperature: " + mainMap.get("temp")  );
//		        System.out.println("Current Humidity: " + mainMap.get("humidity")  );
//		        System.out.println("Wind Speed: " + windMap.get("speed")  );
//		        System.out.println("Wind Angle: " + windMap.get("deg")  );
		        Double temp = (Double) mainMap.get("temp");
		        outsideTemperature = temp.floatValue();
		    	Thread.sleep(500);
		        System.out.println("T: Current Outside Temperature: " + outsideTemperature );
		    	
		    	// Simulated
				float min = 22;
			    float max = 32;
			    float randomTemperature = (float)Math.floor(Math.random()*(max-min+1)+min);
		    	sensorTemperature = randomTemperature;
		    	Thread.sleep(500);
		    	
		    	// Temperature Simulation
		    	if(outsideTemperature > estimatedTemperature+2 || outsideTemperature < estimatedTemperature-2) {
		    		if(outsideTemperature > estimatedTemperature+2 ) {
		    			float differenceOfTemperature = outsideTemperature - (estimatedTemperature+2);
	    		    	metrics.syncChangeLeslieMatrixTemperature(0, differenceOfTemperature);
		    			if(differenceOfTemperature > 2.0f) {
				    		System.out.println("T: Be careful outside its pretty hot it has "+differenceOfTemperature+" degree upper");
		    			}
		    		}else {
		    			float differenceOfTemperature = (estimatedTemperature-2) - outsideTemperature;
	    		    	metrics.syncChangeLeslieMatrixTemperature(0, differenceOfTemperature);
		    			if(differenceOfTemperature > 2.0f) {
				    		System.out.println("T: Be careful outside its pretty cold it has "+differenceOfTemperature+" degree under");
		    			}
		    		}
		    	}
		    	if(estimatedTemperature+2 < sensorTemperature || estimatedTemperature-2 > sensorTemperature) {
		    		// Must regulate urgently or insects will die
		    		if(sensorTemperature > estimatedTemperature+2) {
		    			// Must regulate urgently its pretty hot
		    			float differenceOfTemperature = sensorTemperature - (estimatedTemperature+2);
	    		    	metrics.syncChangeLeslieMatrixTemperature(0, differenceOfTemperature);
		    			if(differenceOfTemperature > 2.0f) {
		    				var upper = estimatedTemperature-2;
				    		System.out.println("T: Urgent you must regulate sensor temperature to  "+upper+" right  now is "+sensorTemperature+ "a diff of "+ differenceOfTemperature);
		    			}
		    		}else {
		    			float differenceOfTemperature = (estimatedTemperature-2) - sensorTemperature;
	    		    	metrics.syncChangeLeslieMatrixTemperature(0, differenceOfTemperature);
		    			if(differenceOfTemperature > 2.0f) {
		    				var under = estimatedTemperature-2;
				    		System.out.println("T: Urgent you must regulate sensor temperature to  "+under+" right  now is "+sensorTemperature+ "a diff of "+ differenceOfTemperature);
		    			}
		    		}
		    	}else {
		    		System.out.println("T: Temperature is in the right number "+sensorTemperature);
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
