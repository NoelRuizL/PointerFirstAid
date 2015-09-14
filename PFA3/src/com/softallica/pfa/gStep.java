package com.softallica.pfa;

import org.json.JSONException;
import org.json.JSONObject;

//import android.R.color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class gStep {
	
	private String html_instructions; //contains formatted instructions for this step, presented as an HTML text string.
	private Double distance_value; //contains the distance covered by this step until the next step. (See the discussion of this field in Directions Legs above.) This field may be undefined if the distance is unknown.
	private String distance_text; 
	private Double duration_value; //contains the typical time required to perform the step, until the next step. (See the description in Directions Legs above.) This field may be undefined if the duration is unknown.
	private String duration_text; 
	private LatLng start_location; //contains the location of the starting point of this step, as a single set of lat and lng fields.
	private LatLng end_location; //contains the location of the last point of this step, as a single set of lat and lng fields.
	private String polyline; //contains a single points object that holds an encoded polyline representation of the step. This polyline is an approximate (smoothed) path of the step.
	private String steps; //contains detailed directions for walking or driving steps in transit directions. Substeps are only available when travel_mode is set to "transit". The inner steps array is of the same type as steps.
	private String transit_details; //contains transit specific information. This field is only returned with travel_mode is set to "transit". See Transit Details below.
	
	
	
	public gStep(JSONObject s){
		
		try{
			this.setHTMLInstructions(s.getString("html_instructions"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
		try{
			this.setDistance(((JSONObject)  s.get("distance")).getDouble("value"), ((JSONObject)  s.get("distance")).getString("text") );
		}catch(JSONException e){
			e.printStackTrace();
		}
		try{
			this.setDuration(((JSONObject)  s.get("duration")).getDouble("value"), ((JSONObject)  s.get("duration")).getString("text") );
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		try{
			this.setStartLocation(((JSONObject)  s.get("start_location")).getDouble("lat"), ((JSONObject)  s.get("start_location")).getDouble("lng"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		try{
			this.setEndLocation(((JSONObject)  s.get("end_location")).getDouble("lat"), ((JSONObject)  s.get("end_location")).getDouble("lng"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		try{
			this.setPolyline( ((JSONObject )s.get("polyline")).getString("points"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
		try{
			this.setSteps(s.getString("steps"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		try{
			this.setTransitDetails(s.getString("transit_details"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
	}

	
	/** SET Methods */
	public void setHTMLInstructions(String s){
		html_instructions = s;
		
	}
	public void setDistance(Double d, String s){
		distance_text = s;
		distance_value = d;
	}
	public void setDuration(Double d, String s){
		duration_text = s;
		duration_value = d;
	}
	public void setStartLocation(Double latitude, Double longitude){
		start_location = new LatLng(latitude, longitude);
		
	}
	public void setEndLocation(Double latitude, Double longitude){
		end_location = new LatLng(latitude, longitude);
		
	}
	public void setPolyline(String s){
		polyline = s;
	}
	public void setSteps(String s){
		steps = s;
	}
	public void setTransitDetails(String s){
		transit_details = s;
	}
	
	/** GET Methods */
	public String getHTMLInstructions(){
		return html_instructions;
		
	}
	public Double getDistance(){
		return distance_value;
	}
	public Double getDuration(){
		return duration_value;
	}
	
	public LatLng getStartLocation(){
		return start_location;
		
	}
	public LatLng getEndLocation(){
		return end_location;
		
	}
	
	public String getPolyline(){
		return polyline;
	}
	
	public String getSteps(String s){
		return steps;
	}
	
	public String getTransitDetails(){
		return transit_details;
	}
	
	public void drawStep(GoogleMap mMap){
		PolylineOptions POLILINEA = new PolylineOptions()
        .add(start_location)
        .add(end_location);
		
		POLILINEA.width(3);
		//POLILINEA.color(color.darker_gray);
		
		mMap.addPolyline(POLILINEA);
		
	}
}
