package net.ticket.android.login;

import net.ticket.android.login.LoginDialogFragment.NoticeDialogListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ServiceDialogFragment extends DialogFragment {
	ServiceDialogListener mListener;
	
	
	 public interface ServiceDialogListener {
	        public void onServiceDialogPositiveClick(DialogInterface dialog);
	        public void onServiceDialogNegativeClick(DialogFragment dialog);
	    }
	 
	 
	 
	 @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        // Verify that the host activity implements the callback interface
	        try {
	            // Instantiate the NoticeDialogListener so we can send events to the host
	            mListener = (ServiceDialogListener) activity;
	        } catch (ClassCastException e) {
	            // The activity doesn't implement the interface, throw exception
	            throw new ClassCastException(activity.toString()
	                    + " must implement NoticeDialogListener");
	        }
	    }
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        builder.setMessage("Demarrer votre service ?")
               .setPositiveButton("Demarrer", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   
                	   ServiceDialogListener activity = (ServiceDialogListener) getActivity();
	        	   		activity.onServiceDialogPositiveClick(dialog);
	        	   		dialog.dismiss();
                     
                	   }
               })
               .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   ServiceDialogFragment.this.getDialog().cancel();
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }

	    
	   
}