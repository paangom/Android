package net.ticket.android.network.provider;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
public class TicketContract {
	
	  /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "net.ticket.android.network.provider.TicketProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Path component for "entry"-type resources..
     */
    private static final String PATH_TICKETS = "tickets";
    private static final String PATH_DEPENSES = "depenses";
    private static final String PATH_PARAMETRE = "parametre";
	
	private TicketContract() {
    }
	 public static class Ticket implements BaseColumns {
		 public static final String CONTENT_TYPE =
	                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.basicsyncadapter.tickets";
	        /**
	         * MIME type for individual entries.
	         */
	        public static final String CONTENT_ITEM_TYPE =
	                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.basicsyncadapter.ticket";
	        /**
	         * Fully qualified URI for "tickets resources.
	         */
	        public static final Uri CONTENT_URI =
	                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TICKETS).build();
	        public static final String TABLE_NAME = "ticket";
	        public static final String COLUMN_ID = "_id";
	        public static final String COLUMN_BUS = "bus";
	        public static final String COLUMN_GIE = "gie";
	        public static final String COLUMN_LIGNE = "ligne";
	        public static final String COLUMN_ITINERAIRE = "itineraire";
	  	  	public static final String COLUMN_SECTION = "section";
	  	  	public static final String COLUMN_USER = "user";
	  	  	public static final String COLUMN_REFERENCE = "reference";
	  	  	public static final String COLUMN_AMOUNT = "amount";
	  	  	public static final String COLUMN_STATUT = "statut";
	  	    public static final String COLUMN_SYNC = "sync";
	  	  	public static final String COLUMN_DATE = "date";
	  	  	
	  	    public static final String COLUMN_CARD = "card";
	  	  	public static final String COLUMN_TYPE = "type";
	  }
	 
	 public static class Depense implements BaseColumns{
		 public static final String CONTENT_TYPE =
	                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.basicsyncadapter.depenses";
		 public static final Uri CONTENT_URI =
	                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DEPENSES).build();
	        /**
	         * MIME type for individual entries.
	         */
	        public static final String CONTENT_ITEM_TYPE =
	                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.basicsyncadapter.depense";
		 
		  public static final String TABLE_NAME = "depense";
		  public static final String DEPENSE_ID = "_id";
		  public static final String DEPENSE_STATIONNEMENT = "stationnement";
		  public static final String DEPENSE_DIVERS = "divers";
		  public static final String DEPENSE_GAZOIL = "gazoil";
		  public static final String DEPENSE_SYNC = "sync";
		  public static final String DEPENSE_DATE = "date";
		  public static final String DEPENSE_LAVAGE = "lavage";
		  public static final String DEPENSE_GARDIENNAGE = "gardiennage";
		  public static final String DEPENSE_PRIME = "prime";
		  public static final String DEPENSE_REGULATEUR = "regulateur";
		  public static final String DEPENSE_RATION = "ration";
		  public static final String DEPENSE_DEPANNAGE = "depannage";
		  public static final String DEPENSE_REFERENCE = "reference";
	 }
	 
	 
	 
	 public static class Params implements BaseColumns{
		 public static final String CONTENT_TYPE =
	                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.basicsyncadapter.params";
		 public static final Uri CONTENT_URI =
	                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DEPENSES).build();
	        /**
	         * MIME type for individual entries.
	         */
	        public static final String CONTENT_ITEM_TYPE =
	                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.basicsyncadapter.param";
		 
		  public static final String TABLE_NAME = "params";
		  public static final String COLUMN_ID = "_id";
		  public static final String COLUMN_USERNAME = "username";
		  public static final String COLUMN_PASSWORD = "password";
		  public static final String COLUMN_MAX_TICKETS = "maxtickets";
		  public static final String COLUMN_SYNC_FREQUENCE = "syncfrequence";
	
	 }
	 
	 public static class Parametre implements BaseColumns{
		 
		 public static final String CONTENT_TYPE =
	                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.basicsyncadapter.parametre";
		 public static final Uri CONTENT_URI =
	                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PARAMETRE).build();
	        /**
	         * MIME type for individual entries.
	         */
	        public static final String CONTENT_ITEM_TYPE =
	        		ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.basicsyncadapter.parametre";
	        
	        public static final String TABLE_NAME = "parametre";
	        public static final String COLUMN_ID = "_id";
	        public static final String COLUMN_BUS = "bus";
	        public static final String COLUMN_GIE = "gie";
	        public static final String COLUMN_LIGNE = "ligne";
	        public static final String COLUMN_ITINERAIRE = "itineraire";
	  	  	public static final String COLUMN_SECTION = "section";
	  	  	public static final String COLUMN_USER = "user";
	  	    public static final String COLUMN_PASSWORD = "password";
	  	    public static final String COLUMN_TOKEN = "token";
	  	    public static final String COLUMN_FIRST = "first";
	  	    public static final String COLUMN_BACK = "back";
	 
	 }

}
