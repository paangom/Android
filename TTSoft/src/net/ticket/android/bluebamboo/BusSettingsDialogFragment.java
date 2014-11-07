package net.ticket.android.bluebamboo;

import net.ticket.android.settings.GlobalApplication;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class BusSettingsDialogFragment extends DialogFragment{
	
	  EditText driverEditText;
	   EditText litrageEditText;
	   EditText priceEditText;
	   String text = "";

	 
	 @Override
	 public Dialog onCreateDialog(Bundle savedInstanceState) {
		 GlobalApplication application = ((GlobalApplication)getActivity().getApplicationContext());
	     AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	     // Get the layout inflater
	     LayoutInflater inflater = getActivity().getLayoutInflater();

	     // Inflate and set the layout for the dialog
	     // Pass null as the parent view because its going in the dialog layout
	     View v = inflater.inflate(R.layout.settings, null);
	     driverEditText = (EditText) v.findViewById(R.id.chauffeur);
		 //litrageEditText = (EditText) v.findViewById(R.id.litrage);
		 //priceEditText = (EditText) v.findViewById(R.id.unit_price);
		 if ((application.getChauffeur() !=null) && (application.getChauffeur().length()>0))
			 driverEditText.setText(application.getChauffeur());
		 /*if ((application.getChauffeur() !=null) && (application.getChauffeur().length()>0))
			 litrageEditText.setText(application.getGazoil_litrage());
		 if ((application.getGazoil_unitprice() != null) && (application.getGazoil_unitprice().length()>0))
			 priceEditText.setText(application.getGazoil_unitprice());*/
		 builder.setTitle("Parametrages du bus");
	     builder.setView(v)
	     // Add action buttons
	            .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int id) {
	                  
	                	
	                	String driver=driverEditText.getText().toString();
	                    //String gazoil=litrageEditText.getText().toString();
	                    //String unit_price=priceEditText.getText().toString();
	                	
	                	if(driver.length()>0 )
	                    {  
	                	GlobalApplication application = ((GlobalApplication)getActivity().getApplicationContext());
	                    //application.setGazoil_litrage(gazoil);
	                    application.setChauffeur(driver);
	                    //application.setGazoil_unitprice(unit_price);
	                    

	                    }
	                    else 
	                    {
	                        Toast.makeText(getActivity(), "le nom du chauffeur est obligatoire", Toast.LENGTH_LONG).show();
	                        
	                    }
	                
	                	
	                }
	            })
	            .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                	BusSettingsDialogFragment.this.getDialog().cancel();
	                }
	            });      
	     return builder.create();
	 }
	}
