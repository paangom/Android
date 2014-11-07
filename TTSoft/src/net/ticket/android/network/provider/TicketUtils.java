package net.ticket.android.network.provider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.ticket.android.settings.GlobalApplication;

import android.R.integer;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;


public class TicketUtils {

	private static final String[] R_PROJECTION = new String[] {

		 TicketContract.Ticket.COLUMN_SECTION,
		 "count(" + TicketContract.Ticket.COLUMN_ID + ")",
		 "sum(" + TicketContract.Ticket.COLUMN_AMOUNT + ")"
		  
		};
	private static final String[] RC_PROJECTION = new String[] {

		 TicketContract.Ticket.COLUMN_SECTION,
		 "count(" + TicketContract.Ticket.COLUMN_ID + ")"
		  
		};
	
	private static final String[] D_PROJECTION = new String[] {

		 "sum(" + TicketContract.Depense.DEPENSE_DEPANNAGE+ ")",
		 "sum(" + TicketContract.Depense.DEPENSE_GAZOIL+ ")",
		 "sum(" + TicketContract.Depense.DEPENSE_DIVERS+ ")",
		 "sum(" + TicketContract.Depense.DEPENSE_STATIONNEMENT+ ")" ,
		 "sum(" + TicketContract.Depense.DEPENSE_RATION+ ")",
		 "sum(" + TicketContract.Depense.DEPENSE_REGULATEUR+ ")",
		 "sum(" + TicketContract.Depense.DEPENSE_GARDIENNAGE+ ")",
		 "sum(" + TicketContract.Depense.DEPENSE_LAVAGE+ ")",
		 "sum(" + TicketContract.Depense.DEPENSE_PRIME+  ")",
		 
		  
		};
	
	private static final String[] ST_PROJECTION = new String[] {

		"count(" + TicketContract.Ticket.COLUMN_ID + ")"
		 
		  
		};
	
	
	 public static void delete_all_rows(Context context )
	 {
		  context.getContentResolver().delete(TicketContract.Depense.CONTENT_URI, null, null);
		  context.getContentResolver().delete(TicketContract.Ticket.CONTENT_URI, null, null);

		 
	 }
	public static  Depense getAllDepenses(Context context )
	{
		
		 Depense depense =new Depense();
		 //String mSelectionClause = null;
		 //mSelectionClause =") GROUP BY (" + TicketContract.Ticket.COLUMN_ID;
	     //mSelectionClause += ") ORDER BY (" + TicketContract.Ticket.COLUMN_SECTION;
		Cursor cursor = context.getContentResolver().query(TicketContract.Depense.CONTENT_URI, D_PROJECTION, null, null, null);
		cursor.moveToFirst();
		if (cursor != null && cursor.getCount()>0){
			 cursor .moveToFirst(); 
			  depense.setDepannage(cursor.getLong(0));
			  depense.setGazoil(cursor.getLong(1));
			  depense.setDivers(cursor.getLong(2));
			  depense.setStationnement(cursor.getLong(3));
			  depense.setRation(cursor.getLong(4));
			  depense.setRegulateur(cursor.getLong(5));
			  depense.setGardiennage(cursor.getLong(6));
			  depense.setLavage(cursor.getLong(7));
			  depense.setPrime(cursor.getLong(8)); 
			  cursor.close();
			  return depense;
		}
		else{ 
			cursor.close();
			return null;
			}
		
	}
	public static  List<Depense> getDepenses(Context context , String sync)
	{
		List<Depense> depenses = new ArrayList<Depense>();
		String mSelectionClause = null;
		// Initializes an array to contain selection arguments
		String[] mSelectionArgs = {""};	
		 mSelectionClause = TicketContract.Depense.DEPENSE_SYNC + " = ?";

	    mSelectionArgs[0] =sync;
		Cursor cursor = context.getContentResolver().query(TicketContract.Depense.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);
		cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Depense depense = cursorToDepense(cursor);
		      depenses.add(depense);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		  
		return depenses;
	}
	public static Depense updateDepense(Context context, Depense depense)
	{
		  int id = (int) depense.getId();
		  
		  ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		  Uri existingUri = TicketContract.Depense.CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();
		 	 batch.add(ContentProviderOperation.newUpdate(existingUri)
		 			
	                 .withValue(TicketContract.Depense.DEPENSE_DEPANNAGE, depense.getDepannage())
	                 .withValue(TicketContract.Depense.DEPENSE_DIVERS, depense.getDivers())
	                 .withValue(TicketContract.Depense.DEPENSE_GAZOIL, depense.getGazoil())
	                 .withValue(TicketContract.Depense.DEPENSE_STATIONNEMENT, depense.getStationnement())
	               
	                 .withValue(TicketContract.Depense.DEPENSE_LAVAGE, depense.getLavage())
	                 .withValue(TicketContract.Depense.DEPENSE_PRIME, depense.getPrime())
	                 .withValue(TicketContract.Depense.DEPENSE_REGULATEUR, depense.getRegulateur())
	                 .withValue(TicketContract.Depense.DEPENSE_RATION, depense.getRation())
	                 .withValue(TicketContract.Depense.DEPENSE_GARDIENNAGE, depense.getGardiennage())
	                 
	                 .withValue(TicketContract.Depense.DEPENSE_DATE, depense.getDate())
	                 .withValue(TicketContract.Depense.DEPENSE_SYNC, depense.getSync())
	                 
	              
                 .build());
		 
		 	try {
				context.getContentResolver().applyBatch(TicketContract.CONTENT_AUTHORITY, batch);
				context.getContentResolver().notifyChange(TicketContract.Depense.CONTENT_URI, null,false);
				return depense;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (OperationApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		  
		
		
	}
	public static Depense createDepense(Context context, Depense depense)
	{
		
		  ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
	    		
			 batch.add(ContentProviderOperation.newInsert(TicketContract.Depense.CONTENT_URI)
					 .withValue(TicketContract.Depense.DEPENSE_DEPANNAGE, depense.getDepannage())
	                 .withValue(TicketContract.Depense.DEPENSE_DIVERS, depense.getDivers())
	                 .withValue(TicketContract.Depense.DEPENSE_GAZOIL, depense.getGazoil())
	                 .withValue(TicketContract.Depense.DEPENSE_STATIONNEMENT, depense.getStationnement())
	                 .withValue(TicketContract.Depense.DEPENSE_REFERENCE, depense.getReference())
	                 .withValue(TicketContract.Depense.DEPENSE_LAVAGE, depense.getLavage())
	                 .withValue(TicketContract.Depense.DEPENSE_PRIME, depense.getPrime())
	                 .withValue(TicketContract.Depense.DEPENSE_REGULATEUR, depense.getRegulateur())
	                 .withValue(TicketContract.Depense.DEPENSE_RATION, depense.getRation())
	                 .withValue(TicketContract.Depense.DEPENSE_GARDIENNAGE, depense.getGardiennage())
	                 
	                 .withValue(TicketContract.Depense.DEPENSE_DATE, depense.getDate())
	                 .withValue(TicketContract.Depense.DEPENSE_SYNC, depense.getSync())
	                
	                 .build());


			try {
				Log.i("downloding tickets", "Merge solution ready. Applying batch update");
				
				context.getContentResolver().applyBatch(TicketContract.CONTENT_AUTHORITY, batch);
				context.getContentResolver().notifyChange(TicketContract.Depense.CONTENT_URI, null,false); 
				return depense;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (OperationApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			
			}
		
		
	}
	public static List<Rapport>getRapport(Context context)
	{
		String mSelectionClause = null;
		// Initializes an array to contain selection arguments
		String[] mSelectionArgs = {""};
		List<Rapport> rapports = new ArrayList<Rapport>();
		
		 mSelectionClause = TicketContract.Ticket.COLUMN_STATUT + " = ?" + ") GROUP BY (" + TicketContract.Ticket.COLUMN_SECTION;
	     mSelectionClause += ") ORDER BY (" + TicketContract.Ticket.COLUMN_SECTION;

	    mSelectionArgs[0] = "1";
		Cursor cursor = context.getContentResolver().query(TicketContract.Ticket.CONTENT_URI, R_PROJECTION, mSelectionClause, mSelectionArgs, null);
		
	    
	    cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	Rapport rapport = cursorToRapport(cursor);
        	rapports.add(rapport);
          cursor.moveToNext();
        }
        Log.i("tag", "rapport " + rapports.toString()+" local rapport. Computing merge solution...");
        // make sure to close the cursor
        cursor.close();
        
	return rapports;
			
	}
	
	
	public static List<Rapport>getRapport(Context context, String type)
	{
		String mSelectionClause = null;
		// Initializes an array to contain selection arguments
		String[] mSelectionArgs = {"",type};
		List<Rapport> rapports = new ArrayList<Rapport>();
		
		 mSelectionClause = TicketContract.Ticket.COLUMN_STATUT + " = ?" + ") GROUP BY (" + TicketContract.Ticket.COLUMN_SECTION;
	     mSelectionClause += ") ORDER BY (" + TicketContract.Ticket.COLUMN_SECTION;

	    mSelectionArgs[0] = "1";
		Cursor cursor = context.getContentResolver().query(TicketContract.Ticket.CONTENT_URI, R_PROJECTION, mSelectionClause, mSelectionArgs, null);
		
	    
	    cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	Rapport rapport = cursorToRapport(cursor);
        	rapports.add(rapport);
          cursor.moveToNext();
        }
        Log.i("tag", "rapport " + rapports.toString()+" local rapport. Computing merge solution...");
        // make sure to close the cursor
        cursor.close();
        
	return rapports;
		
		
			
	}
	
	
	public static List<Rapport>getRapportByType(Context context, String type)
	{
		String mSelectionClause = null;
		// Initializes an array to contain selection arguments
		String[] mSelectionArgs = {type, ""};
		
		List<Rapport> rapports = new ArrayList<Rapport>();
		 mSelectionClause = TicketContract.Ticket.COLUMN_TYPE + " = ?" + " AND ";
		 mSelectionClause += TicketContract.Ticket.COLUMN_STATUT + " = ?" + ") GROUP BY (" + TicketContract.Ticket.COLUMN_SECTION;
	     mSelectionClause += ") ORDER BY (" + TicketContract.Ticket.COLUMN_SECTION;

	    mSelectionArgs[1] = "1";
	    Cursor cursor = null;
	   	cursor = context.getContentResolver().query(TicketContract.Ticket.CONTENT_URI, R_PROJECTION, mSelectionClause, mSelectionArgs, null);
//		  
//	    if (type.equals("CARD"))
//	    	cursor = context.getContentResolver().query(TicketContract.Ticket.CONTENT_URI, RC_PROJECTION, mSelectionClause, mSelectionArgs, null);
//	    else 
//	    	cursor = context.getContentResolver().query(TicketContract.Ticket.CONTENT_URI, R_PROJECTION, mSelectionClause, mSelectionArgs, null);
//		  
	    
	    cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	Rapport rapport = cursorToRapport(cursor);
        	rapports.add(rapport);
          cursor.moveToNext();
        }
        Log.i("tag", "rapport " + rapports.toString()+" local rapport. Computing merge solution...");
        // make sure to close the cursor
        cursor.close();
        
	return rapports;
		
		
			
	}
	
	
	public static int getNumberofTickets(Context context, String status)
	 {
		String mSelectionClause = null;
		// Initializes an array to contain selection arguments
		String[] mSelectionArgs = {""};	
		 mSelectionClause = TicketContract.Ticket.COLUMN_STATUT + " = ?";

	    mSelectionArgs[0] =status; 
	    int resultat=0;
		Cursor cursor = context.getContentResolver().query(TicketContract.Ticket.CONTENT_URI, ST_PROJECTION, mSelectionClause, mSelectionArgs, null);
		//cursor.moveToFirst();
		if (cursor != null && cursor.getCount()>0){
			  cursor.moveToFirst(); 
			  
			   resultat=cursor.getInt(0);
			   cursor.close();
		}
		
		 return resultat ;
		 
	 }
	public static Ticket sell_ticket(Context context, Ticket _ticket)
	{
		String mSelectionClause = null;
		String[] mSelectionArgs = {""};
	
		mSelectionClause = TicketContract.Ticket.COLUMN_STATUT + " = ?";
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		
		mSelectionArgs[0] = "0";
		Cursor cursor = context.getContentResolver().query(TicketContract.Ticket.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);
		int id;
        assert cursor != null;
        Log.i("tag", "Found " + cursor.getCount()+" local entries. Computing merge solution...");
        if (cursor != null && cursor.getCount()>0){
			  cursor .moveToFirst();
			  Ticket ticket =cursorToTicket(cursor);
			  Log.i("tag", "Found " + ticket.toString());
			  id = (int) ticket.getId();
			  cursor.close();
			  ticket.setSection(_ticket.getSection());
			  ticket.setAmount(_ticket.getAmount());
			  ticket.setDate(_ticket.getDate());
			  ticket.setLigne(_ticket.getLigne());
			  ticket.setBus(_ticket.getBus());
			  ticket.setIs_sync(_ticket.getIs_sync());
			  ticket.setUser(_ticket.getUser());
			  ticket.setGie(_ticket.getGie());
			  ticket.setStatut(_ticket.getStatut());
			  ticket.setType(_ticket.getType());
			  ticket.setCard(_ticket.getCard());
			  ticket.setItineraire(_ticket.getItineraire());
			 
			
		 	 Uri existingUri = TicketContract.Ticket.CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();
		 	 batch.add(ContentProviderOperation.newUpdate(existingUri)
		 			
	                 .withValue(TicketContract.Ticket.COLUMN_LIGNE,ticket.getLigne())
	                 .withValue(TicketContract.Ticket.COLUMN_USER, ticket.getUser())
	                 .withValue(TicketContract.Ticket.COLUMN_GIE, ticket.getGie())
	                 .withValue(TicketContract.Ticket.COLUMN_ITINERAIRE, ticket.getItineraire())
	                 .withValue(TicketContract.Ticket.COLUMN_SECTION, ticket.getSection())
	                 .withValue(TicketContract.Ticket.COLUMN_DATE, ticket.getDate())
	                 .withValue(TicketContract.Ticket.COLUMN_AMOUNT, ticket.getAmount())
	                 .withValue(TicketContract.Ticket.COLUMN_STATUT, ticket.getStatut())
	                 .withValue(TicketContract.Ticket.COLUMN_TYPE, ticket.getType())
	                 .withValue(TicketContract.Ticket.COLUMN_CARD, ticket.getCard())
	              
                    .build());
		 	Log.i("tag", "updte ticket  " + ticket.toString());
		 	try {
				context.getContentResolver().applyBatch(TicketContract.CONTENT_AUTHORITY, batch);
				context.getContentResolver().notifyChange(TicketContract.Ticket.CONTENT_URI, null,false);
				return ticket;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (OperationApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
					  
        }
        else
        {
        	//cursor.close();
        	return null;
        }
		
	}

	public static void batch_update_depenses(Context context, List<Depense> depenses)
	{
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
    	for (Depense depense: depenses)
		{
    	int id;
    	id = (int) depense.getId();
    	 Uri existingUri = TicketContract.Depense.CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();	
    	 batch.add(ContentProviderOperation.newUpdate(existingUri)
                 .withValue(TicketContract.Depense.DEPENSE_SYNC, 1)
                .build());
		
		}

		try {
			Log.i("update depense", "Merge solution ready. Applying batch update");
			
			context.getContentResolver().applyBatch(TicketContract.CONTENT_AUTHORITY, batch);
			context.getContentResolver().notifyChange(TicketContract.Depense.CONTENT_URI, null,false); 
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
		
	}
	public static void batch_update_tickets(Context context, List<Ticket> tickets)
	{
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
    	for (Ticket ticket: tickets)
		{
    	int id;
    	id = (int) ticket.getId();
    	 Uri existingUri = TicketContract.Ticket.CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();	
    	 batch.add(ContentProviderOperation.newUpdate(existingUri)
                 .withValue(TicketContract.Ticket.COLUMN_SYNC, 1)
                .build());
		
		}

		try {
			Log.i("update tickets", "Merge solution ready. Applying batch update");
			
			context.getContentResolver().applyBatch(TicketContract.CONTENT_AUTHORITY, batch);
			context.getContentResolver().notifyChange(TicketContract.Ticket.CONTENT_URI, null,false); 
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
		
	}
	
	
	
	public static void batch_insert_tickets(Context context, List<Ticket> tickets)
	{
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
    	for (Ticket ticket: tickets)
		{
			
		 batch.add(ContentProviderOperation.newInsert(TicketContract.Ticket.CONTENT_URI)
                 .withValue(TicketContract.Ticket.COLUMN_BUS,ticket.getBus())
                 .withValue(TicketContract.Ticket.COLUMN_LIGNE,ticket.getLigne())
                 .withValue(TicketContract.Ticket.COLUMN_USER, ticket.getUser())
                 .withValue(TicketContract.Ticket.COLUMN_GIE, ticket.getGie())
                 .withValue(TicketContract.Ticket.COLUMN_ITINERAIRE, ticket.getItineraire())
                 .withValue(TicketContract.Ticket.COLUMN_SECTION, ticket.getSection())
                 .withValue(TicketContract.Ticket.COLUMN_DATE, ticket.getDate())
                 .withValue(TicketContract.Ticket.COLUMN_AMOUNT, ticket.getAmount())
                 .withValue(TicketContract.Ticket.COLUMN_STATUT, ticket.getStatut())
                 .withValue(TicketContract.Ticket.COLUMN_SYNC, ticket.getIs_sync())
                 .withValue(TicketContract.Ticket.COLUMN_REFERENCE, ticket.getReference())
                 
                 .withValue(TicketContract.Ticket.COLUMN_CARD, ticket.getCard())
                 .withValue(TicketContract.Ticket.COLUMN_TYPE, ticket.getType())
                 
                 .build());
		
		}

		try {
			Log.i("downloding tickets", "Merge solution ready. Applying batch update");
			
			context.getContentResolver().applyBatch(TicketContract.CONTENT_AUTHORITY, batch);
			context.getContentResolver().notifyChange(TicketContract.Ticket.CONTENT_URI, null,false); 
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
		
	}
	
	
	 public static List<Ticket> getSelledTickets(Context context, String status, String sync)
	 {
		 List<Ticket> _tickets = new ArrayList<Ticket>();
		 String mSelectionClause = null;
			// Initializes an array to contain selection arguments
			String[] mSelectionArgs = {status,sync};
			 mSelectionClause = TicketContract.Ticket.COLUMN_STATUT + " = ?" + " AND ";
		     mSelectionClause += TicketContract.Ticket.COLUMN_SYNC + " = ?";

		    //mSelectionArgs[0] = status;
		    //mSelectionArgs[1] = sync;
			Cursor cursor = context.getContentResolver().query(TicketContract.Ticket.CONTENT_URI, null, mSelectionClause,mSelectionArgs, null);
			//Log.i("tag", "rapport " + cursor.getCount()+" local rapport. Computing merge solution...");
		    cursor.moveToFirst();
	        while (!cursor.isAfterLast()) {
	        	Ticket ticket = cursorToTicket(cursor);
	        	_tickets.add(ticket);
	          cursor.moveToNext();
	        }
	      
	        // make sure to close the cursor
	        cursor.close();
		 
		 return _tickets; 
		 
	 }
	 
	 
	 
	 public static List<Ticket> getSelledTickets(Context context, String status, String sync, String type)
	 {
		 List<Ticket> _tickets = new ArrayList<Ticket>();
		 String mSelectionClause = null;
			// Initializes an array to contain selection arguments
			String[] mSelectionArgs = {status,sync,type};
			 mSelectionClause = TicketContract.Ticket.COLUMN_STATUT + " = ?" + " AND ";
		     mSelectionClause += TicketContract.Ticket.COLUMN_SYNC + " = ?" + " AND ";
		     mSelectionClause += TicketContract.Ticket.COLUMN_TYPE + " = ?" ;

		    //mSelectionArgs[0] = status;
		    //mSelectionArgs[1] = sync;
			Cursor cursor = context.getContentResolver().query(TicketContract.Ticket.CONTENT_URI, null, mSelectionClause,mSelectionArgs, null);
			//Log.i("tag", "rapport " + cursor.getCount()+" local rapport. Computing merge solution...");
		    cursor.moveToFirst();
	        while (!cursor.isAfterLast()) {
	        	Ticket ticket = cursorToTicket(cursor);
	        	_tickets.add(ticket);
	          cursor.moveToNext();
	        }
	      
	        // make sure to close the cursor
	        cursor.close();
		 
		 return _tickets; 
		 
	 }
	
	private  static Depense cursorToDepense(Cursor cursor){
		  
		  Depense depense =new Depense();
		  depense.setId(cursor.getInt(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_ID)));
		  depense.setGazoil(cursor.getInt(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_GAZOIL)));
		  depense.setStationnement(cursor.getInt(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_STATIONNEMENT)));
		  depense.setDivers(cursor.getInt(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_DIVERS)));
		  depense.setDepannage(cursor.getInt(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_DEPANNAGE)));
		  
		  depense.setRegulateur(cursor.getInt(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_REGULATEUR)));
		  depense.setRation(cursor.getInt(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_RATION)));
		  depense.setLavage(cursor.getInt(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_LAVAGE)));
		  depense.setPrime(cursor.getInt(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_PRIME)));
		  depense.setGardiennage(cursor.getInt(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_GARDIENNAGE)));
		  depense.setReference(cursor.getString(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_REFERENCE)));
		  
		  depense.setDate(cursor.getInt(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_DATE)));
		  depense.setSync(cursor.getInt(cursor.getColumnIndex(TicketContract.Depense.DEPENSE_SYNC)));
		  return depense;
	  }
	
	private static Ticket cursorToTicket(Cursor cursor) {
	    Ticket ticket = new Ticket();
	    ticket.setId(cursor.getInt(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_ID)));
	    ticket.setUser(cursor.getString(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_USER)));
	    
	    ticket.setBus(cursor.getString(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_BUS)));
	    ticket.setGie(cursor.getString(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_GIE)));
	    ticket.setLigne(cursor.getString(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_LIGNE)));
	    ticket.setItineraire(cursor.getString(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_ITINERAIRE)));
	    
	    ticket.setSection(cursor.getInt(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_SECTION)));
	    ticket.setReference(cursor.getString(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_REFERENCE)));
	    ticket.setDate(cursor.getLong(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_DATE)));
	    ticket.setAmount(cursor.getInt(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_AMOUNT)));
	    ticket.setStatut(cursor.getInt(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_STATUT)));
	    
	    ticket.setCard(cursor.getString(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_CARD)));
	    ticket.setType(cursor.getString(cursor.getColumnIndex(TicketContract.Ticket.COLUMN_TYPE)));

	    
	    return ticket;
	  }
	
	private static LoggerSession cursorToLoggerSession(Cursor cursor) {
		// TODO Auto-generated method stub
		LoggerSession logger =new LoggerSession();
		logger.setId(cursor.getInt(cursor.getColumnIndex(TicketContract.Params.COLUMN_ID)));
		logger.setUsername(cursor.getString(cursor.getColumnIndex(TicketContract.Params.COLUMN_USERNAME)));
		    
		logger.setPassword(cursor.getString(cursor.getColumnIndex(TicketContract.Params.COLUMN_PASSWORD)));
		logger.setMaxTickets(cursor.getInt(cursor.getColumnIndex(TicketContract.Params.COLUMN_MAX_TICKETS)));
		logger.setSyncfrequence(cursor.getInt(cursor.getColumnIndex(TicketContract.Params.COLUMN_SYNC_FREQUENCE)));
		return logger;
	}
	
	private static Rapport cursorToRapport(Cursor cursor)
	  {
	  Rapport  rapport = new Rapport();
	  rapport.setSection(cursor.getString(0));
		rapport.setTickets(cursor.getInt(1));
		rapport.setTotal(cursor.getInt(2));
	  return rapport;	
	  }
	
	public static Ticket get_ticket(Context context, Ticket _ticket)
	{
		String mSelectionClause = null;
		String[] mSelectionArgs = {""};

		mSelectionClause = TicketContract.Ticket.COLUMN_STATUT + " = ?";
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		
		mSelectionArgs[0] = "0";
		Cursor cursor = context.getContentResolver().query(TicketContract.Ticket.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);
		int id;
        assert cursor != null;
        Log.i("tag", "Found " + cursor.getCount()+" local entries. Computing merge solution...");
        if (cursor != null && cursor.getCount()>0){
			  cursor .moveToFirst();
			  Ticket ticket =cursorToTicket(cursor);
			  Log.i("tag", "Found " + ticket.toString());
			  id = (int) ticket.getId();
			  cursor.close();
			  ticket.setSection(_ticket.getSection());
			  ticket.setAmount(_ticket.getAmount());
			  ticket.setDate(_ticket.getDate());
			  ticket.setLigne(_ticket.getLigne());
			  ticket.setBus(_ticket.getBus());
			  ticket.setIs_sync(_ticket.getIs_sync());
			  ticket.setUser(_ticket.getUser());
			  ticket.setGie(_ticket.getGie());
			  ticket.setStatut(_ticket.getStatut());
			  ticket.setItineraire(_ticket.getItineraire());
			  
			  ticket.setType(_ticket.getType());
			  ticket.setCard(_ticket.getCard());
        return ticket;
        }
        else return null;
	}
	
	public static LoggerSession get_Logger(Context context, LoggerSession logger)
	{
		String mSelectionClause = null;
		String[] mSelectionArgs = {""};

		mSelectionClause = TicketContract.Params.COLUMN_USERNAME + " = ?";
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		
		mSelectionArgs[0] = logger.getUsername();
		Cursor cursor = context.getContentResolver().query(TicketContract.Params.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);
		int id;
        assert cursor != null;
        Log.i("tag", "Found " + cursor.getCount()+" local loggers. Computing merge solution...");
        if (cursor != null && cursor.getCount()>0){
			  cursor .moveToFirst();
			  LoggerSession _logger =cursorToLoggerSession(cursor);
			  Log.i("tag", "Found " + logger.toString());
			  id = (int) logger.getId();
			  cursor.close();
			  logger.setMaxTickets(_logger.getMaxTickets());
			  logger.setSyncfrequence(_logger.getSyncfrequence());
			 
			
        return logger;
        }
        else return null;
	}
	
	public static void batch_insert_logger(Context context, List<LoggerSession> loggers)
	{
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
    	for (LoggerSession logger: loggers)
		{
			
		 batch.add(ContentProviderOperation.newInsert(TicketContract.Params.CONTENT_URI)
				    .withValue(TicketContract.Params.COLUMN_USERNAME,logger.getUsername())
	                .withValue(TicketContract.Params.COLUMN_PASSWORD, logger.getPassword())
	                .withValue(TicketContract.Params.COLUMN_MAX_TICKETS, logger.getMaxTickets())
	                .withValue(TicketContract.Params.COLUMN_SYNC_FREQUENCE, logger.getSyncfrequence())
                 
                 .build());
		
		}

		try {
			Log.i("create logger", "Merge solution ready. Applying batch update");
			
			context.getContentResolver().applyBatch(TicketContract.CONTENT_AUTHORITY, batch);
			context.getContentResolver().notifyChange(TicketContract.Params.CONTENT_URI, null,false); 
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
		
	}
	
	
	public static boolean updateLoggerSession(Context context, LoggerSession logger)
	{
		int idLogger=(int) logger.getId();
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		Uri existingUri = TicketContract.Params.CONTENT_URI.buildUpon().appendPath(Integer.toString(idLogger)).build();
	 	 batch.add(ContentProviderOperation.newUpdate(existingUri)
	 			
                .withValue(TicketContract.Params.COLUMN_USERNAME,logger.getUsername())
                .withValue(TicketContract.Params.COLUMN_PASSWORD, logger.getPassword())
                .withValue(TicketContract.Params.COLUMN_MAX_TICKETS, logger.getMaxTickets())
                .withValue(TicketContract.Params.COLUMN_SYNC_FREQUENCE, logger.getSyncfrequence())
           
             
               .build());
	 	Log.i("tag", "updte logger  " + logger.toString());
	 	try {
			context.getContentResolver().applyBatch(TicketContract.CONTENT_AUTHORITY, batch);
			context.getContentResolver().notifyChange(TicketContract.Params.CONTENT_URI, null,false);
			return true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			
		
	}
	
	public static boolean updateticket(Context context, Ticket tic) {
		int idTic=(int) tic.getId();
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		Uri existingUri = TicketContract.Ticket.CONTENT_URI.buildUpon().appendPath(Integer.toString(idTic)).build();
	 	 batch.add(ContentProviderOperation.newUpdate(existingUri)
	 			
                .withValue(TicketContract.Ticket.COLUMN_LIGNE,tic.getLigne())
                .withValue(TicketContract.Ticket.COLUMN_USER, tic.getUser())
                .withValue(TicketContract.Ticket.COLUMN_GIE, tic.getGie())
                .withValue(TicketContract.Ticket.COLUMN_ITINERAIRE, tic.getItineraire())
                .withValue(TicketContract.Ticket.COLUMN_SECTION, tic.getSection())
                .withValue(TicketContract.Ticket.COLUMN_DATE, tic.getDate())
                .withValue(TicketContract.Ticket.COLUMN_AMOUNT, tic.getAmount())
                .withValue(TicketContract.Ticket.COLUMN_STATUT, tic.getStatut())
                
                .withValue(TicketContract.Ticket.COLUMN_CARD, tic.getCard())
                .withValue(TicketContract.Ticket.COLUMN_TYPE, tic.getType())
             
               .build());
	 	Log.i("tag", "updte ticket  " + tic.toString());
	 	try {
			context.getContentResolver().applyBatch(TicketContract.CONTENT_AUTHORITY, batch);
			context.getContentResolver().notifyChange(TicketContract.Ticket.CONTENT_URI, null,false);
			return true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static void insert_parametre(Context context, GlobalApplication param){
		System.out.println("parametre");
		
		if(getParametre(context)==null){
			System.out.println("nulllllll");
			ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
			
			 batch.add(ContentProviderOperation.newInsert(TicketContract.Parametre.CONTENT_URI)
					    .withValue(TicketContract.Parametre.COLUMN_USER,param.getUser())
		                .withValue(TicketContract.Parametre.COLUMN_TOKEN, param.getToken())
		                .withValue(TicketContract.Parametre.COLUMN_BUS, param.getBus())
		                .withValue(TicketContract.Parametre.COLUMN_GIE, param.getOrganisation())
		                .withValue(TicketContract.Parametre.COLUMN_ITINERAIRE,param.getItineraire())
		                .withValue(TicketContract.Parametre.COLUMN_LIGNE, param.getLine())
		                .withValue(TicketContract.Parametre.COLUMN_SECTION, param.getSection())
		                .withValue(TicketContract.Parametre.COLUMN_PASSWORD, param.getPassword())
		                .withValue(TicketContract.Parametre.COLUMN_FIRST, param.getFirst_road())
		                .withValue(TicketContract.Parametre.COLUMN_BACK, param.getBack_road())
	                 .build());


			try {
				Log.i("create logger", "Merge solution ready. Applying batch update");
				
				context.getContentResolver().applyBatch(TicketContract.CONTENT_AUTHORITY, batch);
				context.getContentResolver().notifyChange(TicketContract.Parametre.CONTENT_URI, null,false); 
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OperationApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			System.out.println("paaaaa nulll");
			updateParametre(context, param);
		}
		
		
	}
	
	public static void updateParam(Context context, GlobalApplication param){
		Cursor cursor = context.getContentResolver().query(TicketContract.Parametre.CONTENT_URI, null, null, null, null);
		//Log.i("tag", "rapport " + cursor.getCount()+" local rapport. Computing merge solution...");
		if (cursor != null && cursor.getCount()>0){
			cursor.moveToFirst();
			Parametrage para=cursorToParam(cursor);
		}
      
        // make sure to close the cursor
        cursor.close();
	}
	
	public static Parametrage getParametre(Context context){
		Parametrage param=new Parametrage();
		Cursor cursor = context.getContentResolver().query(TicketContract.Parametre.CONTENT_URI, null, null, null, null);
		cursor.moveToFirst();
		if (cursor != null && cursor.getCount()>0){
			 cursor .moveToFirst(); 
			  param.setBus(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_BUS)));
			  param.setChauffeur(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_USER)));
			  param.setItineraire(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_ITINERAIRE)));
			  param.setLine(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_LIGNE)));
			  param.setOrganisation(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_GIE)));
			  param.setSections(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_SECTION)));
			  param.setToken(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_TOKEN)));
			  param.setFirst_road(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_FIRST)));
			  param.setBack_road(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_BACK)));
			  param.setPassword(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_PASSWORD)));
			
			  cursor.close();
			  return param;
		}
		else{ 
			cursor.close();
			return null;
			}
	}
	
	public static boolean updateParametre(Context context, GlobalApplication param){
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		Uri existingUri = TicketContract.Parametre.CONTENT_URI.buildUpon().appendPath(Integer.toString(0)).build();
	 	 batch.add(ContentProviderOperation.newUpdate(existingUri) 			
                .withValue(TicketContract.Parametre.COLUMN_LIGNE,param.getLine())
                .withValue(TicketContract.Parametre.COLUMN_USER, param.getUser())
                .withValue(TicketContract.Parametre.COLUMN_GIE, param.getOrganisation())
                .withValue(TicketContract.Parametre.COLUMN_ITINERAIRE, param.getItineraire())
                .withValue(TicketContract.Parametre.COLUMN_SECTION, param.getSection())
                .withValue(TicketContract.Parametre.COLUMN_BUS, param.getBus())
                .withValue(TicketContract.Parametre.COLUMN_TOKEN, param.getToken()) 
                .withValue(TicketContract.Parametre.COLUMN_PASSWORD, param.getPassword())
                .withValue(TicketContract.Parametre.COLUMN_FIRST, param.getFirst_road()) 
                .withValue(TicketContract.Parametre.COLUMN_BACK, param.getBack_road())
               .build());
	 	Log.i("tag", "updte ticket  " + param.toString());
	 	try {
			context.getContentResolver().applyBatch(TicketContract.CONTENT_AUTHORITY, batch);
			context.getContentResolver().notifyChange(TicketContract.Parametre.CONTENT_URI, null,false);
			return true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private static Parametrage cursorToParam(Cursor cursor) {
	    Parametrage paramet = new Parametrage();
	    paramet.setId(cursor.getInt(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_ID)));
	    paramet.setUser(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_USER)));
	    paramet.setBus(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_BUS)));
	    paramet.setOrganisation(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_GIE)));
	    paramet.setLine(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_LIGNE)));
	    paramet.setItineraire(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_ITINERAIRE)));	    
	    paramet.setSections(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_SECTION)));
	    paramet.setPassword(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_PASSWORD)));
	    paramet.setToken(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_TOKEN)));
	    paramet.setFirst_road(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_FIRST)));
	    paramet.setBack_road(cursor.getString(cursor.getColumnIndex(TicketContract.Parametre.COLUMN_BACK)));
	    return paramet;
	  }
	
	 public static String generateString (String _long, String _short )
		{
		  int diff = _long.length() - _short.length();
		  for (int i=0;i<diff;i++)
		  {
			_short=_short+" ";  
		  }
		  return _short;	
		}
	 	
	public static String getAllRapport(Context context) {
	 // List<Rapport> values = TicketUtils.getRapport(context);
		
	  List<Rapport> values = TicketUtils.getRapportByType(context, "SIMPLE");
	  List<Rapport> card_values = TicketUtils.getRapportByType(context, "CARD");
	  SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRENCH);
	  SimpleDateFormat sdfDateTime1 = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
	  String debuttime = sdfDateTime1.format(new Date(System.currentTimeMillis()));
	  String newtime = sdfDateTime.format(new Date(System.currentTimeMillis()));

	  String result="================================\n";
	  result = result  + "==========COMPAGNIE  ATT=======\n";
	  result = result+ "================================\n";
	  GlobalApplication application = ((GlobalApplication)context);
	  result = result +"Rapport "+application.getOrganisation()+" \n";
	  result = result +"Ligne : "+ application.getLine()+"  \n";
	  result = result +"Bus : " +application.getBus()+"  \n";
	  result = result +"Receveur : "+ application.getUser()+"  \n";
	  result = result +"Chauffeur : "+ application.getChauffeur()+"  \n";
	  result = result +"Date debut : "+debuttime+" 00:00:00"+"\n";
	  result = result +"Date  fin : "+newtime+"  \n\n";
	  
	  result= result +"====TICKETS VENDUS PAR CARTE ==\n";
	  
	  
	  int ctickets=0,ctotal=0;
	  for (int i=0 ;i<card_values.size(); i++)
	  {
	      Rapport rapport1 = (Rapport)card_values.get(i);
	      result = result +"| "+ generateString("SECTION",rapport1.getSection())+" ";
		  result = result +"| "+ generateString("CARTE",Integer.toString(rapport1.getTickets()))+" ";
		  result = result +"|"+ generateString("MONTANT",Integer.toString(rapport1.getTotal()))+"FCFA";
		  result = result +"\n";
		  ctickets= ctickets+rapport1.getTickets();
		  ctotal=ctotal+rapport1.getTotal();
		  
	  }
	  result= result +"\n";
	  result=result +"Ticket: "+ctickets+"\n";
	  
	  
	  
	  result= result +"\n===TICKETS VENDUS PAR ESPECE ==\n";
	  
	  result= result+"| SECTION | TICKETS | MONTANT    \n";
	  int tickets=0,total=0;
	  for (int i=0 ;i<values.size(); i++)
	  {
	      Rapport rapport = (Rapport)values.get(i);
	      result = result +"| "+ generateString("SECTION",rapport.getSection())+" ";
		  result = result +"| "+ generateString("TICKETS",Integer.toString(rapport.getTickets()))+" ";
		  result = result +"|"+ generateString("MONTANT",Integer.toString(rapport.getTotal()))+"FCFA";
		  result = result +"\n";
		  tickets= tickets+rapport.getTickets();
		  total=total+rapport.getTotal();
		  
	  }
	  result= result +"\n";
	  result=result +"Ticket: "+tickets+" ";
	  result=result +"Total: "+total+" FCFA\n";
	  result= result +"================================\n";;
	  
	  int net = total;
	  
	   

	  //Log.i("----------- " , result);
	  return result;
}
	
	public static String resume(Context context) {
		 // List<Rapport> values = TicketUtils.getRapport(context);
			
		 // List<Rapport> values = TicketUtils.getRapportByType(context, "SIMPLE");
		  //List<Rapport> card_values = TicketUtils.getRapportByType(context, "CARD");
		  SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRENCH);
		  SimpleDateFormat sdfDateTime1 = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
		  String debuttime = sdfDateTime1.format(new Date(System.currentTimeMillis()));
		  String newtime = sdfDateTime.format(new Date(System.currentTimeMillis()));

		  String result="================================\n";
		  result = result  + "==========COMPAGNIE  ATT=======\n";
		  result = result+ "================================\n";
		  GlobalApplication application = ((GlobalApplication)context);
		  result = result +"Ligne : "+ application.getLine()+"  \n";
		  result = result +"Bus : " +application.getBus()+"  \n";
		  result = result +"Receveur : "+ application.getUser()+"  \n";
		  result = result +"Chauffeur : "+ application.getChauffeur()+"  \n";
		  result = result +"Heure de connexion : "+application.getDateDeb()+"  \n";
		  result = result +"Intineraire : "+application.getItineraire()+"  \n";
		  result = result +"ZONE : "+application.getSectionnement()+"  \n\n";
		  result=result+"================================\n";
		  result = result  + "-----------INSTRUCTION-----------\n";
		  result = result+ "================================\n";
		  result = result+"1 – Après votre prise de service, le choix de la ligne et du sens, il faut connecter l’imprimante en cliquant dans la loupe a cote de TTS TRANS et cliquer sur le nom de l’imprimante.\n ";
		  result = result+ "2 – Quand vous avez sous TTS TRANS le message « BT deconnecter » cela veut dire que l’imprimante n’est pas encore connectee\n";
		  result = result+ "3 – Quand vous avez sous TTS TRANS le message « connecter : PTP-II » cela veut dire l’imprimante est bien connectée\n";
		  result = result+ "4 – Après avoir connecté l’imprimante, il faut charger des tickets en allant dans le menu « Chargement de tickets » \n";
		  result = result+ "5 – Il faut rentrer le nom du Chauffeur en allant dans le menu « Paramétrages »\n";
		  result = result+ "6 – Il faut toujours vérifier l’itinéraire avant de démarrer un trajet.\n";
		  result = result+ "7 – A la fin de chaque trajet, il faut changer l’itinéraire dans le menu en allant dans « Changer Itinéraire ».\n";
		  result = result+ "8 – A chaque entame de section, il faut oblitérer en changer le sectionnement dans le menu en allant dans « Sectionnement »\n";
		  result = result+ "9 – Si vous avez des tickets dans le téléphone, vous ne pouvez pas charger de ticket. Il faut les finir pour pouvoir charger a nouveau des tickets. \n";
		  result = result+ "10 – A chaque fin de service, il faut vérifier que le message « Fin de service terminée avec succes » est bien affiché. Si vous n’avez pas ce message, la fin de service n’est pas effective. \n";
		  result = result+ "11 –Après chaque fin de service il faut vérifier que dans « Rapport » les tickets et cartes sont a 0. SI c’est le cas, la fin de service est bien effectuee avec succes. \n";
		  result = result+ "12 – En résume, si la fin de service est bien passée, vous avez un rapport qui est imprimé puis le message suivant « Fin de service effectuee avec succés » et dans « Rapport » les tickets et cartes sont a 0.\n";
		  result = result+ "13 – Avant de se déconnecter de l’application, il faut déconnecter l’imprimante en appliquant le même processus lors de la connexion de l’imprimante puis se déconnecter en allant dans le menu « Déconnexion ». \n";
		  result = result+ "14 – Après votre fin de service, vous ne pouvez plus rien faire, Il faut se déconnecter de l’application.\n";
		  //Log.i("----------- " , result);
		  return result;
	}

	
	
	public static JSONObject ticketToJSONObject(Ticket ticket) throws JSONException
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
		//jsonObject.put("device", ticket.getDevice());
		jsonObject.put("reference", ticket.getReference());
		jsonObject.put("selled_date", ticket.getDate());
		jsonObject.put("actor", ticket.getUser());
		jsonObject.put("card", ticket.getCard());
		jsonObject.put("type", ticket.getType());
		return jsonObject;
	}
    
    public static JSONObject depenseToJSONObject(Depense depense) throws JSONException
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
		jsonObject.put("reference", depense.getReference());
		
		jsonObject.put("date", depense.getDate());
		return jsonObject;
		
	}
    
    
    
    public static JSONObject getDatasToSync(Context context) throws JSONException
    {
    	List<Ticket> sync_tickets;
    	List<Depense> sync_depenses;
   	    sync_tickets = TicketUtils.getSelledTickets(context, "1", "0");
    	sync_depenses = TicketUtils.getDepenses(context, "0");
    	if(sync_tickets.size()>0 ||sync_depenses.size()>0){
    	
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
	    jsonobject.put("device", GlobalApplication.PHONEIMEI);
	    jsonobject.put("imsi", GlobalApplication.PHONEIMSI);
	  
	    return jsonobject;
	    
    	}
		return null;
    }
    
    
    
    public static JSONObject getDatasToSync( List<Ticket> sync_tickets,List<Depense> sync_depenses) throws JSONException
    {
    	if(sync_tickets.size()>0 ||sync_depenses.size()>0){
    	
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
	    jsonobject.put("device", GlobalApplication.PHONEIMEI);
	    jsonobject.put("imsi", GlobalApplication.PHONEIMSI);
	  
	    return jsonobject;
	    
    	}
		return null;
    }
    
    
    
	
}
