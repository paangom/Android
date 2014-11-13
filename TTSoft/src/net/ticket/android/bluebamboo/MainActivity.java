package net.ticket.android.bluebamboo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.ticket.android.bluebamboo.adapter.TabsPagerAdapter;
import net.ticket.android.bluebamboo.pockdata.PocketPos;
import net.ticket.android.bluebamboo.util.FontDefine;
import net.ticket.android.bluebamboo.util.Printer;
import net.ticket.android.customer.Subscription;
import net.ticket.android.login.ItineraireDialogFragment;
import net.ticket.android.login.SectionFragment;
import net.ticket.android.network.adaptator.ConnectionDetector;
import net.ticket.android.network.adaptator.SyncUtils;
import net.ticket.android.network.provider.Depense;
import net.ticket.android.network.provider.Parametrage;
import net.ticket.android.network.provider.Ticket;
import net.ticket.android.network.provider.TicketUtils;
import net.ticket.android.settings.GlobalApplication;
import net.ticket.android.settings.SecurityUtils;
import net.ticket.android.settings.SystemUiHider;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.ParseException;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;





@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener{
	
	private static final int MAX_TICKETS_TO_LOAD = 100;
/*	// Sound congfig 
	private SoundPool soundPool;
	private int soundID;
	boolean plays = false, loaded = false;
	float actVolume, maxVolume, volume;
	AudioManager audioManager;
	int counter;
	// NFC Config
   public static final String TAG = "NFC_TTSOFT";
 
	
	public static final String CRYPTED_KEY = "123$$$$_????ZER@#";
	public static final String DEFAULT_TEXT_MESSAGE = "Hello, NFC World by Pathe!";
	
	public static final String DEFAULT_AAR_TEXT_MESSAGE = "AAR detected!";
	public static final String DEFAULT_URL = "http://developer.sudpay.com";
	public static final String TEXT_PLAIN_MIME_TYPE = "text/plain";
	private static Tag mTag;
	private NdefRecord mNdefAARRecord = NdefRecord.createApplicationRecord("com.ttsoft.nfccard");
	private NdefMessage mDefaultNdefURIMesssage = new NdefMessage(new NdefRecord[]{NdefRecord.createUri(DEFAULT_URL)});
	public static final SecurityUtils securityUtils =  new SecurityUtils();
		
	private final Object syncObject = new Object();

	private enum WorkMode {
		MODE_READ, MODE_WRITE
	}
	WorkMode mMode = WorkMode.MODE_READ;
	// public AlertDialog mDialog;

	NfcAdapter mNfcAdapter;
	private PendingIntent mNfcPendingIntent;
	private IntentFilter[] mNdefFilters;

	*//**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 *//*
	private static final boolean AUTO_HIDE = true;

	*//**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 *//*
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	*//**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 *//*
	private static final boolean TOGGLE_ON_CLICK = true;

	*//**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 *//*
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	*//**
	 * The instance of the {@link SystemUiHider} for this activity.
	 *//*
	private SystemUiHider mSystemUiHider;*/
	
	// Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
	Boolean isInternetPresent = false;
	Boolean printRapport = false;
	Boolean hasSimCardPresent = false;
	ConnectionDetector cd;
	private BluetoothAdapter mBluetoothAdapter;
	private P25Connector mConnector;
	private ProgressDialog mProgressDlg;
	private ProgressDialog mProgressDialog;
	private ProgressDialog mConnectingDlg;
	private ArrayList<BluetoothDevice> mDeviceList = new ArrayList<BluetoothDevice>();

	private RequestQueue mRequestQueue;
	private ProgressDialog pd;
	private String LOG_TAG = "GenerateQRCode";
	private String TAG = this.getClass().getSimpleName();
	private List<Ticket> sync_tickets;
	private List<Depense> sync_depenses;	
	
	private  static ViewPager viewPager;
	private static TabsPagerAdapter mAdapter;
	private static ActionBar actionBar;
	private boolean eta;
	public boolean isEta() {
		return eta;
	}
	public void setEta(boolean eta) {
		this.eta = eta;
	}
	
	/*
	 * bar de progression chargement ticket offline
	 */
	
	public static final int MSG_CNF = 1;
	public static final int MSG_IND = 2;

	final Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_IND:
				if (mProgressDialog.isShowing()) {
					mProgressDialog.setMessage(((String) msg.obj));
				}
				break;
			case MSG_CNF:
				
				Toast.makeText(getApplicationContext(), "Chargement  de " +MAX_TICKETS_TO_LOAD+" tickets termine avec succes", Toast.LENGTH_LONG).show();
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
			
			default: // should never happen
				break;
			}
			
		}
	};
	// Tab titles
	private String[] tabs = { "Tickets", "Utilisation",  "Rapport" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		setContentView(R.layout.activity_main);
		SyncUtils.CreateSyncAccount(this);
		//handler = new Handler();
		//handler.postDelayed(myrunnable, 5000);
		//init();

		// Initilization
		
		// mode offline
		/*GlobalApplication application = ((GlobalApplication)getApplicationContext());
		TicketUtils.insert_parametre(getApplicationContext(), application);
			Parametrage para=TicketUtils.getParametre(getApplicationContext());
			System.out.println("toke"+para.getToken()+ "info"+para.getBus()+" "+para.getChauffeur()+" "+para.getItineraire()+" "+para.getLine()+" "+para.getSections()+" "+para.getOrganisation());
*/
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setTitle("TTS-TRANS");
		//actionBar.setIcon(R.drawable.logo3);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		cd = new ConnectionDetector(getApplicationContext());
		
		mBluetoothAdapter	= BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			//showUnsupported();
		} 
		else {
			if (!mBluetoothAdapter.isEnabled()) {
				//showDisabled();
			} else {
				//showEnabled();
				
				Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
				
				if (pairedDevices != null) {
					for (BluetoothDevice device : pairedDevices) {
						
						// PTP-II is the name of the bluetooth printer device
						if (device.getName().equals("PTP-II")) {
							
							//mDeviceList.add(device);
						    break;
						}
					}
					
					
					//updateDeviceList();
				}
			}
			
			mProgressDlg 	= new ProgressDialog(this);
			
			mProgressDlg.setMessage("Scanning...");
			mProgressDlg.setCancelable(false);
			mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        dialog.dismiss();
			        
			        mBluetoothAdapter.cancelDiscovery();
			    }
			});
			
			mConnectingDlg 	= new ProgressDialog(this);
			
			mConnectingDlg.setMessage("Connecting...");
			mConnectingDlg.setCancelable(false);
			
			mConnector 		= new P25Connector(new P25Connector.P25ConnectionListener() {
				
				@Override
				public void onStartConnecting() {
					mConnectingDlg.show();
				}
				
				@Override
				public void onConnectionSuccess() {
					mConnectingDlg.dismiss();
					
					showConnected();
				}
				
				@Override
				public void onConnectionFailed(String error) {
					mConnectingDlg.dismiss();
					setStatus("BT deconnecter");
				}
				
				@Override
				public void onConnectionCancelled() {
					mConnectingDlg.dismiss();
				}
				
				@Override
				public void onDisconnected() {
					showDisonnected();
					setStatus("BT deconnecter");
				}
			});
				
			
		}
		
		IntentFilter filter = new IntentFilter();
		
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		filter.addAction(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		
		registerReceiver(mReceiver, filter);
		
		if(!GlobalApplication.is_device_connect)
		{setStatus("BT deconnecter");
		}
		
		
		
		//final View contentView = findViewById(R.id.pager);
		
		
		/*mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
			// Cached values.
			int mControlsHeight;
			int mShortAnimTime;
			@Override
			public void onVisibilityChange(boolean visible) {
				// TODO Auto-generated method stub
				
			}

			
		});*/

		// Set up the user interaction to manually show or hide the system UI.
		/*contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});
		
		
		mNfcPendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter ndefFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
		try {
			ndefFilter.addDataType(TEXT_PLAIN_MIME_TYPE);
		} catch (MalformedMimeTypeException e) {
			e.printStackTrace();
			Log.e(TAG, "Error - MalformedMimeTypeException");
		}

		mNdefFilters = new IntentFilter[]{ndefFilter};

		mMode = WorkMode.MODE_READ;
		
		// AudioManager audio settings for adjusting the volume
		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = actVolume / maxVolume;

		//Hardware buttons setting to adjust the media sound
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		// the counter will help us recognize the stream id of the sound played  now
		counter = 0;

		// Load the sounds
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
		@Override
		public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
			loaded = true;
			}
		});
		soundID = soundPool.load(this, R.raw.beep, 1);
	}
	
	public void playSound(View v) {
		// Is the sound loaded does it already play?
		if (loaded && !plays) {
			soundPool.play(soundID, volume, volume, 1, 0, 1f);
			counter = counter++;
			Toast.makeText(this, "Carte non valide", Toast.LENGTH_SHORT).show();
			plays = true;
		}
	}

	public void playLoop(View v) {
		// Is the sound loaded does it already play?
		if (loaded && !plays) {

			// the sound will play for ever if we put the loop parameter -1
			soundPool.play(soundID, volume, volume, 1, -1, 1f);
			counter = counter++;
			Toast.makeText(this, "Plays loop", Toast.LENGTH_SHORT).show();
			plays = true;
		}
	}

	public void pauseSound(View v) {
		// Is the sound loaded already?
		if (plays) {
			soundPool.pause(soundID);
			soundID = soundPool.load(this, R.raw.beep, counter);
			Toast.makeText(this, "Pause sound", Toast.LENGTH_SHORT).show();
			plays = false;
		}
	}

	public void stopSound(View v) {
		// Is the sound loaded already?
		if (plays) {
			soundPool.stop(soundID);
			soundID = soundPool.load(this, R.raw.beep, counter);
			Toast.makeText(this, "Stop sound", Toast.LENGTH_SHORT).show();
			plays = false;
		}*/
	}
	@Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if ((mBluetoothAdapter != null) && (!mBluetoothAdapter.isEnabled())) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } 
    }
	
	
	private void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
    
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	
	/*
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(handler!= null)
			handler.removeCallbacks(myrunnable);
	}*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		GlobalApplication application = ((GlobalApplication)getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
       	hasSimCardPresent = cd.hasSimCard();
       	System.out.println("item"+item.getItemId());
		switch (item.getItemId()) {	
		case R.id.action_settings:
			if(application.isOn_service()){
				BusSettingsDialogFragmen  busfragment = new BusSettingsDialogFragmen();
				busfragment.show(getFragmentManager(), "msactivity");
			}
			else
				openAlert("Demande impossible:", "Vous avez terminer votre service pour aujourd'hui \n Veuillez vous reconnecter a nouveau pour demarrer un nouveau service");
			
			return true;
		case R.id.secure_connect_scan:
            // Launch the DeviceListActivity to see devices and do scan
			Intent serverIntent = null;
            serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            actionBar.setSelectedNavigationItem(0);
        	viewPager.setAdapter(mAdapter);
            return true;
		case R.id.action_logout:
			if(GlobalApplication.is_device_connect==true){
				new AlertDialog.Builder(this)
				.setTitle("Alerte")
				.setMessage("Veuillez d�connecter l'imprimante avant d�connexion!")
				.setNeutralButton("Close", new DialogInterface.OnClickListener() { 
					public void onClick(DialogInterface dlg, int sumthin) {
						
					}
				})
				.show();
				
			}
			else{
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
				dialogBuilder.setTitle("Alerte");
				dialogBuilder.setMessage("Voulez-vous vraiment vous d�connecter!.");
				dialogBuilder.setPositiveButton("OUI", new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dlg, int sumthin) {
						
						GlobalApplication application = ((GlobalApplication)getApplicationContext());
						System.out.println("etat service "+ application.isOn_service());
						application.logout();
						onBackPressed();		
					}
				});
				dialogBuilder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				AlertDialog alertDialog = dialogBuilder.create();
				alertDialog.show();
				
			}
			
			return true;
		case R.id.action_resetdata:
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
			dialogBuilder.setTitle("Alerte");
			dialogBuilder.setMessage("Voulez-vous confirmer la fin de Service! Attention Cette action est irréversible.");
			dialogBuilder.setPositiveButton("OUI", new DialogInterface.OnClickListener() { 
				@Override
				public void onClick(DialogInterface dlg, int sumthin) {
					
						endservice();
						actionBar.setSelectedNavigationItem(0);
					
					
				}
			});
			dialogBuilder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			AlertDialog alertDialog = dialogBuilder.create();
			alertDialog.show();

			return true;
			
		case R.id.action_loadtickets:
			Ticket ticket = new Ticket();
		    SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRENCH);
			
			long date  = System.currentTimeMillis()/1000;
			GlobalApplication applicatio = ((GlobalApplication)getApplicationContext());
		    ticket.setDate(date);
		    ticket.setStatut(1);
		    ticket.setIs_sync(0);
		    ticket.setUser(applicatio.getUser());
		    ticket.setBus(applicatio.getBus());
		    ticket.setLigne(applicatio.getLine());
		    ticket.setItineraire(applicatio.getItineraire());
		    ticket.setGie(applicatio.getOrganisation());
		    ticket.setSection(1);
		    ticket.setAmount(200);
			Ticket _ti = get_unselledTicket(ticket);
			
			if (_ti!=null){
				new AlertDialog.Builder(this)
				.setTitle("Alerte")
				.setMessage("Vous ne pouvez pas charger de tickets! il vous reste encore des tickets dans le device")
				.setNeutralButton("Close", new DialogInterface.OnClickListener() { 
					public void onClick(DialogInterface dlg, int sumthin) {
						
					}
				})
				.show();
			}
			else{
				String url = GlobalApplication.BASE_URL +"load/?format=json";
				 if(application.isOn_service())
				 {
				     	 try {
					     		if(isInternetPresent && hasSimCardPresent){
					     			JSONObject jsonSections = new JSONObject();
						     		int count = TicketUtils.getNumberofTickets(getApplicationContext(), "0") ;
						     		int num = MAX_TICKETS_TO_LOAD-count;
						     		System.out.println(" nombre ticket"+num);
						     		//openAlert("Erreur de saisie:", ""+num);
						     		if (num>0)
						        	{
						     			jsonSections.put(application.getSections().get(0), String.valueOf(num));
							       		JSONObject jsonObj = new JSONObject();
							       		jsonObj.put("sections", jsonSections);
										jsonObj.put("device", GlobalApplication.PHONEIMEI);
								       	jsonObj.put("imsi", GlobalApplication.PHONEIMSI);
								       	jsonObj.put("line", application.getLine());
								       	jsonObj.put("itineraire", application.getItineraire());
							        	loadTicket( url, jsonObj, application.getUser(), application.getPassword());
						        	}
					             }
					        	 else {
					        		 if(GlobalApplication.etat)
					        			 openAlert("Erreur de saisie:", "Verifier que vous avez internet");
					        		 else{
					        			 //chargementTicket();
					        		 }
					        		 
					        	 }
					       	
					       	
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 }
				 else {
	        		 openAlert("Demande impossible:", "Vous avez terminer votre service pour aujourd'hui \n Veuillez vous reconnecter a nouveau pour demarrer un nouveau service");
	        	 }
			}
			 
			 
	    
			return true;
		case R.id.action_switch:
			if(application.isOn_service()){
				ArrayList<String> lines = new ArrayList<String>();
	        	lines.add(application.getFirst_road());
	        	lines.add(application.getBack_road());
				ItineraireDialogFragmen itineraireDialog = new ItineraireDialogFragmen();
				Bundle bundle = new Bundle();
	            bundle.putStringArrayList(ItineraireDialogFragmen.DATA, lines);  
	            if (application.getFirst_road()== application.getItineraire())
	            	bundle.putInt(ItineraireDialogFragmen.SELECTED, 0);
	            else 
	            	bundle.putInt(ItineraireDialogFragmen.SELECTED, 1);
	            itineraireDialog.setArguments(bundle);
	            itineraireDialog.show(getFragmentManager(), "activity");
			}
			else
				openAlert("Demande impossible:", "Vous avez terminer votre service pour aujourd'hui \n Veuillez vous reconnecter a nouveau pour demarrer un nouveau service");
			
			
			return true;
			
	
		case R.id.action_sectionnement:
			if(application.isOn_service()){
				ArrayList<String> _lines = new ArrayList<String>();
				//{"A", "B","C","D", "E", "F", "G","H","I","J"};// 
				_lines.add("ZONE 1");_lines.add("ZONE 2");_lines.add("ZONE 3");_lines.add("ZONE 4");_lines.add("ZONE 5");
				//_lines.add("G");_lines.add("H");_lines.add("I");_lines.add("J");_lines.add("K");_lines.add("L");
	        	
				SectionFragmen sectionDialog = new SectionFragmen();
				Bundle _bundle = new Bundle();
	            _bundle.putStringArrayList(ItineraireDialogFragment.DATA, _lines); 
	            int index =_lines.indexOf(application.getSectionnement());
	            if (index!=0)
	            	_bundle.putInt(SectionFragmen.SELECTED,index );
	            else 
	            	_bundle.putInt(SectionFragmen.SELECTED, 0);
	            sectionDialog.setArguments(_bundle);
	            sectionDialog.show(getFragmentManager(), "activity");
	           
			}
			else
				openAlert("Demande impossible:", "Vous avez terminer votre service pour aujourd'hui \n Veuillez vous reconnecter a nouveau pour demarrer un nouveau service");
			
			
			
			return true;

		case R.id.action_sync:
			try {
				
				if(isInternetPresent && hasSimCardPresent){
	        		
		        	syncTickets("ON");
	             }
	        	 else {
	        		 openAlert("Erreur de saisie:", "Verifier que vous avez internet ");
	        	 }
				
				return true;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
		default:
			return super.onOptionsItemSelected(item);
		}
		}
	
	public void onConnect(View v)
	{
		//connect();
	}
	
	

	public void syncTickets(String service_status) throws JSONException
    {
    	String url = GlobalApplication.BASE_URL+"sync/?format=json";
   	 	GlobalApplication application = ((GlobalApplication)getApplicationContext());
   	 	sync_tickets = TicketUtils.getSelledTickets(getApplicationContext(), "1", "0");
   	 //Log.i(TAG,"======================= "+ sync_tickets.toString());
   	 
   	//Log.i(TAG,"my selled tickets  "+"************************");
   	 	sync_depenses = TicketUtils.getDepenses(getApplicationContext(), "0");
		//JSONObject jsonobject = TicketUtils.getDatasToSync(getApplicationContext());
   	    JSONObject jsonobject = TicketUtils.getDatasToSync(sync_tickets,sync_depenses);
   	    
		if (jsonobject!=null)
		{   
			jsonobject.put("service", service_status);
		}
		else
		{
			jsonobject = new JSONObject();
			jsonobject.put("device", GlobalApplication.PHONEIMEI);
		    jsonobject.put("imsi", GlobalApplication.PHONEIMSI);
		    jsonobject.put("depenses",new JSONArray());
		    jsonobject.put("tickets", new JSONArray());
			jsonobject.put("service", service_status);
		}
		loadSynchronisation(url,jsonobject,application.getUser(),application.getPassword());	
		
    }
    public void loadSynchronisation(String url, JSONObject params, final String username, final String password)
    {
    	
    	mRequestQueue =  Volley.newRequestQueue(this);  
        pd = ProgressDialog.show(this,"Synchronisation...","Svp attendre...");
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url, params ,synclistener,
                syncerrorListener){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            	GlobalApplication application = ((GlobalApplication)getApplicationContext());
                return application.createBasicAuthHeader(username, password);
                
            }
        };
        mRequestQueue.add(jr);
        //ApplicationController.getInstance().addToRequestQueue(jr);
    	
    }
    
    Response.Listener<JSONObject> synclistener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
        	
            Log.i("Success Response: ", response.toString());
            try {
            	System.out.println("servicePAS"+response.getString("service"));
				JSONObject ticketsdatas = new JSONObject(response.getString("tickets"));
				String ticket_status = ticketsdatas.getString("status");
				JSONObject depensesdatas = new JSONObject(response.getString("depenses"));
				String depense_status = depensesdatas.getString("status");
				String service = response.getString("service");
				 Log.i("Success status de service : ", service);
				 Log.i("Success Response: ", ticket_status);
				 Log.i("Success Response: ", depense_status);
				 if (depense_status.equals("success")  &&  sync_depenses.size()>0)
					 TicketUtils.batch_update_depenses(getApplicationContext(), sync_depenses);
				 if (ticket_status.equals("success") &&  sync_tickets.size()>0)
					 TicketUtils.batch_update_tickets(getApplicationContext(), sync_tickets);
				 if (service.equals("END"))
				 {
					 
					 TicketUtils.delete_all_rows(getApplicationContext());
					 GlobalApplication application = ((GlobalApplication)getApplicationContext());
					 application.setOn_service(false);
					 application.setGazoil_litrage("");
	                 application.setChauffeur("");
	                 application.setGazoil_unitprice("");
					 openAlert ("Alerte: ", "Fin de service effectuée avec succés");
				 }
					 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                 
            pd.dismiss();
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
        	openAlert ("Error Response code: ", message);
             pd.dismiss();
        }
    };
  
    private void openAlert(String title, String message) {
		 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
	     
		 alertDialogBuilder.setTitle(title);
		 alertDialogBuilder.setMessage(message);
		 alertDialogBuilder.setNegativeButton("OK",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// cancel the alert box and put a Toast to the user
					dialog.cancel();
		
				}
			});
		 
		 
		 AlertDialog alertDialog = alertDialogBuilder.create();
		 // show alert
		 alertDialog.show();
	}
	
    
	public Ticket jsonToTicket(JSONObject c)  throws ClientProtocolException, IOException, JSONException 
    {
		Ticket ticket = new Ticket();
		if(c.getString("is_sell")=="false"){ ticket.setStatut(0);}
        ticket.setLigne(c.getString("line"));
        ticket.setSection( Integer.parseInt(c.getString("section")));
        ticket.setAmount(0);
        ticket.setItineraire(c.getString("itineraire"));
        ticket.setGie(c.getString("organisation"));
        ticket.setBus(c.getString("bus"));
        ticket.setUser(c.getString("actor"));
        ticket.setEtat("online");
        ticket.setReference(c.getString("reference"));
        //ticket.setDevice(c.getString("device"));
        ticket.setGenerateDate(c.getString("generate_date"));
    	return ticket;
    	
    }
	
	public Ticket offToTicket(String datee, String a) 
    {
		GlobalApplication application = ((GlobalApplication)getApplicationContext());
		Ticket ticket = new Ticket();
		ticket.setStatut(0);
        ticket.setLigne(application.getLine());
        ticket.setSection(0);
        ticket.setAmount(0);
        ticket.setItineraire(application.getItineraire());
        ticket.setGie(application.getOrganisation());
        ticket.setBus(application.getBus());
        ticket.setUser(application.getChauffeur());
        ticket.setEtat("offline");
        ticket.setReference(application.getBus()+"OFF"+a);
        //ticket.setDevice(c.getString("device"));
        ticket.setGenerateDate(datee);
    	return ticket;
    	
    }
	
	
	public void loadTicket(String url, JSONObject params, final String username, final String password)
	    {
	    	
	    	 Log.i(TAG,params.toString());
	    	
	       mRequestQueue =  Volley.newRequestQueue(this);
	        
	        pd = ProgressDialog.show(this,"SVP veuillez attendre...","Chargement de tickets en cours");
	        
	        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url, params,new Response.Listener<JSONObject>() {
	            @Override
	            public void onResponse(JSONObject response) {
	                Log.i("RESPONSE DATA",response.toString());
	               try {
	      	      JSONArray jsonArray = new JSONArray(response.getString("data"));
	      	      //Log.i(LoginActivity.class.getName(), "Number of entries " + jsonArray.length());
	      	      List<Ticket> _tickets = new ArrayList<Ticket>();
	      	      for (int i = 0; i < jsonArray.length(); i++) {
	      	        JSONObject jsonObject = jsonArray.getJSONObject(i);
	      	        JSONObject jsonFields = new JSONObject(jsonObject.getString("fields"));
	      	        Ticket ticket = jsonToTicket(jsonFields);
	      	        _tickets.add(ticket);
	      	      }
	      	      
	      	    TicketUtils.batch_insert_tickets(getApplicationContext(), _tickets);
	      	    pd.dismiss();
	      	    Toast.makeText(getApplicationContext(), "Chargement  de " +jsonArray.length()+" tickets terminés avec succes", 300)
	      	    .show();
	      	      
	      	    } catch (Exception e) {
	      	      e.printStackTrace();
	      	    pd.dismiss();
	      	    }
	                
	            }
	        },new Response.ErrorListener() {
	            @Override
	            public void onErrorResponse(VolleyError error) {
	                Log.i(TAG,error.toString());
	                pd.dismiss();
	            }
	        }) {

	            @Override
	            public Map<String, String> getHeaders() throws AuthFailureError {
	            	GlobalApplication application = ((GlobalApplication)getApplicationContext());
	                return application.createBasicAuthHeader(username, password);
	            }
	        };
	        
	        jr.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
	        mRequestQueue.add(jr);
	       // ApplicationController.getInstance().addToRequestQueue(jr);
	        
	    	
	    	
	    }
 
	public void onDialogPositiveClick(DialogInterface dialog, String line) {
			//onItineraireDialogPositiveClick
			GlobalApplication application = ((GlobalApplication)getApplicationContext());
			application.setItineraire(line);
		}
	  
	 	
   private final void setStatus(CharSequence subTitle) {
		        final ActionBar actionBar = getActionBar();
		        actionBar.setSubtitle(subTitle);
		        //TicketsFragment.goSection1.setEnabled(GlobalApplication.is_device_connect);
		        // int i=viewPager.getCurrentItem();
		        //TicketsFragment rfragment = (TicketsFragment) mAdapter.getItem(0);
		       // if( rfragment!=null)
		        	//TicketsFragment.goSection1.setEnabled(GlobalApplication.is_device_connect);
		       // rfragment.changeButtonState(GlobalApplication.is_device_connect);
		          
		    }
		 
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
		        Log.d(TAG, "onActivityResult " + resultCode);
		        switch (requestCode) {
		        case REQUEST_CONNECT_DEVICE_SECURE:
		            // When DeviceListActivity returns with a device to connect
		            if (resultCode == Activity.RESULT_OK) {
		                connectDevice(data, true);
		            	Log.d(TAG, " connect to device");
		            }
		            break;
		        case REQUEST_CONNECT_DEVICE_INSECURE:
		            // When DeviceListActivity returns with a device to connect
		            if (resultCode == Activity.RESULT_OK) {
		                connectDevice(data, false);
		            	Log.d(TAG, " connect to device");
		            }
		            break;
		        case REQUEST_ENABLE_BT:
		            // When the request to enable Bluetooth returns
		            if (resultCode == Activity.RESULT_OK) {
		                // Bluetooth is now enabled, so set up a chat session
		            	Log.d(TAG, "BT now enabled");
		               // setupChat();
		            } else {
		                // User did not enable Bluetooth or an error occurred
		                Log.d(TAG, "BT not enabled");
		                Toast.makeText(this, "BT not enabled", Toast.LENGTH_SHORT).show();
		                finish();
		            }
		        }
		    }

  private void createBond(BluetoothDevice device) throws Exception { 
		        
		        try {
		            Class<?> cl 	= Class.forName("android.bluetooth.BluetoothDevice");
		            Class<?>[] par 	= {};
		            
		            Method method 	= cl.getMethod("createBond", par);
		            
		            method.invoke(device);
		            
		        } catch (Exception e) {
		            e.printStackTrace();
		            
		            throw e;
		        }
		    }
			
  private void connect() {
				if (mDeviceList == null || mDeviceList.size() == 0) {
					return;
				}
				
				BluetoothDevice device = mDeviceList.get(0);
				
				if (device.getBondState() == BluetoothDevice.BOND_NONE) {
					try {
						createBond(device);
					} catch (Exception e) {
						showToast("Failed to pair device");
						
						return;
					}
				}
				
				try {
					if (!mConnector.isConnected()) {
						mConnector.connect(device);
						setStatus("connecter :"+ device.getName());
					} else {
						mConnector.disconnect();
						setStatus("non connecter :");
						showDisonnected();
					}
				} catch (P25ConnectionException e) {
					e.printStackTrace();
				}
			}
				
			
  private void connectDevice(Intent data, boolean secure) {
		        // Get the device MAC address
		        String address = data.getExtras()
		            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		        // Get the BluetoothDevice object
		        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		        // Attempt to connect to the device
		        if (device.getBondState() == BluetoothDevice.BOND_NONE) {
					try {
						createBond(device);
					} catch (Exception e) {
						showToast("Failed to pair device");
						
						return;
					}
				}
				
				try {
					if (!mConnector.isConnected()) {
						mConnector.connect(device);
						setStatus("connecter :"+ device.getName());
					} else {
						mConnector.disconnect();
						setStatus("BT deconnecter");
						showDisonnected();
					}
				} catch (P25ConnectionException e) {
					e.printStackTrace();
				}
		    }
		
  private void showDisabled() {
				showToast("Imprimante desactiver");
				
				GlobalApplication.is_bt_enabled=false;
			}
			
  private void showEnabled() {		
				showToast("Imprimante activer");
				GlobalApplication.is_bt_enabled=true;
			}
			
  private void showUnsupported() {
				showToast("Ce appareil ne supporte le bluetooth");

				GlobalApplication.bt_unsupported=true;
				
			}
			
  private void showConnected() {
				showToast("Imprimante connecter");
				GlobalApplication.is_device_connect=true;
				actionBar.setSelectedNavigationItem(0);
				viewPager.setAdapter(mAdapter);
				imprimerAuto();
				
			}
			
  private void showDisonnected() {
				showToast("Imprimante deconnecter");
				GlobalApplication.is_device_connect=false;
				
				
			}
			
  public boolean sendData(byte[] bytes) {
		try {			
			return mConnector.sendData(bytes);
		} catch (P25ConnectionException e) {
			e.printStackTrace();
			return false;			
		}
	}
	
	private boolean printText(String text) {
		
		byte[] line 	= Printer.printfont(text + "\n\n", FontDefine.FONT_32PX, FontDefine.Align_LEFT, (byte) 0x1A, 
						PocketPos.LANGUAGE_ENGLISH);
	
		
		//byte[] senddata = PocketPos.FramePack(PocketPos.FRAME_TOF_PRINT, line, 0, line.length);
      
		return sendData(line);		
	}		
public void printClicked(View v) {	
	if(GlobalApplication.is_device_connect){
	
	    Ticket ticket = new Ticket();
		long date  = System.currentTimeMillis()/1000;
		GlobalApplication application = ((GlobalApplication)getApplicationContext());
	    ticket.setDate(date);
	    ticket.setStatut(1);
	    ticket.setIs_sync(0);
	    ticket.setUser(application.getUser());
	    ticket.setBus(application.getBus());
	    ticket.setLigne(application.getLine());
	    ticket.setItineraire(application.getItineraire());
	    ticket.setGie(application.getOrganisation());
	    ticket.setType("SIMPLE");
	    ticket.setCard(null);
	     
		switch (v.getId()) {
		case R.id.goSection0:

	        ticket.setSection(0);
	        ticket.setAmount(100);
	      
	       
	    break;
	
	    case R.id.goSection1:
	    
	    ticket.setSection(1);
	    ticket.setAmount(150);
	
	   
	    break;
	
	case R.id.goSection2:
	  
	    ticket.setSection(2);
	    ticket.setAmount(175);
	
	  
	    break;
	
	case R.id.goSection3:
	   
	    ticket.setSection(3);
	    ticket.setAmount(200);
	    
	    break;
	    
	case R.id.goSection4:
	    
	    ticket.setSection(4);
	    ticket.setAmount(250);
	
	   
	    break;  
	
	 case R.id.goSection5:
	        
	        ticket.setSection(5);
	        ticket.setAmount(350);
	
	       
	        break;  

	        //benin
	 case R.id.goSection20:

	        ticket.setSection(20);
	        ticket.setAmount(7000);
	      
	       
	    break;
	
	    case R.id.goSection21:
	    
	    ticket.setSection(21);
	    ticket.setAmount(7500);
	
	   
	    break;
	
	case R.id.goSection22:
	  
	    ticket.setSection(22);
	    ticket.setAmount(8500);
	
	  
	    break;
	
	case R.id.goSection23:
	   
	    ticket.setSection(23);
	    ticket.setAmount(9500);
	    
	    break;
	    
	case R.id.goSection24:
	    
	    ticket.setSection(24);
	    ticket.setAmount(6000);
	
	   
	    break;  
	
	 case R.id.goSection25:
	        
	        ticket.setSection(25);
	        ticket.setAmount(3000);
	
	       
	        break;  

	
	
	}
		printTextAndQRCODE(application, ticket);
	}

	else  showToast("Imprimante non  trouver");
}

public void printTextAndQRCODE(GlobalApplication application, Ticket ticket)
{
	int qrCodeDimention = 500;
	QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(ticket.toString(), null,
	        Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);
	try {
		Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
 
		//String data = qrCodeEncoder.getContents();
		 Ticket _ti = get_unselledTicket(ticket);
		 
			if (_ti!=null)
			{	
				
				if(printText(_ti.toString(application.getSectionnement(),application.getAdverse())))					
					TicketUtils.updateticket(this, _ti);
			}
			else
			{
				//printText("srzerr");
				Toast.makeText(this, "Recharge vos tickets", 3000)
			    .show();	
				//this.setVisible(false);
				
			}
		
		 //mImageView.setImageBitmap(bitmap);
		 
	} catch (WriterException e) {
		e.printStackTrace();
	}




}

public void printRapport(View v)
{  if(GlobalApplication.is_device_connect)
		printText(TicketUtils.getAllRapport(getApplicationContext()));
   else  showToast("Imprimante non  trouver");
}
private Ticket get_unselledTicket(Ticket ticket)
{
    
	 return TicketUtils.get_ticket(this, ticket);
	
}
			
  private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
			    public void onReceive(Context context, Intent intent) {	    	
			        String action = intent.getAction();
			        
			        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
			        	final int state 	= intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
			        	
			        	if (state == BluetoothAdapter.STATE_ON) {
			        		showEnabled();
			        	} else if (state == BluetoothAdapter.STATE_OFF) {
				        	showDisabled();
			        	}
			        } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
			        	mDeviceList = new ArrayList<BluetoothDevice>();
						
						mProgressDlg.show();
			        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
			        	mProgressDlg.dismiss();
			        	
			        	//updateDeviceList();
			        } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
			        	BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				        
			        	mDeviceList.add(device);
			        	
			        	showToast("Imprimante trouver " + device.getName());
			        } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
			        	 final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
			        	 
			        	 if (state == BluetoothDevice.BOND_BONDED) {
			        		 showToast("Paired");
			        		 
			        		 connect();
			        	 }
			        }
			    }
			};
	
			
			
			
public void endservice()
 {
	//send a sync commande
	if(isInternetPresent && hasSimCardPresent){
    	    
			if(GlobalApplication.is_device_connect)
			{
				if (printText(TicketUtils.getAllRapport(getApplicationContext()))){
				
						try {
						syncTickets("END");
						GlobalApplication application = ((GlobalApplication)getApplicationContext());
						application.setOn_service(false);
						
						} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
					}
	
			}
		   else  showToast("Imprimante non  trouver");
			
		
     }
	 else {
		 if(GlobalApplication.etat)
			 openAlert("Erreur de saisie:", "Verifier que vous avez internet ");
		/* else{
			 if(GlobalApplication.is_device_connect){
				 if (printText(TicketUtils.getAllRapport(getApplicationContext()))){
					 TicketUtils.delete_all_rows(getApplicationContext());
					 GlobalApplication application = ((GlobalApplication)getApplicationContext());
					 application.setOn_service(false);
					
	                 
					 openAlert ("Alerte: ", "Fin de service effectuée avec succés");
				 }
				 
			 }
			 else
				  showToast("Imprimante non  trouver");
			 
		 }*/
		 
	 }
				  				  		  
}

public boolean imprimer(View v){
	String text="================================\n  ---- DAKAR DEM DIKK ---- \n "
			+ " =============================\n C'EST BON VOUS POUVEZ COMMENCER A VENDRE DES TICKETS \n"
			+ "================================\n ---- DAKAR DEM DIKK ---- \n ===============================\n";
	byte[] line 	= Printer.printfont(text + "\n\n", FontDefine.FONT_32PX, FontDefine.Align_LEFT, (byte) 0x1A, 
			PocketPos.LANGUAGE_ENGLISH);


//byte[] senddata = PocketPos.FramePack(PocketPos.FRAME_TOF_PRINT, line, 0, line.length);

return sendData(line);
}

public boolean imprimerAuto(){
	String text="================================\n  ---- DAKAR DEM DIKK ---- \n "
			+ " =============================\n C'EST BON VOUS POUVEZ COMMENCER A VENDRE DES TICKETS \n"
			+ "================================\n ---- DAKAR DEM DIKK ---- \n ===============================\n";
	byte[] line 	= Printer.printfont(text + "\n\n", FontDefine.FONT_32PX, FontDefine.Align_LEFT, (byte) 0x1A, 
			PocketPos.LANGUAGE_ENGLISH);


//byte[] senddata = PocketPos.FramePack(PocketPos.FRAME_TOF_PRINT, line, 0, line.length);

return sendData(line);
}

public void changeView(){
	actionBar.setSelectedNavigationItem(0);
	viewPager.setAdapter(mAdapter);
}

	
	public static class BusSettingsDialogFragmen extends DialogFragment{
	
	  EditText driverEditText;
	  
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
		 
		 if ((application.getChauffeur() !=null) && (application.getChauffeur().length()>0))
			 driverEditText.setText(application.getChauffeur());
		 
		 builder.setTitle("Nom du Chauffeur");
	     builder.setView(v)
	     // Add action buttons
	            .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int id) {
	                  
	                	
	                	String driver=driverEditText.getText().toString();
	                   
	                	
	                	if(driver.length()>0)
	                    {  
	                	GlobalApplication application = ((GlobalApplication)getActivity().getApplicationContext());
	                    
	                    application.setChauffeur(driver);
	                    
	                    actionBar.setSelectedNavigationItem(0);
	                    viewPager.setAdapter(mAdapter);

	                    }
	                    else 
	                    {
	                        Toast.makeText(getActivity(), "les champs sont obligatoires", Toast.LENGTH_LONG).show();
	                        
	                    }
	                
	                	
	                }
	            })
	            .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                	BusSettingsDialogFragmen.this.getDialog().cancel();
	                }
	            });      
	     return builder.create();
	 }
	}
	
	public static class SectionFragmen extends DialogFragment {
		 public static final String DATA = "items";
	     public static final String SELECTED = "selected";

	     @Override
	 	public Dialog onCreateDialog(Bundle savedInstanceState) {
	 		Bundle bundle = getArguments();
	 		final List<String> list = (List<String>)bundle.get(DATA);
	        final int position = bundle.getInt(SELECTED);
	        CharSequence[] lines =list.toArray(new CharSequence[list.size()]);
	 	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	 	    builder.setTitle("Selectionner la zone  ")
	 	           .setSingleChoiceItems(lines, position ,new DialogInterface.OnClickListener() {
	 	        	   	@Override
	 	        	   	public void onClick(DialogInterface dialog, int which) {
	 	        	  
	 	        	   		dialog.dismiss();
	 	        	   		int pos = position;
	 	        	   		Log.i("id  ", ""+which);
	 	        	   		if (which!=-1) pos=which;
	 	        	   		Log.i("section selectionner ", list.get(pos));
	 	        	   		GlobalApplication application = ((GlobalApplication)getActivity().getApplicationContext());
		 	    	        application.setSectionnement(list.get(pos));
		 	    	       actionBar.setSelectedNavigationItem(0);
		                    viewPager.setAdapter(mAdapter);
		 	    	       if(getActivity().getClass() != MainActivity.class)
	 	            	   { 
		 	    	    	 Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class); 
	 	          	  
	 	          		    startActivity(intent);
	 	          		   //getActivity().finish();
	 	          		   }
	 	        	   }
	 	           	})
	 	           .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	 	               @Override
	 	               public void onClick(DialogInterface dialog, int id) {
	 	            	  
	 	            	   if(getActivity().getClass() != MainActivity.class)
	 	            	   { Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class); 
	 	          	  
	 	          		   startActivity(intent);
	 	          		
	 	          		   }
	 	            	  actionBar.setSelectedNavigationItem(0);
		                    viewPager.setAdapter(mAdapter);
		                
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
	
	public static class ItineraireDialogFragmen   extends DialogFragment {
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
		 	    	       actionBar.setSelectedNavigationItem(0);
		                    viewPager.setAdapter(mAdapter);
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
	 	            	  actionBar.setSelectedNavigationItem(0);
		                  viewPager.setAdapter(mAdapter);
		                
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


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		if(GlobalApplication.is_device_connect==true){
			new AlertDialog.Builder(this)
			.setTitle("Alerte")
			.setMessage("Veuillez déconnecter l'imprimante avant déconnexion!")
			.setNeutralButton("Close", new DialogInterface.OnClickListener() { 
				public void onClick(DialogInterface dlg, int sumthin) {
					
				}
			})
			.show();
			
		}
		else{
			super.onBackPressed();
			
		}
			
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_HOME){
			if(GlobalApplication.is_device_connect==true){
				new AlertDialog.Builder(this)
				.setTitle("Alerte")
				.setMessage("Veuillez déconnecter l'imprimante avant déconnexion!")
				.setNeutralButton("Close", new DialogInterface.OnClickListener() { 
					public void onClick(DialogInterface dlg, int sumthin) {
						
					}
				})
				.show();
				
			}
		}
		return super.onKeyDown(keyCode, event);
		
	}
	

	private void chargementTicket(){
		 mProgressDialog= ProgressDialog.show(this,"SVP veuillez attendre...","Chargement de tickets en cours", true);
		 
		 new Thread((new Runnable() {
			 SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRENCH);
			 String newtime = sdfDateTime.format(new Date(System.currentTimeMillis()));
				@Override
				public void run() {
					Message msg = null;
					String progressBarData = "Chargement de tickets en cours";

					// populates the message
					msg = mHandler.obtainMessage(MSG_IND, (Object) progressBarData);

					// sends the message to our handler
					mHandler.sendMessage(msg);
					
					List<Ticket> _tickets = new ArrayList<Ticket>();
		      	      String a="";
		      	      for (int i = 1; i < MAX_TICKETS_TO_LOAD+1; i++) {
		      	    	  if(i<10)
		      	    		  a="00"+i;
		      	    	  if(i<100 && i>9)
		      	    		  a="0"+i;
		      	    	  if(i>99)
		      	    		  a=""+i;
		      	        Ticket ticketoff = offToTicket(newtime, a);
		      	        _tickets.add(ticketoff);
		      	      }
		      	      
		      	    TicketUtils.batch_insert_tickets(getApplicationContext(), _tickets);
		      	    
		      	  msg = mHandler.obtainMessage(MSG_CNF,
							"Parsing and computing ended successfully !");
					// sends the message to our handler
					mHandler.sendMessage(msg);
		      	    
				}
			})).start();
     
	      	   // Toast.makeText(getApplicationContext(), "Chargement  de " +MAX_TICKETS_TO_LOAD+" tickets termine avec succes", 300)
	      	   // .show();
	}
	
	
	/*@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	*//**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 *//*
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};


	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	*//**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 *//*
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}

	private void logDetectedTechs(Tag pTag) {
		if (mTag != null) {
			for (String s : mTag.getTechList()) {
				Log.d(TAG, "Detected tech: " + s);
			}
		}
	}

	private void logTagInfo(Ndef pNdef) {
		if (pNdef != null) {
			int size = pNdef.getMaxSize(); // tag size
			boolean writable = pNdef.isWritable(); // is tag writable?
			String type = pNdef.getType(); // tag type
			Log.d(TAG, "Tag size: " + size + " type: " + type + " is writable?: " + writable);
			Log.d(TAG, "Tag can be made readonly: " + pNdef.canMakeReadOnly() + " is connected: " + pNdef.isConnected());
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.i(TAG, "MainActivity onNewIntent, action: " + intent.getAction());
		setIntent(intent);
		if (isNFCIntent(intent)) {
			mMode = WorkMode.MODE_READ;
		}
	}

	public void onPause() {
		super.onPause();
		mNfcAdapter.disableForegroundDispatch(this);
		Log.i(TAG, "MainActivity onPause");
	}

	public void onStop() {
		super.onStop();
		Log.i(TAG, "MainActivity onStop");
	}

	public void onRestart() {
		super.onRestart();
		Log.i(TAG, "MainActivity onRestart");
	}

	

	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "MainActivity onDestroy");
	}

	private boolean isNFCIntent(Intent pIntent) {
		String action = pIntent.getAction();
		return NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) 
				
				|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
				|| NfcAdapter.ACTION_TAG_DISCOVERED.equals(action);
	}

	private void handleNFCTag(Intent pNfcIntent) {
		
		if (!isNFCIntent(pNfcIntent)) {
			Log.w(TAG, "Non-NDEF action in intent - returning");
			return;
		} 
		else {
			if (mMode == WorkMode.MODE_READ) {
				mTag = pNfcIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			} 
			else {
				Tag newTag = pNfcIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				Log.i(TAG, "MainActivity newTag is null?: " + (newTag == null));

				if (newTag != null) {
					mTag = newTag;
				}
			}
			logDetectedTechs(mTag);

			if (mTag != null && mMode == WorkMode.MODE_READ) {
				 StringBuilder sb = new StringBuilder();
			       
			        byte[] id = mTag .getId();
			        sb.append("Tag ID (hex): ").append(getHex(id)).append("\n");
			        sb.append("ID (reversed): ").append(getReversed(id)).append("\n");
			        Log.w(TAG, "tag id info  "+sb.toString());
			       
				mMode = WorkMode.MODE_WRITE;
				Ndef ndefTag = Ndef.get(mTag);
				

				if (ndefTag != null) {
					logTagInfo(ndefTag);
					NdefMessage ndefMesg = ndefTag.getCachedNdefMessage();
					if (ndefMesg != null) {
						displayMessages(ndefMesg.getRecords());
					} else {
						Log.w(TAG, "No cached NDEF message");
					}
				} else {
					Log.w(TAG, "ndefTag is null");
					updateTextField("");
				}

			}
		}
	}

	public void onResume() {
		super.onResume();
		// mDialog.hide();
		Intent nfcIntent = getIntent();
		Log.i(TAG, "MainActivity onResume, action: " + nfcIntent.getAction());
		setIntent(new Intent()); // consume Intent
		handleNFCTag(nfcIntent);

		String[][] techList = {new String[]{NfcA.class.getName()}, new String[]{MifareClassic.class.getName()}};
		mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNdefFilters, techList);
	}

	private void displayMessages(NdefRecord[] ndefRecords) {
		String[] rets = new String[ndefRecords.length];
		int i = 0;

		for (NdefRecord ndr : ndefRecords) {
			
			rets[i++] = parsePayload(ndr);
			
			
		}
		if (rets.length > 0) {
			// decrypted  data  from card
			String decryptedData =  securityUtils.decrypteDataCard(rets[0],CRYPTED_KEY);
			
			updateTextField(decryptedData);
		}
		
		
	}
	 
	private void updateTextField(final String text) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Log.d(TAG, "lire carte et infos "+ text);
				if (mTag!=null && text !=null)
				{
					try {
						Log.d(TAG, "card data");
						JSONObject jsonTag = getTagToJSON(mTag);
						JSONObject ob_data = new JSONObject(text);
						Log.d(TAG, "card data"+  ob_data);
						ob_data.put("tag",jsonTag);
						
						try {
							GlobalApplication application = ((GlobalApplication)getApplicationContext());
							String _status =ob_data.getString("status");
							if (_status.equals("error"))
							{
							 
							 playSound(null);
							 plays = false;
							}
							else{
									boolean statut;
									Subscription subscription = new Subscription(ob_data.getJSONObject("data"));
									statut = isValidNFCCard( subscription, mTag, application.getLine());
									System.out.println("========etat carte:" + statut);
									if (statut==false)
									{
										System.out.println("========etat carte:" + statut);
										playSound(null);
										plays = false;
										
										
										
									}
									else
									{
										Ticket ticket = new Ticket();
										long date  = System.currentTimeMillis()/1000;
										
									    ticket.setDate(date);
									    ticket.setStatut(1);
									    ticket.setIs_sync(0);
									    ticket.setUser(application.getUser());
									    ticket.setBus(application.getBus());
									    ticket.setLigne(application.getLine());
									    ticket.setItineraire(application.getItineraire());
									    ticket.setGie(application.getOrganisation());
									    ticket.setType("CARD");
									    ticket.setCard(subscription.getCustomer().getReference());
									    //ticket.setSection(0);
								        //ticket.setAmount(0);
										printTextAndQRCODE(application, ticket);
									}
							
							}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						 playSound(null);
						 plays = false;
						
					}
				}
				
				else{
					System.out.println("tag non prise en compte" );
					playSound(null);
					plays = false;
					
				}
				//tv.setText(message);
			}
		});
	}

	private String parseWellKnownPayload(NdefRecord pRecord) throws UnsupportedEncodingException {
		Log.d(TAG, "parseWellKnownPayload start");
		String ret = "";
		byte[] type = pRecord.getType();
		byte[] payload = pRecord.getPayload();

		if (Arrays.equals(type, NdefRecord.RTD_TEXT)) {
			Log.d(TAG, "NdefRecord.RTD_TEXT detected");
			if (payload.length > 0) {
				String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
				int languageCodeLength = payload[0] & 0077;
				String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
				Log.d(TAG, "parseWellKnownPayload encoding: " + textEncoding + " languagecodelength: "
						+ languageCodeLength + " languageCode: " + languageCode);
				ret = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
				Log.d(TAG, "parseWellKnownPayload text: " + ret);
			}

			return ret;
		} else {// if (Arrays.equals(type, NdefRecord.RTD_URI)) {
			ret = parseUri(pRecord);

			return ret;
		}
	}

	private String parseUri(NdefRecord pNdefRecord) {
		String ret = "";
		if (isURLRecord(pNdefRecord)) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				Uri u = pNdefRecord.toUri();
				if (u != null) {
					Log.d(TAG, "parseWellKnownPayload - URI detected");
					ret = u.toString();
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(u);
					startActivity(i);
				}
			} else {
				logAndToast("Intelligent URI parsing unsupported - API level 16 required!");
			}
		}

		return ret;
	}

	private boolean isURLRecord(NdefRecord pNdefRecord) {
		short tnf = pNdefRecord.getTnf();
		byte[] type = pNdefRecord.getType();

		if (NdefRecord.TNF_ABSOLUTE_URI == tnf
				|| (NdefRecord.TNF_WELL_KNOWN == tnf && (Arrays.equals(NdefRecord.RTD_URI, type) || Arrays.equals(
						NdefRecord.RTD_SMART_POSTER, type)))) {
			return true;
		} else {
			return false;
		}
	}

	private String normalizeMimeType(String pType) {
		if (pType == null) {
			return null;
		} else {
			pType = pType.trim().toLowerCase(Locale.US);
			int semicolonIndex = pType.indexOf(';');
			if (semicolonIndex >= 0) {
				pType = pType.substring(0, semicolonIndex);
			}

			return pType;
		}
	}

	private String detectMimeType(short pTnf, byte[] pType) {
		String ret = "";

		switch (pTnf) {
			case NdefRecord.TNF_WELL_KNOWN :
				if (Arrays.equals(pType, NdefRecord.RTD_TEXT)) {
					ret = TEXT_PLAIN_MIME_TYPE;
				}
				break;
			case NdefRecord.TNF_MIME_MEDIA :
				String mimeType = new String(pType, Charset.forName("US_ASCII"));
				ret = normalizeMimeType(mimeType);
		}

		return ret;
	}

	private String parseMimeMediaPayload(NdefRecord pRecord) throws UnsupportedEncodingException {
		String ret = "";
		String mimeType = detectMimeType(pRecord.getTnf(), pRecord.getType());
		if (!TEXT_PLAIN_MIME_TYPE.equals(mimeType)) {
			Log.w(TAG, "parseMimeMediaPayload - unsupported mime type detected: " + mimeType);
			return "";
		}

		byte[] payload = pRecord.getPayload();
		Log.d(TAG, "parseMimeMediaPayload mimeType: " + mimeType + " payload length: " + payload.length);

		if (payload.length > 0) {
			ret = new String(payload);
			Log.d(TAG, "parseMimeMediaPayload Text: " + ret);
		}

		return ret;
	}
	private String getHex(byte[] bytes) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = bytes.length - 1; i >= 0; --i) {
	            int b = bytes[i] & 0xff;
	            if (b < 0x10)
	                sb.append('0');
	            sb.append(Integer.toHexString(b));
	            if (i > 0) {
	                sb.append(" ");
	            }
	        }
	        return sb.toString();
	    }
	 
	 

	 private long getReversed(byte[] bytes) {
	        long result = 0;
	        long factor = 1;
	        for (int i = bytes.length - 1; i >= 0; --i) {
	            long value = bytes[i] & 0xffl;
	            result += value * factor;
	            factor *= 256l;
	        }
	        return result;
	    }
	private String parsePayload(NdefRecord pRecord) {
		Log.d(TAG, "parsePayload start, TNF: " + pRecord.getTnf() + " type: " + pRecord.getType() +" id: "+ pRecord.getId());
		 String ret = "";
		 

		// TNF values supported by this application:
		switch (pRecord.getTnf()) {
			case NdefRecord.TNF_WELL_KNOWN :
				try {
					ret = parseWellKnownPayload(pRecord);
				} catch (UnsupportedEncodingException e) {
					Log.e(TAG, "parsePayload TNF_WELL_KNOWN - UnsupportedEncodingException");
					e.printStackTrace();
				}
				break;

			case NdefRecord.TNF_MIME_MEDIA :
				try {
					ret = parseMimeMediaPayload(pRecord);
				} catch (UnsupportedEncodingException e) {
					Log.e(TAG, "parsePayload TNF_MIME_MEDIA - UnsupportedEncodingException");
					e.printStackTrace();
				}
				break;

			case NdefRecord.TNF_ABSOLUTE_URI :
				ret = parseUri(pRecord);
				// Log.w(TAG, "Unsupported NdefRecord type detected: " +
				// NdefRecord.TNF_ABSOLUTE_URI);
				break;

			case NdefRecord.TNF_EXTERNAL_TYPE :
				ret = parseUri(pRecord);
				// Log.w(TAG, "Unsupported NdefRecord type detected: " +
				// NdefRecord.TNF_EXTERNAL_TYPE);
				break;
		}

		return ret;
	}

	
	

	private void logAndToast(final String pMessage) {
		Log.i(TAG, pMessage);
		toast(pMessage);
	}

	private void toast(final String pMessage) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(MainActivity.this.getApplicationContext(), pMessage, Toast.LENGTH_SHORT).show();
			}
		});
	}
    
    private JSONObject getTagToJSON(Tag tag) throws JSONException
    {
		JSONObject object = new JSONObject();
		object.put("id", getReversed(tag.getId()));
		object.put("hex", getHex(tag.getId()));
    	return object;
    	
    }
    
    private String getTagInfo(Tag tag) throws JSONException
    {
		String info ="=========info Tag =========\n";
		info = info + "Tag id: " + getReversed(tag.getId()) +"\n";
		info = info + "Tag hex_id: " + getHex(tag.getId());
		
    	return info;
    	
    }
    
   
    private NdefMessage getNdefTextMesssage(String message)
    {
		String cryptedMessage =securityUtils.crypteDataCard(message, CRYPTED_KEY);
		NdefMessage mCryptedNdefTextMesssage =new NdefMessage(new NdefRecord[]{new NdefRecord(
    			NdefRecord.TNF_MIME_MEDIA, TEXT_PLAIN_MIME_TYPE.getBytes(), new byte[0], cryptedMessage.getBytes())});
    	return mCryptedNdefTextMesssage;
    	
    	
    	
    }
   

	
	
	public void doPositiveClick() {
	    // Do stuff here.
	    Log.i("FragmentAlertDialog", "Positive click!");
		}

		public void doNegativeClick() {
	    // Do stuff here.
	    Log.i("FragmentAlertDialog", "Negative click!");
	   }

		public boolean isValidNFCCard(final Subscription subscription, final Tag tag, final String line) throws JSONException, ParseException, java.text.ParseException
		{
			boolean status = subscription.isSubcriptionIsValid(line);
			System.out.println("resultat : " + status);
			return status ;
			
		}
		

*/

}
