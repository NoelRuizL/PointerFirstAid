package com.softallica.pfa;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/** 
 * CLASE que servira para guardar los objetos que obtenemos de google Places API
 * de aqui se podra extender la clase a una mas detallada con mas información de cada sitio
 * 
 * 
 * */
public class googlePlaces {

	private String name;
	private double latitude, longitude;
	private String id;
	private String placeId;
	private String vicinity;
	private double rating;
	private Boolean open = true;
	private String weekday;
	private String reference;
	private String markerId;
	
	public googlePlaces(JSONObject place){
	
		try{
			this.setName(place.getString("name"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		try{
			this.setLat(((JSONObject) ((JSONObject) place.get("geometry")).get("location")).getDouble("lat"));
		
		}catch(JSONException e){
			e.printStackTrace();
		}

		try{
			this.setLng(((JSONObject) ((JSONObject) place.get("geometry")).get("location")).getDouble("lng"));
		
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		try{
			this.setId(place.getString("id"));
		}catch(JSONException e){
			e.printStackTrace();
		}	
		
		try{
			this.setPlaceId(place.getString("place_id"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		try{
			this.setVicinity(place.getString("vicinity"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		try{
			this.setRating(place.getDouble("rating"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		try{
			this.setOpen(	((JSONObject) place.get("opening_hours")).getString("open_now"));
		}catch(JSONException e){
			//No todos los resultados devueltos desde el JSON poseen este dato, si entro aqui
			//Es porque no lo ha encontrado, que suele pasar cuando el sitio siempre esta abierto, 
			// es decir, tiene horario de urgencias 24h. lo capturo, y ssigue
			
		}
		
		/**
		try{
			this.setRating(((JSONObject) place.get("opening_hours")).getString("weekday_text"));
		}catch(JSONException e){
			e.printStackTrace();
		}	
		*/
		
		try{
			this.setReference(place.getString("reference"));
		}catch(JSONException e){
			e.printStackTrace();
		}
			
					
		
	}
	
	
	public void setLat(double l){
		latitude = l;
	}
	public void setLng(double l){
		longitude = l;
	}
	public void setId(String s){
		id = s;
	}
	public void setName(String n){
		name = n;
	}
	public void setPlaceId(String pi){
		placeId = pi;
	}
	public void setVicinity(String v){
		vicinity = v;
	}
	public void setRating(double r){
		rating = r;
	}
	public void setOpen(String o){
		
		if (o.compareTo("true") == 0 )
			open = true;
		else
			open = false;
		
	}
	public void setWeekday(String wdt){
		weekday = wdt;
	}
	public void setReference(String r){
		reference = r;
	}
	public String getId(){
		return id;
	}
	public double getLat(){
		return latitude;
	}
	public double getLng(){
		return longitude;
	}
	public String getName(){
		return name;
	}
	public String getPlaceId(){
		return placeId;
	}
	public String getVicinity(){
		return vicinity;
	}
	public double getRating(){
		return rating;
	}
	public Boolean getOpen(){
		return open;
	}
	public String getWeekday(){
		return weekday;
	}
	public String getReference(){
		return reference;
	}

	public String getMarkerId(){
		return markerId;
	}
	
	public void addgMapMarker(GoogleMap g){
		
		 Marker m = g.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.img01)).position(new LatLng(latitude,longitude)).title("HOSPITAL").snippet(name));
		 m.showInfoWindow();
		 
		 this.markerId = m.getId();
	}
	
}
