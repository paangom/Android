package net.ticket.android.network.adaptator;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import net.ticket.android.network.provider.Depense;
import net.ticket.android.network.provider.Ticket;
import net.ticket.android.network.provider.TicketUtils;
import net.ticket.android.settings.ApplicationController;
import net.ticket.android.settings.GlobalApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

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





@SuppressLint("NewApi")
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String TAG = "SyncAdapter";
    private RequestQueue mRequestQueue;
    private List<Ticket> sync_tickets;
	private List<Depense> sync_depenses;
	Boolean isInternetPresent = false;
	Boolean hasSimCardPresent = false;
	ConnectionDetector cd;
	Context _context;

    /**
     * URL to fetch content from during a sync.
     *
     * <p>This points to the Android Developers Blog. (Side note: We highly recommend reading the
     * Android Developer Blog to stay up to date on the latest Android platform developments!)
     */
    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        _context =context; 
        cd = new ConnectionDetector(context);
    }

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        
        _context =context;
        cd = new ConnectionDetector(context);
    }

    
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        Log.i(TAG, "Beginning network synchronization");
        
        isInternetPresent = cd.isConnectingToInternet();
        hasSimCardPresent  = cd.hasSimCard();
        if(isInternetPresent && hasSimCardPresent){   
	        try {
	            final URL location = new URL(GlobalApplication.BASE_URL+"sync/?format=json");
	            InputStream stream = null;
	            try {
	                Log.i(TAG, "Streaming data from network: " + location);
	                
	             
	                try {
						syncTickets("ON");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	               
	            } finally {
	                if (stream != null) {
	                    stream.close();
	                }
	            }
	        } catch (MalformedURLException e) {
	            Log.wtf(TAG, "Feed URL is malformed", e);
	            syncResult.stats.numParseExceptions++;
	            return;
	        } catch (IOException e) {
	            Log.e(TAG, "Error reading from network: " + e.toString());
	            syncResult.stats.numIoExceptions++;
	            return;
	        
	        }
	        Log.i(TAG, "Network synchronization complete");
	        
	        }
	   	 
    }

   
  
    public void syncTickets(String service_status) throws JSONException
    {
    	String url = GlobalApplication.BASE_URL+"sync/?format=json";
   	 	GlobalApplication application = ((GlobalApplication)this.getContext());
   	    sync_tickets = TicketUtils.getSelledTickets(this.getContext(), "1", "0");
   	    sync_depenses = TicketUtils.getDepenses(this.getContext(), "0");
   	    //Log.i(TAG+"***********************",application.getUser());
   	 	//Log.i("=======================", sync_tickets.toString());
	 	if(sync_tickets.size()>0 ||sync_depenses.size()>0){
		 	TelephonyManager phoneManager = (TelephonyManager) this.getContext().getSystemService(Context.TELEPHONY_SERVICE);
		    String phoneIMSI = phoneManager.getSubscriberId().toString();
		    String phoneIMEI = phoneManager.getDeviceId();
		 	JSONArray jsonArray = new JSONArray();
		 	JSONObject jsonobject = new JSONObject();
			for (int i = 0; i < sync_tickets.size(); i++) {
			    	Ticket obj = sync_tickets.get(i);
			    	jsonArray.put(TicketUtils.ticketToJSONObject(obj));
			}
			JSONArray jsonArrayDepenses = new JSONArray();
			for (int i = 0; i < sync_depenses.size(); i++) {
			    	Depense obj = sync_depenses.get(i);
			    	obj.setDate(System.currentTimeMillis()/1000);
			    	jsonArrayDepenses.put(TicketUtils.depenseToJSONObject(obj));
			 }
			jsonobject.put("depenses", jsonArrayDepenses);
			jsonobject.put("tickets", jsonArray);
			jsonobject.put("device", phoneIMEI);
			jsonobject.put("imsi", phoneIMSI);
			jsonobject.put("service", service_status);
			if(application.getUser()!=null && application.getPassword()!=null)
				loadSynchronisation(url,jsonobject,application.getUser(),application.getPassword());
		   
    }
}
    public void loadSynchronisation(String url, JSONObject params, final String username, final String password)
    {
    	
    	mRequestQueue =  Volley.newRequestQueue(this.getContext()); 
    	mRequestQueue.cancelAll("SYNC");
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url, params ,synclistener,
                syncerrorListener){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            	GlobalApplication application = ((GlobalApplication)getContext());
                return application.createBasicAuthHeader(username, password);
                
            }
        };
        jr.setTag("SYNC");
     
        jr.setRetryPolicy(new DefaultRetryPolicy());
        mRequestQueue.add(jr);
        //ApplicationController.getInstance().addToRequestQueue(jr);
    	
    }
    
    Response.Listener<JSONObject> synclistener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.i("Success Response: ", response.toString());
   
            try {
				JSONObject ticketsdatas = new JSONObject(response.getString("tickets"));
				String ticket_status = ticketsdatas.getString("status");
				JSONObject depensesdatas = new JSONObject(response.getString("depenses"));
				String depense_status = depensesdatas.getString("status");
				
				 Log.i("Success Response: ", ticket_status);
				 Log.i("Success Response: ", depense_status);
				 if (depense_status.equals("success")  &&  sync_depenses.size()>0)
					 TicketUtils.batch_update_depenses(_context, sync_depenses);
				 if (ticket_status.equals("success") &&  sync_tickets.size()>0)
					TicketUtils.batch_update_tickets(_context, sync_tickets);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
            
        }
    };
      
    Response.ErrorListener syncerrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
        	String message = "erreur"; 
        	if( error instanceof NetworkError) {
        		message =error.networkResponse.toString();
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
           
            Log.i("Error Response code: " +  message, TAG);   
            
        }
    };
    
    
  /*  
    public JSONObject ticketToJSONObject(Ticket ticket) throws JSONException
	{
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("organisation", ticket.getGie());
		jsonObject.put("bus", ticket.getBus());
		jsonObject.put("line", ticket.getLigne());
		jsonObject.put("section", ticket.getSection());
		jsonObject.put("amount", ticket.getAmount());
		jsonObject.put("itineraire", ticket.getItineraire());
		if (ticket.getStatut()==1) jsonObject.put("is_sell", "True");
		else  jsonObject.put("is_sell", "False");
		jsonObject.put("device", ticket.getDevice());
		jsonObject.put("reference", ticket.getReference());
		jsonObject.put("selled_date", ticket.getDate());
		jsonObject.put("actor", ticket.getUser());
		return jsonObject;
	}
    
    public JSONObject depenseToJSONObject(Depense depense) throws JSONException
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("depannage", depense.getDepannage());
		jsonObject.put("gazoil", depense.getGazoil());
		jsonObject.put("divers", depense.getDivers());
		jsonObject.put("stationnement", depense.getStationnement());
		
		jsonObject.put("gardiennage", depense.getGardiennage());
		jsonObject.put("ration", depense.getRation());
		jsonObject.put("regulateur", depense.getRegulateur());
		jsonObject.put("prime", depense.getPrime());
		jsonObject.put("lavage", depense.getLavage());
		
		jsonObject.put("date", depense.getDate());
		return jsonObject;
		
	}*/
   
}
