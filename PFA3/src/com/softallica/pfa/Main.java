package com.softallica.pfa;

import android.content.Context;
import android.content.DialogInterface;

//import java.util.ArrayList;

import com.softallica.pfa.JSONConverter;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
//import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class Main extends Activity {
	
	// Definimos la variable GLOBAL antes del método OnCreate
	public GoogleMap mapa;
	//Datos movil residente
//	private int mvHeight;
//	private int mvWidth; 
	//Animacion
	private ImageView ivAnimacion;
	private AnimationDrawable savingAnimation;
//	private Button btnIniciar, btnDetener;
	//Contexto
	private Context ctx;
	private String jsonString;
	//Array de sitos de google
	private  googlePlaces[] pfa;
	private googleDirections[] pfaDirection;
	//String de coordenadas destino
	private String origen = "37.18670522,-3.58"; 
	private String destino;
	    
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.screenSize();
		//Falta cambiar pantalla según parametros de entrada
		ctx = this;
		
		setContentView(R.layout.main);
		
		//Activamos la animación del Logo
		ivAnimacion = (ImageView)findViewById(R.id.iv_animacion);
	    ivAnimacion.setBackgroundResource(R.drawable.animacion);
	    savingAnimation = (AnimationDrawable)ivAnimacion.getBackground();
	    savingAnimation.start();
	    
	    //Inicializamos Mapa
	    this.initMapa();
	    
	    
	    //Llamada a WSGoogleMap y obtención de centros cercanos
	    jsonString = this.getWSResponse(this.composeURLrequestGooglePlaces());
	    
	    //Parseo de la respuesta paragPlaces
        pfa = JSONConverter.jsonParserToGooglePlaces(jsonString);
	   
        //Pintamos cada uno de los objetos obtenidos que nos interesen
	    //Esto en el futuro deberia mirar en la lista compelta devuelta asta dar tres opciones minimo
        for (int i= 0; i < pfa.length; i++){
	    	if(pfa[i].getName().contains("ospital"))
	    		pfa[i].addgMapMarker(mapa);
	    }	
        
        //
        
        
		
	}
	
	private void screenSize(){
		//Capturo tamaño de pantala
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//		mvHeight = displaymetrics.heightPixels;
//		mvWidth = displaymetrics.widthPixels;
	}

	protected void initMapa() {
		
		// Asignamos el control a la variable GLOBAL después del OnCreate
		mapa = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			
		// Asigno una vista al mapa
		mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		 
		// Activo mi localizacion en el mapa
		mapa.setMyLocationEnabled(true);
		CameraUpdate cam = CameraUpdateFactory.newLatLng(new LatLng(37.18670522,-3.58));
		
        mapa.moveCamera(cam);
        mapa.addMarker(new MarkerOptions().position(new LatLng(37.18670522,-3.58)).title("You").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        
        // Asigno un nivel de zoom
		CameraUpdate ZoomCam = CameraUpdateFactory.zoomTo(12);
		mapa.animateCamera(ZoomCam);
		 
		// Establezco un listener para ver cuando cambio de posicion
		// y otro para cuando se pulse sobre un Marker
		mapa.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
		 
		     public void onMyLocationChange(Location pos) {
		 
                // Extraigo la Lat y Lon del Listener
                double lat = pos.getLatitude();
                double lon = pos.getLongitude();
		 
                origen = (Double.toString(lat).concat(",")).concat(Double.toString(lon));
                // Muevo la camara a mi posicion
                CameraUpdate cam = CameraUpdateFactory.newLatLng(new LatLng(
                        lat, lon));
		 
                mapa.moveCamera(cam);
                Marker mm = mapa.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title("You"));
                mm.showInfoWindow();
                // Notifico con un mensaje al usuario de su Lat y Lon
                Toast.makeText(Main.this,
                        "Lat: " + lat + "\nLon: " + lon, Toast.LENGTH_SHORT)
                        .show();
            }
        });
		mapa.setOnMarkerClickListener(new OnMarkerClickListener()
         {

     		@Override
			public boolean onMarkerClick(Marker m) {
     			
     			//Aqui hay dos vertientes, una no necesita búsqueda, que es mas rápido, la otra
     			//Busca en nuestro array de googlePlaces para hacernos el tema ordenado, 
     			//dejo en comentario esta segunda e implementaremos la primera
     			
     			/**
     			String markerId = m.getId();
     			googlePlaces destino;
     			*/
     			
     			Boolean retorno = false;
     			LatLng dest;
     			
     			dest = m.getPosition();
     			
     			   			
     			/**
     			//buscamos nuestro Objeto googlePlaces donde tenemos almacenada la información relacionada con el Marker
     			for(int i= 0; i < pfa.length; i++){
     				if( (pfa[i].getMarkerId()).compareTo(markerId) == 0){
     					destino = pfa[i];
     					
     				}
     			} */
     			//Compongo String destino para agregar a peticion WS googleDirections
     			destino = (String) Double.toString(dest.latitude).replace(',', '.');
     			destino = destino.concat(","); 
     			destino = destino.concat(Double.toString(dest.longitude).replace(',', '.'));
     			
     			//Pregunto si de verdad desean trazar la ruta hacia ese centro
     			new AlertDialog.Builder(ctx)
     		    .setTitle("¿Desea ir a esta localización?")
     		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
     		        public void onClick(DialogInterface dialog, int which) { 
 //    		        	Boolean r;
     		        	//Llamada a WSGoogleMap y obtención de RUTA
     		        	jsonString = getWSResponse(composeURLrequestGoogleDirections()); 
     		        	//Parseo de RUTA
     		        	pfaDirection = JSONConverter.jsonParserToGoogleDirections(jsonString);
     		        	
     		        	//Deberiamos ahora pintar el CAMINO
     		        	pfaDirection[0].drawDirection(mapa);
     		        	
 //    		        	r = false;
     		        	
     		        	
     		        }
     		     })
     		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
     		        public void onClick(DialogInterface dialog, int which) { 
     		            // do nothing
     		        }
     		     })
     		    .setIcon(R.drawable.img09)
     		     .show();
     			
     			//Esto sera si se acepta un popup de desea que le llevemos aqui??!?
     			if (dest != null)
     				retorno = true;
                     
                return retorno;
     						
     	  }

         });      
		
	} 

	private String composeURLrequestGooglePlaces(){
		String url;
		
		url = this.getString(R.string.gPlacesURL);
		url = url.concat(this.getString(R.string.Location)); 
		url = url.concat(this.getString(R.string.type)); 
		url = url.concat(this.getString(R.string.gPlacesKey)); 
		
		return url;
		
	}
	
	private String composeURLrequestGoogleDirections(){
		String url;
		
		url = this.getString(R.string.gDirections);
		url = url.concat(this.getString(R.string.gOrigin)); 
		url = url.concat(origen); 
		url = url.concat(this.getString(R.string.gDestination)); 
		url = url.concat(destino);
		url = url.concat(this.getString(R.string.gMode)); 
		url = url.concat(this.getString(R.string.gPlacesKey)); 
		return url;
		
	}
	
	protected String getWSResponse(String urlrequest){
					
		//Creo objeto y le comunico contexto
		JSONConverter converter = new JSONConverter(urlrequest);
        //Petición al servició web
		return converter.mapSearch2JSON();
		
	}
	    
}
