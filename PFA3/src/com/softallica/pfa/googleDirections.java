package com.softallica.pfa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;

public class googleDirections {

	private String STATUS;
	
	
	private String summary; 			//contains a short textual description for the route, suitable for naming and disambiguating the route from alternatives.
	private gLeg[] legs; 				//contains an array which contains information about a leg of the route, between two locations within the given route. A separate leg will be present for each waypoint or destination specified. (A route with no waypoints will contain exactly one leg within the legs array.) Each leg consists of a series of steps. (See Directions Legs below.)
	private Double[] waypoint_order; 	//contains an array indicating the order of any waypoints in the calculated route. This waypoints may be reordered if the request was passed optimize:true within its waypoints parameter.
	private String polyline;		 	//contains a single points object that holds an encoded polyline representation of the route. This polyline is an approximate (smoothed) path of the resulting directions.
	private String bounds;/** */		//contains the viewport bounding box of the overview_polyline.
	 private double origen_lat;
	 private double origen_lon;
	 private double destino_lat;
	 private double destino_lon;
	 private String cardinal_origen;  
	 private String cardinal_destino;
	
	private String copyrights; 			//contains the copyrights text to be displayed for this route. You must handle and display this information yourself.
	private String[] warnings; 			//contains an array of warnings to be displayed when showing these directions. You must handle and display these warnings yourself.
	private Boolean isFare;				//: If present, contains the total fare (that is, the total ticket costs) on this route. This property is only returned for transit requests and only for routes where fare information is available for all transit legs. The information includes:
	private String moneda;				// 		- An ISO 4217 currency code indicating the currency that the amount is expressed in.
	private Double value;				//		- The total fare amount, in the currency specified above.
	
		
	public googleDirections(JSONObject gRoute){

		try{
			this.setSummary(gRoute.getString("summary"));
		}catch(JSONException e){
			e.printStackTrace();
		}

		
		try{
			this.setGLegs((JSONArray) gRoute.get("legs"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		try{
			this.setPolyline(gRoute.getString("polyline"));
		}catch(JSONException e){
			polyline = e.getMessage();
		}
		
		try{
			this.setBounds((JSONObject) gRoute.get("bounds"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		try{
			this.setCopyrights(gRoute.getString("copyrights"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		try{
			this.setWarnings(gRoute.getJSONArray("warnings"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		//Consulto si existen datos de precios
		isFare = false;
		try{
			JSONObject j = (JSONObject) gRoute.get("fare");
			//Si sigue es que no captura excepción por lo que existe el valor.
			this.setIsFare();
			value = Double.valueOf(j.getString("value"));
			moneda = j.getString("currency");
			
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
	}
	 
	public void setSummary(String s){
		summary = s;
	}

	public void setGLegs(JSONArray l){
		
		legs = new gLeg[l.length()];
		
		for(int i=0; i < l.length(); i++){
			try{
				legs[i] = new gLeg((JSONObject) l.get(i));
			}catch(JSONException e){
				e.printStackTrace();
			}	
		}
		//Movida este, hay que coger todos los legs de forma parecida
	}
	
	public void setPolyline(String pl){
		polyline = pl;
	}
	public void setBounds(JSONObject b){
		//Movida dos, debo rellenar con esto los puntos origen y destino
	}
	public void setCopyrights(String c){
		copyrights = c;
	}
	public void setWarnings(JSONArray w) throws JSONException{
		//Para la longitud de warnings crel el array
		warnings = new String[w.length()];
		//Y ahora uno a uno los meto en el array de warnings
		for(int i=0; i < w.length(); i++){
			try{
				warnings[i] =  (String) w.get(i);
			}catch(JSONException e){
				warnings[i] = "";
			}	
		}
	}
	
	public void setIsFare(){
		isFare= true;
	}
	public void setMoneda(String m){
		moneda = m;
	}
	public void setValue(Double v){
		value = v;
	}
	
	public String getSummary(){
		if (summary.isEmpty())
			summary="No summary";
		return summary;
	}
	public gLeg[] getLegs(){
		return legs;
	}
	public String getPolyline(){
		return polyline;
	}
	
	public String getCopyrights(){
		return copyrights;
	}

	public String[] getWarningsArray(){
		return warnings;
	}
	
	public String getFares(){
		
		String message = "No information";
		
		if(isFare == true)
			message = ((String) Double.toString(value).concat(" ")).concat(moneda);
			
		return message;
	}
	
	public void drawDirection(GoogleMap mMap){
		for(int i = 0; i < legs.length; i++){
			 legs[i].drawLeg(mMap);
		}
		
	}
	
	
}

