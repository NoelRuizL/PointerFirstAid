/** 
 * 
 * Clase para consultar servicios Web, preparada en principio para la respuesta de googlemaps
 * previa pregunta sobre localización 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * **/




package com.softallica.pfa;



import java.io.IOException;
/*import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;*/




import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/*import android.content.Context;
import android.content.res.Resources;*/
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.json.*;

//Obtiene un JSON a traves de una petición por url (Servicio WEB) y lo parsea según convenga

public class JSONConverter{
	
	//private String url="http://maps.google.com/maps?f=q&source=s_q&output=json&start=0&q=";
	private String url;
	protected String q;
	protected String res;

	
	public JSONConverter(String u){
		url = u;
		res = "";	
	}
	
	private class Connection extends AsyncTask {
		 
        @Override
        protected Object doInBackground(Object... arg0) {
            connect();
            return null;
        }
 
    }
 
    private void connect() {
    
    	try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
                                 
            res = EntityUtils.toString(response.getEntity());
            
        } catch (ClientProtocolException e) {
            Log.d("HTTPCLIENT", e.getLocalizedMessage());
        } catch (IOException e) {
            Log.d("HTTPCLIENT", e.getLocalizedMessage());
        }
    }
	
	public String mapSearch2JSON()
    {
     
        try {
            //Permisos para que funcione con las versioens adeciadas de Android
        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);

        	connect();

        	//falta parametrizar
        	new Connection().execute();
        	        	        	
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        return res;
    }
	
	//Esto deberia ir en googlePlaces
	
	public static googlePlaces[] jsonParserToGooglePlaces(String json)
    {
		googlePlaces[] g = null;
		
        try{
        	JSONObject jsonObject = new JSONObject(json);
        	        
        	JSONArray arrayOfPlaces = (JSONArray) jsonObject.get("results");

        	g = new googlePlaces[arrayOfPlaces.length()];
        	
        	//coger el nombre  
        	for (int i = 0; i < arrayOfPlaces.length(); i++) {
        	    g[i] = new googlePlaces((JSONObject) arrayOfPlaces.get(i));
        	} 
        	
        }catch(Exception e){
        	e.printStackTrace();
        }      
        return g;
    }
	
	
	public static googleDirections[] jsonParserToGoogleDirections(String json)
    {
		
		googleDirections[] g = null;
		
        try{
        	JSONObject jsonObject = new JSONObject(json);
        	        
        	JSONArray arrayOfPlaces = (JSONArray) jsonObject.get("routes");

        	g = new googleDirections[arrayOfPlaces.length()];
        	
        	//coger el nombre  
        	for (int i = 0; i < arrayOfPlaces.length(); i++) {
        	    g[i] = new googleDirections((JSONObject) arrayOfPlaces.get(i));
        	} 
        	
        }catch(Exception e){
        	e.printStackTrace();
        }      
        return g;
        
    }
	
}
