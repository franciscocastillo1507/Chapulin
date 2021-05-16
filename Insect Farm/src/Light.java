import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

public class Light implements Runnable{

	float lastLight = 18;
	float initialLight = 8;
	boolean turnOff = false;
	String clear;
	
	public void setLastHour(float lastLight) {
		this.lastLight = lastLight;
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

    int isOk = 0;

    //todo afecta la tempuratura la matriz
	@Override
	public void run() {
		try {

			System.out.println("L: Lights initialized");
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
		    Thread.sleep(500);
		    
		    
		    while(turnOff == false) {

		    	Thread.sleep(500);
		    	Map<String, Object > respMap = jsonToMap (result.toString());
		    	ArrayList<Map<String, Object>> weathers = (ArrayList<Map<String, Object>>) respMap.get("weather");
//		        System.out.println("Current Day: " + weathers.get(0).get("description") );
		       
		        clear =  (String) weathers.get(0).get("description");
		    	
				int currentH = LocalDateTime.now().getHour();
				
			    if(currentH > initialLight && currentH < lastLight) {
//					 System.out.println(" Current H: " + currentH );
			    }else {
			    	isOk = 1;
			    	turnOff = true;
			    	System.out.println("L: Crickets had already too much light, let em rest");
			    }

		        System.out.println("L: Current Day: " + clear +"");
			    if(clear.contentEquals("clear sky") ) {

			    	System.out.println("L: Sky is clear crickets can be outside");
			    	isOk = 0;
			    	
			    }
			    else {
			    	System.out.println("L: Watch out the weather is no longer safe for the crickets");
			    	System.out.println("L: Put in on a safe spot");
			    	isOk = 1;
			    }
//			    if(clear != "clear sky") {
//			    	
//			    	
//			    }else {
//			    	
//			    }
			    
			    metrics.syncChangeLeslieMatrixFeed(1, isOk);
		    }
		}
		catch (InterruptedException e) {

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
