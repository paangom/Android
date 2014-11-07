package net.ticket.android.bluebamboo;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.ticket.android.network.provider.Depense;
import net.ticket.android.network.provider.Rapport;
import net.ticket.android.network.provider.TicketUtils;
import net.ticket.android.settings.GlobalApplication;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RapportFragment extends Fragment {

	private Button printBtn = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_report, container, false);
		TextView  text = (TextView) rootView.findViewById(R.id.rapporttext);
		printBtn = (Button) rootView .findViewById(R.id.printRapport);
		if(GlobalApplication.is_device_connect)
		{printBtn.setEnabled(true);}
		else printBtn.setEnabled(false);
		text.setText(TicketUtils.getAllRapport(getActivity().getApplicationContext()));
		return rootView;
	}



public void changeButtonState(boolean enabled)
{
 printBtn.setEnabled(enabled);
}



}