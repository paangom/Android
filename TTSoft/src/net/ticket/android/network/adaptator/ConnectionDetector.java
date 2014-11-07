package net.ticket.android.network.adaptator;



import net.ticket.android.settings.GlobalApplication;
import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

public class ConnectionDetector { 
	private Context _context;

	public ConnectionDetector(Context context){
	   this._context = context;
	}
	
	public boolean isConnectingToInternet(){
	   ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
	     return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected());
		   
	}
	
	public boolean hasSimCard()
	{
		
		TelephonyManager phoneManager = (TelephonyManager)_context.getSystemService(Context.TELEPHONY_SERVICE);
		if (phoneManager != null) {
			 
			if(phoneManager.getSubscriberId() !=null)
			{GlobalApplication.PHONEIMSI = phoneManager.getSubscriberId().toString();
			 GlobalApplication.PHONEIMEI =phoneManager.getDeviceId();
			 return true;
			 }
			else return false;
	    }
		return false;
	}
	

	
}