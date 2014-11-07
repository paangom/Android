package net.ticket.android.bluebamboo;

import net.ticket.android.settings.GlobalApplication;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TicketsFragment extends Fragment {
	public static Button goSection0 = null;
	public static Button goSection1 = null;
	public static Button goSection2 = null;
	public static Button goSection3 = null;
	public static Button goSection4 = null;
	public static Button goSection5 = null;
	
	//beni
	public static Button goSection20 = null;
	public static Button goSection21 = null;
	public static Button goSection22 = null;
	public static Button goSection23 = null;
	public static Button goSection24 = null;
	public static Button goSection25 = null;

	public static Button imprimer=null;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.benin, container, false);
		init(rootView);
		if(GlobalApplication.is_device_connect)
		{showConnected();}
		else showDisonnected();
		return rootView;
	}
	
	
	
	private void showDisabled() {
		
	}
	
	private void showEnabled() {		
		
	}
	
	public void showUnsupported() {
		/*goSection0.setEnabled(false);
		goSection1.setEnabled(false);
		goSection2.setEnabled(false);
		goSection3.setEnabled(false);
		goSection4.setEnabled(false);
		goSection5.setEnabled(false);*/
		
		//
		goSection20.setEnabled(false);
		goSection21.setEnabled(false);
		goSection22.setEnabled(false);
		goSection23.setEnabled(false);
		goSection24.setEnabled(false);
		goSection25.setEnabled(false);
		goSection25.setEnabled(false);
		
		//imprimer.setEnabled(false);
		

	}
	
	private void showConnected() {
		/*goSection0.setEnabled(true);
		goSection1.setEnabled(true);
		goSection2.setEnabled(true);
		goSection3.setEnabled(true);
		goSection4.setEnabled(true);
		goSection5.setEnabled(true);*/
		
		//benin
		goSection20.setEnabled(true);
		goSection21.setEnabled(true);
		goSection22.setEnabled(true);
		goSection23.setEnabled(true);
		goSection24.setEnabled(true);
		goSection25.setEnabled(true);
		
		
		//imprimer.setEnabled(false);
		
	
	}
	
	private void showDisonnected() {
		
	  
		/*goSection0.setEnabled(false);
		goSection1.setEnabled(false);
		goSection2.setEnabled(false);
		goSection3.setEnabled(false);
		goSection4.setEnabled(false);
		goSection5.setEnabled(false);*/
		
		//benin
		
		goSection20.setEnabled(false);
		goSection21.setEnabled(false);
		goSection22.setEnabled(false);
		goSection23.setEnabled(false);
		goSection24.setEnabled(false);
		goSection25.setEnabled(false);
		//goSection26.setEnabled(false);
		
		//imprimer.setEnabled(false);
		
	}
	
	
	private void init(View v)
	{
		GlobalApplication application = ((GlobalApplication) getActivity().getApplicationContext());
	 
		/*goSection0 = (Button) v.findViewById(R.id.goSection0);
		goSection1 = (Button) v.findViewById(R.id.goSection1);
    	goSection2 = (Button) v.findViewById(R.id.goSection2);
    	goSection3 = (Button) v.findViewById(R.id.goSection3);
    	goSection4 = (Button) v.findViewById(R.id.goSection4);
    	goSection5 = (Button) v.findViewById(R.id.goSection5);*/
    	
    	//benin
    	goSection20 = (Button) v.findViewById(R.id.goSection20);
		goSection21 = (Button) v.findViewById(R.id.goSection21);
    	goSection22 = (Button) v.findViewById(R.id.goSection22);
    	goSection23 = (Button) v.findViewById(R.id.goSection23);
    	goSection24 = (Button) v.findViewById(R.id.goSection24);
    	goSection25 = (Button) v.findViewById(R.id.goSection25);
    	
    	
    	imprimer= (Button) v.findViewById(R.id.imprim);
    	

    	/*if(!application.getSections().contains("0"))
    	{
    		
    		goSection0.setEnabled(false);
        	goSection0.setVisibility(4);
        	goSection0.setVisibility(View.GONE);
    		
    	}
    	if(!application.getSections().contains("1"))
    	{
    		goSection1.setEnabled(false);
        	goSection1.setVisibility(4);
        	goSection1.setVisibility(View.GONE);
    		
    	}
    	if(!application.getSections().contains("2"))
    	{
    		goSection2.setEnabled(false);
        	goSection2.setVisibility(4);
        	goSection2.setVisibility(View.GONE);
    		
    	}
    	if(!application.getSections().contains("3"))
    	{
    		goSection3.setEnabled(false);
        	goSection3.setVisibility(4);
        	goSection3.setVisibility(View.GONE);
    		
    	}
    	
    	if(!application.getSections().contains("4"))
    	{
    		goSection4.setEnabled(false);
        	goSection4.setVisibility(4);
        	goSection4.setVisibility(View.GONE);
    		
    	}
    	if(!application.getSections().contains("5"))
    	{
    		goSection5.setEnabled(false);
        	goSection5.setVisibility(4);
        	goSection5.setVisibility(View.GONE);
    		
    	}*/
    	
    	//benin
    	if(!application.getSections().contains("20"))
    	{
    		goSection20.setEnabled(false);
        	goSection20.setVisibility(4);
        	goSection20.setVisibility(View.GONE);
    		
    	}
    	if(!application.getSections().contains("21"))
    	{
    		goSection21.setEnabled(false);
        	goSection21.setVisibility(4);
        	goSection21.setVisibility(View.GONE);
    	}
    	
    	if(!application.getSections().contains("22"))
    	{
    		goSection22.setEnabled(false);
        	goSection22.setVisibility(4);
        	goSection22.setVisibility(View.GONE);
    		
    	}
    	if(!application.getSections().contains("23"))
    	{
    		goSection23.setEnabled(false);
        	goSection23.setVisibility(4);
        	goSection23.setVisibility(View.GONE);
    		
    	}
    	if(!application.getSections().contains("24"))
    	{
    		goSection24.setEnabled(false);
        	goSection24.setVisibility(4);
        	goSection24.setVisibility(View.GONE);
    		
    	}
    	if(!application.getSections().contains("25"))
    	{
    		goSection25.setEnabled(false);
        	goSection25.setVisibility(4);
        	goSection25.setVisibility(View.GONE);
    			
    	}
    	
    	
    
    	/*goSection0.setEnabled(false);
    	goSection1.setEnabled(false);
		goSection2.setEnabled(false);
		goSection3.setEnabled(false);
		goSection4.setEnabled(false);
		goSection5.setEnabled(false);*/
		
		//benin
		goSection20.setEnabled(false);
    	goSection21.setEnabled(false);
		goSection22.setEnabled(false);
		goSection23.setEnabled(false);
		goSection24.setEnabled(false);
		goSection25.setEnabled(false);
		
			
		
	}

	public void changeButtonsState(boolean enabled)
	{
	  if (enabled==true)
		  showConnected();
	  else showDisonnected();
	}

		
	
}
