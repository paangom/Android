package net.ticket.android.bluebamboo;


import java.util.Calendar;
import java.util.List;





import net.ticket.android.network.provider.Depense;
import net.ticket.android.network.provider.TicketUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DepensesFragment extends Fragment {
	

	private long select_id;
	private Boolean to_update=false;
	private Button saveBtn;
	private EditText editTextStationnement=null;
	private EditText editTextGazoil=null;
	private EditText editTextDepannage=null;
	private EditText editTextDivers=null;
	
	private EditText editTextRegulateur=null;
	private EditText editTextGardiennage=null;
	private EditText editTextLavage=null;
	private EditText editTextRation=null;
	private EditText editTextPrime=null;
	
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.resume, container, false);
		TextView text=(TextView) rootView.findViewById(R.id.resum);
		
		text.setText(TicketUtils.resume(getActivity().getApplicationContext()));
		return rootView;
        
    }
   
  
}