package com.softallica.pfa;

import java.lang.reflect.Array;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class gLeg{
	private gStep[] steps; 	//contains an array of steps denoting information about each separate step of the leg of the journey. (See Directions Steps below.)
	
	/** Distance indicates the total distance covered by this leg, as a field with the following elements: */
	private double dist_value; 	// indicates the distance in meters
	private String dist_text; 	// contains a human-readable representation of the distance, displayed in units as used at the origin (or as overridden within the units parameter in the request). (For example, miles and feet will be used for any origin within the United States.) Note that regardless of what unit system is displayed as text, the distance.value field always contains a value expressed in meters.
									// These fields may be absent if the distance is unknown.

	/** Duration indicates the total duration of this leg, as a field with the following elements: */
	private double dur_value; // indicates the duration in seconds.
	private String dur_text;   // contains a human-readable representation of the duration.
								   // These fields may be absent if the duration is unknown.

	/** duration_in_traffic indicates the total duration of this leg, taking into account current traffic conditions. The duration in traffic will only be returned if all of the following are true:
	 The directions request includes a departure_time parameter set to a value within a few minutes of the current time.
	  	1 - The request includes a valid Google Maps API for Work client and signature parameter.
	  	2 - Traffic conditions are available for the requested route.
	  	3 - The directions request does not include stopover waypoints.
	    4 - The duration_in_traffic will contain the following fields:  */

	private double dit_value; // indicates the duration in seconds.
	private String dit_text;  // contains a human-readable representation of the duration.

	/** arrival_time contains the estimated time of arrival for this leg. 
		This property is only returned for transit directions. 
		The result is returned as a Time object with two properties:    */
	
	private String at_text;   // the time specified as a string. The time is displayed in the time zone of the transit stop.
	private String time_zone; //contains the time zone of this station. The value is the name of the time zone as defined in the IANA Time Zone Database, e.g. "America/New_York".
	
		
	private String departure_time; 	// contains the estimated time of departure for this leg, specified as a Time object. The departure_time is only available for transit directions.
	private LatLng start_location; 	// contains the latitude/longitude coordinates of the origin of this leg. Because the Directions API calculates directions between locations by using the nearest transportation option (usually a road) at the start and end points, start_location may be different than the provided origin of this leg if, for example, a road is not near the origin.
	private LatLng end_location;  	// contains the latitude/longitude coordinates of the given destination of this leg. Because the Directions API calculates directions between locations by using the nearest transportation option (usually a road) at the start and end points, end_location may be different than the provided destination of this leg if, for example, a road is not near the destination.
	private String start_address; 	// contains the human-readable address (typically a street address) reflecting the start_location of this leg.
	private String end_address;  	//contains the human-readable address (typically a street address) reflecting the end_location of this leg.
	
	public gLeg(JSONObject leg){
	
		try{
			this.setGSteps((JSONArray) leg.get("steps"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
		try{
			this.setDuration(((JSONObject)  leg.get("duration")).getDouble("value"), ((JSONObject)  leg.get("duration")).getString("text"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		try{
			this.setDistance(((JSONObject)  leg.get("distance")).getDouble("value"), ((JSONObject)  leg.get("distance")).getString("text"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		try{
			this.setTrafficDuration(((JSONObject)  leg.get("duration_in_traffic")).getDouble("value"), ((JSONObject)  leg.get("duration_in_traffic")).getString("text"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
		try{
			this.setArrivalTime(((JSONObject)  leg.get("arrival_time")).getString("time_zone"), ((JSONObject)  leg.get("arrival_time")).getString("text"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		try{
			this.setStartLocation(((JSONObject)  leg.get("start_location")).getDouble("lat"), ((JSONObject)  leg.get("start_location")).getDouble("lng"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		try{
			this.setEndLocation(((JSONObject)  leg.get("end_location")).getDouble("lat"), ((JSONObject)  leg.get("end_location")).getDouble("lng"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		try{
			this.setDepartureTime(leg.getString("departure_time"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
		try{
			this.setStartAddress(leg.getString("start_address"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		try{
			this.setEndAddress(leg.getString("end_address"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
	}
	
	
	public void setGSteps(JSONArray l){
		
		steps = new gStep[l.length()];
		
		for(int i=0; i < l.length(); i++){
			try{
				steps[i] = new gStep((JSONObject) l.get(i));
			}catch(JSONException e){
				e.printStackTrace();
			}	
		}

	}
	
	
	
	public void setDuration(Double d, String s){
		dur_value = d;
		dur_text = s;
	}
	
	public void setDistance(Double d, String s){
		dist_value = d;
		dist_text = s;
	}
	
	public void setTrafficDuration(Double d, String s){
		dit_value = d;
		dit_text = s;
		
	}
	public void setArrivalTime(String s1, String s2){
		time_zone = s1;
		at_text = s2;
	}
	
	
	public void setStartLocation(Double latitude, Double longitude){
		start_location = new LatLng(latitude, longitude);
		
	}
	public void setEndLocation(Double latitude, Double longitude){
		end_location = new LatLng(latitude, longitude);
		
	}
	
	public void setStartAddress(String s){
		start_address = s;
	}
	
	public void setEndAddress(String s){
		end_address = s;
	}
	
	public void setDepartureTime(String s){
		departure_time = s;
	}
	/** 
	 * El método devuelve un Array de Strings con:
	 * array[0] en la primera casilla un Double de valor en segundos
	 * array[1] texto legible sobre duracion
	 * */
	public String[] getDuration(Double d, String s){
		String[] array = new String[2];
		
		
		array[0] = Double.toString(dur_value);
		array[1] = dur_text;
		
		return array;
	}
	
	
	/** 
	 * El método devuelve un Array de Strings con:
	 * array[0] en la primera casilla un Double de valor en metros
	 * array[1] texto legible sobre distancia
	 * */
	public String[] getDistance(Double d, String s){
		String[] array = new String[2];
		
		
		array[0] = Double.toString(dist_value);
		array[1] = dist_text;
		
		return array;
	}
	
	/** 
	 * El método devuelve un Array de Strings con:
	 * array[0] en la primera casilla un Double de valor en segundos
	 * array[1] texto legible sobre duracion
	 * */
	public String[] getTrafficDuration(Double d, String s){
		String[] array = new String[2];
		
		
		array[0] = Double.toString(dit_value);
		array[1] = dit_text;
		
		return array;
	}
	
	
	public LatLng getStartLocation(){
		return start_location;
	}
	
	public LatLng getEndLocation(){
		return end_location;
	}
	
	
	public String getStartAddress(){
		return start_address;
	}
	
	public String getEndAddress(){
		return end_address;
	}
	public String getDepartureTime(){
			return departure_time;
	
	}
	public String getTimeZone(){
		return time_zone;
	
	}
	
	public String getArrivalTime(){
		return at_text;
	
	}
	
	public void drawLeg(GoogleMap mMap){
		for(int i = 0; i < steps.length; i++){
			steps[i].drawStep(mMap);
		}
	}
}
