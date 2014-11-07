package net.ticket.android.login;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import net.ticket.android.bluebamboo.MainActivity;
import net.ticket.android.bluebamboo.R;
import net.ticket.android.network.adaptator.ConnectionDetector;
import net.ticket.android.network.provider.Parametrage;
import net.ticket.android.network.provider.TicketUtils;
import net.ticket.android.settings.ApplicationController;
import net.ticket.android.settings.GlobalApplication;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


//import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;



public class LoginActivity extends Activity implements LoginDialogFragment.NoticeDialogListener, ServiceDialogFragment.ServiceDialogListener{
	public final static String login = "login";
	public final static String ERROR_RESPONSE = "error";
	public final static String CONFIG_RESPONSE = "config";
	public final static String SUCCES_RESPONSE="succes";
	private RequestQueue mRequestQueue;
	private String TAG = this.getClass().getSimpleName();
	
	private ProgressDialog pd;
	private  ArrayList<String> lines ;
	private  JSONArray jsonArrayLines  ;
    Boolean isInternetPresent = false;
    Boolean hasSimCardPresent = false;
    ConnectionDetector cd;
	@Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connex);
        
        cd = new ConnectionDetector(getApplicationContext());
    }

    public void connexionClicked(View V) throws JSONException, ClientProtocolException, IOException
    {
 
         final  EditText editTextUserName=(EditText)findViewById(R.id.login);
         final  EditText editTextPassword=(EditText)findViewById(R.id.password);
         isInternetPresent = cd.isConnectingToInternet();
         hasSimCardPresent = cd.hasSimCard();
      // get The User name and Password
         String userName=editTextUserName.getText().toString();
         String password=editTextPassword.getText().toString();
     
         // check if the Stored password matches with  Password entered by user
         if(password.length()>0 && userName.length()>0)
         {
        	 GlobalApplication application = ((GlobalApplication)getApplicationContext());
        	 String login=application.getUser();
             String pass=application.getPassword();
             System.out.println("service : "+application.isOn_service());
        	 if(application.isOn_service()==true &&(login.equals(userName))&&(pass.equals(password)))
        	 { 
        		
        		 Intent intent = new Intent(LoginActivity.this, MainActivity.class); 
          		 startActivity(intent);
          	
        		 
        	 }
        	 else{
	        	 if(isInternetPresent && hasSimCardPresent){
	        		
	        		 JSONObject jsonObj = new JSONObject();
	            	 String url = GlobalApplication.BASE_URL+"settings/?format=json";
	            	 
	            	 application.setUser(userName);
	            	 application.setPassword(password);
	            	 jsonObj.put("device", GlobalApplication.PHONEIMEI);
	            	 jsonObj.put("imsi", GlobalApplication.PHONEIMSI);
	        		 loadConfiguration(url,jsonObj, userName,password);
	             }
	        	 
	        	/* else {
	        		 SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRENCH);
       			  String newtime = sdfDateTime.format(new Date(System.currentTimeMillis()));
	        		 Parametrage param=TicketUtils.getParametre(getApplicationContext());
	        		 if(param.getToken().equals(password)){
	        			 //GlobalApplication application2=(GlobalApplication) getApplicationContext();
	        			 application.setOn_service(true);
	        			 application.setBack_road(param.getBack_road());
	        			 application.setBus(param.getBus());
	        			 application.setChauffeur(param.getChauffeur());
	        			 application.setDateDeb(newtime);
	        			 application.setFirst_road(param.getFirst_road());
	        			 application.setItineraire(param.getItineraire());
	        			 application.setLine(param.getLine());
	        			 application.setOn_service(true);
	        			 application.setOrganisation(param.getOrganisation());
	        			 application.setPassword(param.getPassword());
	        			 application.setSectionnement("ZONE 1");
	        			 application.setUser(userName);
	        			 String section=param.getSections();
	        			 String [] sections = null;
	     	    	    section=section.substring(section.indexOf("[") +1);
	     	    	    String [] __sections=section.split("]");
	     	    	    sections= __sections[0].split(", ");
	     	    	    ArrayList<String> line_sections = new ArrayList<String>();
	     	    	    for (String sec: sections)
	     	    	    {
	     	    	    	Log.i("******************", sec + sec.length());
	     	    	    	line_sections.add(sec);
	     	    	    	
	     	    	    }
	     	    	    application.setSections(line_sections);
	     	    	     GlobalApplication.etat=false;
	     	    	    lines = new ArrayList<String>();
		            	  lines.add(param.getLine());
		            	  //mRequestQueue.cancelAll("LOGIN");
		            	  LoginDialogFragment  loginDialog = new  LoginDialogFragment();
		             	  Bundle bundle = new Bundle();
		                  bundle.putStringArrayList(LoginDialogFragment.DATA, lines);     // Require ArrayList
		                  bundle.putInt(LoginDialogFragment.SELECTED, 0);
		                  loginDialog.setArguments(bundle);
		                  loginDialog.show(getFragmentManager(), "logintag");
	     	    	    
	     	    	    
	        		 }
	        		 else
	        			 showDialog("Erreur de saisie:", "Verifier que vous avez internet ");
	        		 
	        	 }*/
        	 }
        	
            /*
        	Intent intent = new Intent(LoginActivity.this, MainActivity.class); 
     	    intent.putExtra(login, userName);
     		startActivity(intent);
     		*/
     
         }
         
         else
         {
             //Toast.makeText(LoginActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
             showDialog("Erreur de saisie:", "Les champs sont obligatoires");
         }

 }

  
  
    public void getSelectedLine(JSONArray array, String line) throws JSONException
    {
    	if(GlobalApplication.etat){
    		for (int i = 0; i < array.length(); i++) {
        		
    	        JSONObject jsonObject = array.getJSONObject(i);
    	        
    	        //JSONObject jsonFields = new JSONObject(jsonObject.getString("fields"));
    	        String __line = jsonObject .getString("name");
    	        //int sections []=null;
    	        if(__line.equals(line))
    	        {   GlobalApplication application = ((GlobalApplication)getApplicationContext());
    	    	    application.setLine(__line);
    	    	    application.setItineraire(jsonObject .getString("first_road"));
    	    	    application.setFirst_road(jsonObject .getString("first_road"));
    	    	    application.setBack_road(jsonObject .getString("back_road"));
    	    	    String section = jsonObject .getString("sections");
    	    	    application.setSection(section);
    	    	    String [] sections = null;
    	    	    section=section.substring(section.indexOf("[") +1);
    	    	    String [] __sections=section.split("]");
    	    	    sections= __sections[0].split(", ");
    	    	    ArrayList<String> line_sections = new ArrayList<String>();
    	    	    for (String sec: sections)
    	    	    {
    	    	    	Log.i("******************", sec + sec.length());
    	    	    	line_sections.add(sec);
    	    	    	
    	    	    }
    	    	    application.setSections(line_sections);
    	    	    
    	    	    //application.setSections(jsonObject .getString("sections"));
    	    	    Log.i(LoginActivity.class.getName(), jsonObject .getString("sections"));
    	    	    
    	        	break;
    	        }
    	       
    	       
    	        //lines.add(jsonFields.getString("name"));
    	      }
    	}
    	else{
    		
    	}
    	
    		
    	return ;
    	
    }
    public void loadConfiguration(String url, JSONObject params, final String username, final String password)
    {
    	mRequestQueue =  Volley.newRequestQueue(this);  
        pd = ProgressDialog.show(this,"Connexion...","Svp attendre...");
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url, params ,listener,
                errorListener){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            	GlobalApplication application = ((GlobalApplication)getApplicationContext());
                return application.createBasicAuthHeader(username, password);
                
            }
        };
        jr.setTag("LOGIN");
        jr.setRetryPolicy(new DefaultRetryPolicy());
  
       mRequestQueue.add(jr);
        //ApplicationController.getInstance().addToRequestQueue(jr);
    	
    }
    Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            //Log.i("Success Response: " + response.toString(), TAG);
            pd.dismiss();
            try {
            	  String statut = new String(response.getString("status"));
            	  if (statut.equals(ERROR_RESPONSE)){
            		showDialog(ERROR_RESPONSE,response.getString("message"));  
            	  }
            	  else if(statut.equals(CONFIG_RESPONSE))
            	  {
            		  showDialog(CONFIG_RESPONSE,response.getString("message"));  
            	  }
            	  else if(statut.equals(SUCCES_RESPONSE))
            	  {
            		  JSONObject jsondatas = new JSONObject(response.getString("data"));
            		  Log.i("Success Response PAPA: " + jsondatas.toString(), TAG);
            		  GlobalApplication application = ((GlobalApplication)getApplicationContext());
                 	  application.setBus( jsondatas.getString("bus").toString());
                 	  application.setAdverse(jsondatas.getString("adverse").toString());
                 	  application.setOrganisation(jsondatas.getString("organisation").toString());
            		  String service = jsondatas.getString("service").toString();
            		  
            		  jsonArrayLines = new JSONArray(jsondatas.getString("lines"));
            		  lines = new ArrayList<String>();
	            	  for (int i = 0; i < jsonArrayLines.length(); i++) {
	            	        JSONObject jsonObject = jsonArrayLines.getJSONObject(i);
	            	        lines.add(jsonObject.getString("name"));
	            	      }
	            	 
            		  if (service.equals("ON"))
            		  {
		                 	  application.setOn_service(true);
		                 	 GlobalApplication.etat=true;
		                 	  Log.i("organisation " + jsondatas.getString("organisation").toString(), TAG);
			            	  //mRequestQueue.cancelAll("LOGIN");
			            	  LoginDialogFragment  loginDialog = new  LoginDialogFragment();
			             	  Bundle bundle = new Bundle();
			                  bundle.putStringArrayList(LoginDialogFragment.DATA, lines);     // Require ArrayList
			                  bundle.putInt(LoginDialogFragment.SELECTED, 0);
			                  loginDialog.setArguments(bundle);
			                  loginDialog.show(getFragmentManager(), "logintag");
            		  }
            		  else if(service.equals("OFF"))  
            		  {
            			  application.setLines(lines);
            			  GlobalApplication.etat=true;
            			  SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRENCH);
            			  String newtime = sdfDateTime.format(new Date(System.currentTimeMillis()));
            			  application.setDateDeb(newtime);
            			  application.setSectionnement("ZONE 1");
            			  ServiceDialogFragment serviceDialog = new ServiceDialogFragment();
            			  serviceDialog.show(getFragmentManager(), "servicetag");
            			  
            		  }
            	  }
            	   
        	    } catch (Exception e) {
        	      e.printStackTrace();
        	      Log.i("rzerzerzezrzrrzrzrezrzrzer ", TAG);
        	    }
            
            
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
        	pd.dismiss();
        	String message = "erreur"; 
        	if( error instanceof NetworkError) {
        		message ="Veuillez entrer un bon login et mot de passe";
            }  else if( error instanceof ServerError) {
            	message ="Serveur indisponible";
            } else if( error instanceof AuthFailureError) {
            	message ="Erreur de connexion ";
            } else if( error instanceof ParseError) {
            } else if( error instanceof NoConnectionError) {
            	message ="Connexion non disponible";
            } else if( error instanceof TimeoutError) {
            	message ="Delai de connexion depasse";
            }
            else message= "Erreur ";
        	 showDialog("Error Response code: ", message);
             
             
        	
            /*if (error.networkResponse != null) {
                Log.i("Error Response code: " +  error.networkResponse.statusCode, TAG);
                showDialog("Error Response code: ", error.networkResponse.toString());
                pd.dismiss();
            }*/
        }
    };
   
   
  

    @Override
	public void onDialogPositiveClick(DialogInterface dialog, String line) {
    	Log.i("nom de la boite de dialog", dialog.getClass().getName());
	dialog.dismiss();
	
	try {
		getSelectedLine(jsonArrayLines,line);
		
		//Intent intent = new Intent(LoginActivity.this, MainActivity.class); 
	    //intent.putExtra(login, userName);
		//startActivity(intent);
		ArrayList<String> lines = new ArrayList<String>();
		GlobalApplication application = ((GlobalApplication)getApplicationContext());
    	lines.add(application.getFirst_road());
    	lines.add(application.getBack_road());
    	if(GlobalApplication.etat)
    		mRequestQueue.cancelAll("LOGIN");
    	
		ItineraireDialogFragment itineraireDialog = new ItineraireDialogFragment();
		Bundle bundle = new Bundle();
        bundle.putStringArrayList(ItineraireDialogFragment.DATA, lines);     // Require ArrayList
        bundle.putInt(ItineraireDialogFragment.SELECTED, 0);
        itineraireDialog.setArguments(bundle);
        itineraireDialog.show(getFragmentManager(), "activity");
		Log.i("ligne selectionner", line);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
	// TODO Auto-generated method stub
	dialog.dismiss();
	
	}
   

	void showDialog(String title, String message) {
    DialogFragment newFragment = MyAlertDialogFragment.newInstance(title, message);
    newFragment.show(getFragmentManager(), "dialog");
	}
	 
	public void doPositiveClick() {
    // Do stuff here.
    Log.i("FragmentAlertDialog", "Positive click!");
	}

	public void doNegativeClick() {
    // Do stuff here.
    Log.i("FragmentAlertDialog", "Negative click!");
   }

	@Override
	public void onServiceDialogPositiveClick(DialogInterface dialog) {
		// TODO Auto-generated method stub
		
 	   if(isInternetPresent && hasSimCardPresent){
 		   Log.i("Success Response: ///////////////////" , "TAG");
         	 JSONObject jsonObj = new JSONObject();
           	 String url = GlobalApplication.BASE_URL+"service/?format=json";
         	 GlobalApplication application = ((GlobalApplication)getApplicationContext());
           	
               	 try {
						 jsonObj.put("device", GlobalApplication.PHONEIMEI);
						 jsonObj.put("imsi", GlobalApplication.PHONEIMSI);
	              		 loadCreateService(url,jsonObj, application.getUser(),application.getPassword());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
           	
            }
		
	}

	@Override
	public void onServiceDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
	
	public void loadCreateService(String url, JSONObject params,
			final String username, final String password) {
			
		mRequestQueue =  Volley.newRequestQueue(getApplicationContext());  
        pd = ProgressDialog.show(this,"Prise de service...","Configuration en cours...");
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url, params ,listener1,
                errorListener){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            	 GlobalApplication application = ((GlobalApplication)getApplicationContext());
                return application.createBasicAuthHeader(username, password);
                
            }
        };
        jr.setTag("SERVICE");
        jr.setRetryPolicy(new DefaultRetryPolicy());
  
       mRequestQueue.add(jr);
		
		// TODO Auto-generated method stub
		
	}
	
	
	  Response.Listener<JSONObject> listener1 = new Response.Listener<JSONObject>() {
	        @Override
	        public void onResponse(JSONObject response) {
	            Log.i("Success Response AL: " + response.toString(), TAG);
	            pd.dismiss();
	            try {
	            	  String statut = new String(response.getString("status"));
	            	  if (statut.equals(ERROR_RESPONSE)){
	            		showDialog(ERROR_RESPONSE,response.getString("message"));  
	            	  }
	            	  else if(statut.equals(SUCCES_RESPONSE))
	            	  {
	            		  JSONObject jsondatas = new JSONObject(response.getString("data"));
	            		  Log.i("Success Response: " + jsondatas.toString(), "TAG");
	            		  String service = jsondatas.getString("service").toString();
	            		  
	            		  
	            		  Log.i("==================================", service);
	            		  GlobalApplication application = ((GlobalApplication)getApplicationContext());
	                      if (application ==null )Log.i("==================================", "TAG");
	            		  if (service.equals("ON"))
	            		  {
	            			      String token = jsondatas.getString("token").toString(); 
	            			      
	            			      //showDialog("TOKEN", "Merci de garder cette valeur :" +token +"\n"+"pour vous connecter en cas de soucis avec internet");
	            			      application.setOn_service(true);
	            			      GlobalApplication.etat=true;
	            			      application.setToken(token);
	            			     // application.setToken(token);
				            	  LoginDialogFragment  loginDialog = new  LoginDialogFragment();
				             	  Bundle bundle = new Bundle();
				                  bundle.putStringArrayList(LoginDialogFragment.DATA, lines);     // Require ArrayList
				                  bundle.putInt(LoginDialogFragment.SELECTED, 0);
				                  loginDialog.setArguments(bundle);
				                  loginDialog.show(getFragmentManager(), "logintag");
	            		  }
	            		  else if(service.equals("OFF"))  
	            		  {
	            			  showDialog("Horaire non authoriser",jsondatas.getString("message")); 
	            			  
	            		  }
	            	  }
	            	   
	        	    } catch (Exception e) {
	        	      e.printStackTrace();
	        	    
	        	    }
	            
	            
	        }
	    };
	    
	    
	    

	    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	    	GlobalApplication application = ((GlobalApplication)getApplicationContext());
	    	if(application.isOn_service()==true){
	    		new AlertDialog.Builder(this)
				.setTitle("Alerte")
				.setMessage("Vous ne pouvez pas quitter l'application! Vous n'avez pas encore fait votre fin de service")
				.setNeutralButton("Close", new DialogInterface.OnClickListener() { 
					public void onClick(DialogInterface dlg, int sumthin) {
						
					}
				})
				.show();
	    	}
	    	else
	    		super.onBackPressed();
	}

		public void vider(){
	    	((EditText)findViewById(R.id.login)).setText("");
	        ((EditText)findViewById(R.id.password)).setText("");
	    }
}