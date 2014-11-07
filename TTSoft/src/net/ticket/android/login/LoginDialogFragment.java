package net.ticket.android.login;

import java.util.List;

import net.ticket.android.bluebamboo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class LoginDialogFragment extends DialogFragment{
	 public static final String DATA = "items";
     public static final String SELECTED = "selected";
     NoticeDialogListener mListener;
	 /**
	     * Create a new instance of MyDialogFragment, providing "num"
	     * as an argument.
	     */
	  
	    public interface NoticeDialogListener {
	        public void onDialogPositiveClick(DialogInterface dialog, String line);
	        public void onDialogNegativeClick(DialogFragment dialog);
	    }
	    
	    @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        // Verify that the host activity implements the callback interface
	        try {
	            // Instantiate the NoticeDialogListener so we can send events to the host
	            mListener = (NoticeDialogListener) activity;
	        } catch (ClassCastException e) {
	            // The activity doesn't implement the interface, throw exception
	            throw new ClassCastException(activity.toString()
	                    + " must implement NoticeDialogListener");
	        }
	    }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		final List<String> list = (List<String>)bundle.get(DATA);
        final int position = bundle.getInt(SELECTED);
        CharSequence[] lines = list.toArray(new CharSequence[list.size()]);
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setTitle("Selectionner votre ligne ")
	           .setMessage("Bienvenue dans la plateforme de vente de tickets. \n Vous ?tes sur la ligne "+list.get(0))
	    // Set the action buttons
	           .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	 
	            	   NoticeDialogListener activity = (NoticeDialogListener) getActivity();
	            	   int pos = position;
	            	   if (id!=-1) pos=id;
	            	   Log.i("position ==========================" , list.get(0));
	        	   		activity.onDialogPositiveClick(dialog,list.get(0));
	                    dialog.dismiss();
	                
	               }
	           });
	           

	    return builder.create();
	}

}
