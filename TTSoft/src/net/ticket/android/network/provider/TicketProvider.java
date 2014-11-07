package net.ticket.android.network.provider;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class TicketProvider extends ContentProvider{
	
	
	 TicketDatabase mDatabaseHelper;
	/**
     * Content authority for this provider.
     */
    private static final String AUTHORITY = TicketContract.CONTENT_AUTHORITY;
    
    private static final int TICKETS = 1;
    private static final int TICKET_ID = 2;
    private static final int DEPENSES = 3;
    private static final int DEPENSE_ID = 4;
    private static final int PARAMS = 5;
    private static final int PARAM_ID = 6;
    private static final int PARAMETRE = 7;
    private static final int PARAMETRE_ID = 8;
    
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    
   
    static {
    	sUriMatcher.addURI(AUTHORITY, "tickets", TICKETS);
    	sUriMatcher.addURI(AUTHORITY, "tickets/#", TICKET_ID);
    	sUriMatcher.addURI(AUTHORITY, "depenses", DEPENSES);
    	sUriMatcher.addURI(AUTHORITY, "depenses/#", DEPENSE_ID);
    	sUriMatcher.addURI(AUTHORITY, "params", PARAMS);
    	sUriMatcher.addURI(AUTHORITY, "params/#", PARAM_ID);
    	sUriMatcher.addURI(AUTHORITY, "parametre", PARAMETRE);
    	sUriMatcher.addURI(AUTHORITY, "parametre/#", PARAMETRE_ID);
    }



	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SelectionBuilder builder = new SelectionBuilder();
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
        case DEPENSES:
            count = builder.table(TicketContract.Depense.TABLE_NAME)
                    .where(selection, selectionArgs)
                    .delete(db);
            break;
            case TICKETS:
                count = builder.table(TicketContract.Ticket.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .delete(db);
                break;
                
            case PARAMS:
                count = builder.table(TicketContract.Params.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .delete(db);
                break;
            
            case PARAMETRE:
                count = builder.table(TicketContract.Parametre.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .delete(db);
                break;
            case DEPENSE_ID:
                String depense_id = uri.getLastPathSegment();
                count = builder.table(TicketContract.Depense.TABLE_NAME)
                       .where(TicketContract.Depense._ID + "=?", depense_id)
                       .where(selection, selectionArgs)
                       .delete(db);
                break;
            case TICKET_ID:
            	Log.i("tag", "Found " + TicketContract.Ticket._ID+" local entries. Computing merge solution...");
                String id = uri.getLastPathSegment();
                count = builder.table(TicketContract.Ticket.TABLE_NAME)
                       .where(TicketContract.Ticket._ID + "=?", id)
                       .where(selection, selectionArgs)
                       .delete(db);

                break;
                
            case PARAM_ID:
            	Log.i("tag", "Found " + TicketContract.Ticket._ID+" local entries. Computing merge solution...");
                String param_id = uri.getLastPathSegment();
                count = builder.table(TicketContract.Params.TABLE_NAME)
                       .where(TicketContract.Ticket._ID + "=?", param_id)
                       .where(selection, selectionArgs)
                       .delete(db);

                break;
            case PARAMETRE_ID:
            	Log.i("tag", "Found " + TicketContract.Parametre._ID+" local entries. Computing merge solution...");
                String parametre_id = uri.getLastPathSegment();
                count = builder.table(TicketContract.Params.TABLE_NAME)
                       .where(TicketContract.Ticket._ID + "=?", parametre_id)
                       .where(selection, selectionArgs)
                       .delete(db);

                break;

            
            
            
            
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Send broadcast to registered ContentObservers, to refresh UI.
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return count;
	
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		final int match = sUriMatcher.match(uri);
		switch (match) {
        case TICKETS:
            return TicketContract.Ticket.CONTENT_TYPE;
        case TICKET_ID:
            return TicketContract.Ticket.CONTENT_ITEM_TYPE;
        case DEPENSES:
            return TicketContract.Depense.CONTENT_TYPE;
        case DEPENSE_ID:
            return TicketContract.Depense.CONTENT_ITEM_TYPE;
            
        case PARAMS:
            return TicketContract.Params.CONTENT_TYPE;
        case PARAM_ID:
            return TicketContract.Params.CONTENT_ITEM_TYPE;
        case PARAMETRE:
            return TicketContract.Parametre.CONTENT_TYPE;
        case PARAMETRE_ID:
            return TicketContract.Parametre.CONTENT_ITEM_TYPE;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        assert db != null;
        final int match = sUriMatcher.match(uri);
        Uri result;
        switch (match) {
            case TICKETS:
                long id = db.insertOrThrow(TicketContract.Ticket.TABLE_NAME, null, values);
                result = Uri.parse(TicketContract.Ticket.CONTENT_URI + "/" + id);
                break;
            case DEPENSES:
            	long depense_id = db.insertOrThrow(TicketContract.Depense.TABLE_NAME, null, values);
                result = Uri.parse(TicketContract.Depense.CONTENT_URI + "/" + depense_id);
                break;
            case PARAMS:
            	long param_id = db.insertOrThrow(TicketContract.Params.TABLE_NAME, null, values);
                result = Uri.parse(TicketContract.Params.CONTENT_URI + "/" + param_id);
                break;
            case PARAMETRE:
            	long parametre_id = db.insertOrThrow(TicketContract.Parametre.TABLE_NAME, null, values);
                result = Uri.parse(TicketContract.Params.CONTENT_URI + "/" + parametre_id);
                break;
            case TICKET_ID:
                throw new UnsupportedOperationException("Insert not supported on URI: " + uri);
            case DEPENSE_ID:
                throw new UnsupportedOperationException("Insert not supported on URI: " + uri);
            case PARAM_ID:
                throw new UnsupportedOperationException("Insert not supported on URI: " + uri);
            case PARAMETRE_ID:
                throw new UnsupportedOperationException("Insert not supported on URI: " + uri);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Send broadcast to registered ContentObservers, to refresh UI.
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return result;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		
		mDatabaseHelper = new TicketDatabase(getContext());
        return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        SelectionBuilder builder = new SelectionBuilder();
       
        int uriMatch = sUriMatcher.match(uri);
        switch (uriMatch) {
            case DEPENSE_ID:
            	// Return a single entry, by ID.
                String id = uri.getLastPathSegment();
                builder.where(TicketContract.Depense._ID + "=?", id);
            	
            case TICKET_ID:
                // Return a single entry, by ID.
                String ticket_id = uri.getLastPathSegment();
                builder.where(TicketContract.Ticket._ID + "=?", ticket_id);
            case PARAM_ID:
                // Return a single entry, by ID.
                String param_id = uri.getLastPathSegment();
                builder.where(TicketContract.Params._ID + "=?", param_id);
            case PARAMETRE_ID:
                // Return a single entry, by ID.
                String parametre_id = uri.getLastPathSegment();
                builder.where(TicketContract.Parametre._ID + "=?", parametre_id);
            case DEPENSES:
                // Return all known entries.
                builder.table(TicketContract.Depense.TABLE_NAME)
                       .where(selection, selectionArgs);
                Cursor c = builder.query(db, projection, sortOrder);
                // Note: Notification URI must be manually set here for loaders to correctly
                // register ContentObservers.
                Context ctx = getContext();
                assert ctx != null;
                c.setNotificationUri(ctx.getContentResolver(), uri);
                return c;
                
            case PARAMS:
                // Return all known entries.
                builder.table(TicketContract.Params.TABLE_NAME)
                       .where(selection, selectionArgs);
                Cursor c_param = builder.query(db, projection, sortOrder);
                // Note: Notification URI must be manually set here for loaders to correctly
                // register ContentObservers.
                Context ctx_param = getContext();
                assert ctx_param != null;
                c_param.setNotificationUri(ctx_param.getContentResolver(), uri);
                return c_param;
                
            case PARAMETRE:
                // Return all known entries.
                builder.table(TicketContract.Parametre.TABLE_NAME)
                       .where(selection, selectionArgs);
                Cursor c_parametre = builder.query(db, projection, sortOrder);
                // Note: Notification URI must be manually set here for loaders to correctly
                // register ContentObservers.
                Context ctx_parametre = getContext();
                assert ctx_parametre != null;
                c_parametre.setNotificationUri(ctx_parametre.getContentResolver(), uri);
                return c_parametre;
                
            case TICKETS:
                // Return all known entries.
                builder.table(TicketContract.Ticket.TABLE_NAME)
                       .where(selection, selectionArgs);
                
                Cursor ticket_c = builder.query(db, projection, sortOrder);
                // Note: Notification URI must be manually set here for loaders to correctly
                // register ContentObservers.
                //SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                //queryBuilder.setTables(TicketContract.Ticket.TABLE_NAME);
                //queryBuilder.setProjectionMap(TicketMap);
                //Cursor ticket_c = queryBuilder.query(db, projection, selection,selectionArgs, null, null, sortOrder);



                Context ticket_ctx = getContext();
                assert ticket_ctx != null;
                ticket_c.setNotificationUri(ticket_ctx.getContentResolver(), uri);
                return ticket_c;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
		
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		SelectionBuilder builder = new SelectionBuilder();
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
        case TICKETS:
            count = builder.table(TicketContract.Ticket.TABLE_NAME)
                    .where(selection, selectionArgs)
                    .update(db, values);
            break;
        case TICKET_ID:
            String id = uri.getLastPathSegment();
            count = builder.table(TicketContract.Ticket.TABLE_NAME)
                    .where(TicketContract.Ticket._ID + "=?", id)
                    .where(selection, selectionArgs)
                    .update(db, values);
            break;
        case DEPENSES:
            count = builder.table(TicketContract.Depense.TABLE_NAME)
                    .where(selection, selectionArgs)
                    .update(db, values);
            break;
        case PARAMS:
            count = builder.table(TicketContract.Params.TABLE_NAME)
                    .where(selection, selectionArgs)
                    .update(db, values);
            break;
        case PARAM_ID:
            String param_id = uri.getLastPathSegment();
            count = builder.table(TicketContract.Params.TABLE_NAME)
                    .where(TicketContract.Params._ID + "=?", param_id)
                    .where(selection, selectionArgs)
                    .update(db, values);
        case DEPENSE_ID:
            String depense_id = uri.getLastPathSegment();
            count = builder.table(TicketContract.Depense.TABLE_NAME)
                    .where(TicketContract.Ticket._ID + "=?", depense_id)
                    .where(selection, selectionArgs)
                    .update(db, values);
            break;
        case PARAMETRE:
            count = builder.table(TicketContract.Parametre.TABLE_NAME)
                    .where(selection, selectionArgs)
                    .update(db, values);
            break;
        case PARAMETRE_ID:
            String idp = uri.getLastPathSegment();
            count = builder.table(TicketContract.Parametre.TABLE_NAME)
                    .where(TicketContract.Ticket._ID + "=?", idp)
                    .where(selection, selectionArgs)
                    .update(db, values);
            break;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
	    }
	    Context ctx = getContext();
	    assert ctx != null;
	    ctx.getContentResolver().notifyChange(uri, null, false);
	    return count;
		}
	
	
	
	 /**
     * SQLite backend for @{link FeedProvider}.
     *
     * Provides access to an disk-backed, SQLite datastore which is utilized by FeedProvider. This
     * database should never be accessed by other parts of the application directly.
     */
    static class TicketDatabase extends SQLiteOpenHelper {
        /** Schema version. */
        public static final int DATABASE_VERSION = 1;
        /** Filename for SQLite file. */
        public static final String DATABASE_NAME = "ddktickets.db";

        private static final String TYPE_TEXT = " TEXT";
        private static final String TYPE_INTEGER = " INTEGER";
        private static final String COMMA_SEP = ",";
        /** SQL statement to create "ticket" table. */
        private static final String SQL_CREATE_TICKETS =
                "CREATE TABLE " + TicketContract.Ticket.TABLE_NAME + " (" +
                		TicketContract.Ticket.COLUMN_ID + " INTEGER PRIMARY KEY autoincrement , " +
                		TicketContract.Ticket.COLUMN_BUS + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Ticket.COLUMN_SECTION    + TYPE_INTEGER+ COMMA_SEP +
                        TicketContract.Ticket.COLUMN_ITINERAIRE + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Ticket.COLUMN_GIE + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Ticket.COLUMN_TYPE + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Ticket.COLUMN_CARD + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Ticket.COLUMN_LIGNE    + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Ticket.COLUMN_USER + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Ticket.COLUMN_REFERENCE + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Ticket.COLUMN_DATE    + TYPE_INTEGER + COMMA_SEP +
                        TicketContract.Ticket.COLUMN_SYNC + TYPE_INTEGER + COMMA_SEP +
                        TicketContract.Ticket.COLUMN_STATUT + TYPE_INTEGER + COMMA_SEP +
                        TicketContract.Ticket.COLUMN_AMOUNT + TYPE_INTEGER + ")";
        
        /** SQL statement to create "depense" table. */
        private static final String SQL_CREATE_DEPENSES =
                "CREATE TABLE " + TicketContract.Depense.TABLE_NAME + " (" +
                		TicketContract.Depense.DEPENSE_ID + " INTEGER PRIMARY KEY autoincrement , " +
                        TicketContract.Depense.DEPENSE_DIVERS   + TYPE_INTEGER+ COMMA_SEP +
                        TicketContract.Depense.DEPENSE_GAZOIL + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Depense.DEPENSE_STATIONNEMENT + TYPE_INTEGER  + COMMA_SEP + 
                        TicketContract.Depense.DEPENSE_DATE + TYPE_INTEGER  + COMMA_SEP + 
                        TicketContract.Depense.DEPENSE_SYNC + TYPE_INTEGER  + COMMA_SEP + 
                        TicketContract.Depense.DEPENSE_REFERENCE + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Depense.DEPENSE_LAVAGE + TYPE_INTEGER  + COMMA_SEP +  
                        TicketContract.Depense.DEPENSE_RATION + TYPE_INTEGER  + COMMA_SEP + 
                        TicketContract.Depense.DEPENSE_REGULATEUR + TYPE_INTEGER  + COMMA_SEP + 
                        TicketContract.Depense.DEPENSE_GARDIENNAGE + TYPE_INTEGER  + COMMA_SEP + 
                        TicketContract.Depense.DEPENSE_PRIME + TYPE_INTEGER  + COMMA_SEP + 
                        TicketContract.Depense.DEPENSE_DEPANNAGE + TYPE_INTEGER + ")";
       
        
        /** SQL statement to create "params" table. */
        private static final String SQL_CREATE_PARAMS =
                "CREATE TABLE " + TicketContract.Params.TABLE_NAME + " (" +
                		TicketContract.Params.COLUMN_ID + " INTEGER PRIMARY KEY autoincrement , " +
                        TicketContract.Params.COLUMN_MAX_TICKETS   + TYPE_INTEGER+ COMMA_SEP +
                        TicketContract.Params.COLUMN_PASSWORD + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Params.COLUMN_SYNC_FREQUENCE + TYPE_INTEGER  + COMMA_SEP + 
                        TicketContract.Params.COLUMN_USERNAME + TYPE_TEXT + " NOT NULL  UNIQUE )";
        
        /** SQL statement to create "parametre" table. */
        private static final String SQL_CREATE_PARAMETRE =
                "CREATE TABLE " + TicketContract.Parametre.TABLE_NAME + " (" +
                		TicketContract.Parametre.COLUMN_ID + " INTEGER PRIMARY KEY autoincrement , " +
                		TicketContract.Parametre.COLUMN_BUS + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Parametre.COLUMN_SECTION    + TYPE_INTEGER+ COMMA_SEP +
                        TicketContract.Parametre.COLUMN_ITINERAIRE + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Parametre.COLUMN_GIE + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Parametre.COLUMN_LIGNE    + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Parametre.COLUMN_USER + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Parametre.COLUMN_PASSWORD + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Parametre.COLUMN_FIRST + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Parametre.COLUMN_BACK + TYPE_TEXT + COMMA_SEP +
                        TicketContract.Parametre.COLUMN_TOKEN  + TYPE_TEXT +
                         ")";
        
        /** SQL statement to drop "ticket" table. */
        private static final String SQL_DELETE_TICKETS =
                "DROP TABLE IF EXISTS " + TicketContract.Ticket.TABLE_NAME;
        private static final String SQL_DELETE_DEPENSES =
                "DROP TABLE IF EXISTS " + TicketContract.Depense.TABLE_NAME;
        
        private static final String SQL_DELETE_PARAMS =
                "DROP TABLE IF EXISTS " + TicketContract.Params.TABLE_NAME;
        
        private static final String SQL_DELETE_PARAMETRE =
                "DROP TABLE IF EXISTS " + TicketContract.Parametre.TABLE_NAME;

        public TicketDatabase (Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TICKETS);
            db.execSQL(SQL_CREATE_DEPENSES);
            db.execSQL(SQL_CREATE_PARAMS);
            db.execSQL(SQL_CREATE_PARAMETRE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_DEPENSES);
            db.execSQL(SQL_DELETE_TICKETS);
            db.execSQL(SQL_DELETE_PARAMS);
            db.execSQL(SQL_DELETE_PARAMETRE);
            onCreate(db);
        }
    }

}
