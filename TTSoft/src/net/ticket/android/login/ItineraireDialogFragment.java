package net.ticket.android.login;

import java.util.List;

import net.ticket.android.bluebamboo.MainActivity;
import net.ticket.android.bluebamboo.R;


import net.ticket.android.settings.GlobalApplication;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ItineraireDialogFragment   extends DialogFragment {
	 public static final String DATA = "items";
     public static final String SELECTED = "selected";

     @Override
 	public Dialog onCreateDialog(Bundle savedInstanceState) {
 		Bundle bundle = getArguments();
 		final List<String> list = (List<String>)bundle.get(DATA);
        final int position = bundle.getInt(SELECTED);
        CharSequence[] lines = list.toArray(new CharSequence[list.size()]);
 	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
 	    builder.setTitle("Selectionner l'itineraire")
 	           .setSingleChoiceItems(lines, position ,new DialogInterface.OnClickListener() {
 	        	   	@Override
 	        	   	public void onClick(DialogInterface dialog, int which) {
 	        	   	//NoticeDialogListener activity = (NoticeDialogListener) getActivity();
        	   		//activity.onDialogPositiveClick(dialog,list.get(which));
 	        	   	   // Log.i("itineraire selectionner ", list.get(which));
 	        	   		dialog.dismiss();
 	        	   		int pos = position;
 	        	   		Log.i("id  ", ""+which);
 	        	   		if (which!=-1) pos=which;
 	        	   		Log.i("itineraire selectionner ", list.get(pos));
 	        	   		GlobalApplication application = ((GlobalApplication)getActivity().getApplicationContext());
	 	    	        application.setItineraire(list.get(pos));
	 	    	       if(getActivity().getClass() != MainActivity.class)
 	            	   { 
	 	    	    	 Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class); 
 	          	  
 	          		    startActivity(intent);
 	          		   // getActivity().finish();
 	          		   }
 	        	   }
 	           	})
 	           .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
 	               @Override
 	               public void onClick(DialogInterface dialog, int id) {
 	            	  
 	            	   if(getActivity().getClass() != MainActivity.class)
 	            	   { Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class); 
 	          	  
 	          		   startActivity(intent);
 	          		//getActivity().finish();
 	          		   }
 	            	
	                
 	               }
 	           })
 	           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
 	               @Override
 	               public void onClick(DialogInterface dialog, int id) {
 	            	   dialog.dismiss();
 	                
 	               }
 	           });

 	    return builder.create();
 	}

 }