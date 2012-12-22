package com.langley.blackfoot.dictionary;

import java.io.*;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.*;



public class DictionaryDatabaseHelper extends SQLiteOpenHelper {	
	
	//Initialize Database path and name variables
	public static String DB_path = "/data/data/com.langley.blackfoot.dictionary/databases/";
	public static String DB_name = "DictionaryDatabase.db";
	
	//Define Database and Context objects
	private SQLiteDatabase dictionaryDatabase;
	private Context dictionaryContext;
	
	
	//Constructor for DictionaryDatabaseHelper
	public DictionaryDatabaseHelper(Context context) {
    	
    	super(context, DB_name, null, 1);
    	this.dictionaryContext = context;
    }	

    //Creates a empty database on the system and rewrites it with your own database.
    public void createDatabase() throws IOException {
    
    	//Check whether a database already exists
    	boolean dbExist = checkDatabase();

    	if(!dbExist) {
    		//Call getReadableDatabase to create a new empty database
    		this.getReadableDatabase();
    		
    		//Attempt to copy the stored database to this new empty one
    		try {
    			copyDatabase();	
    			
    		} catch (IOException e) {
    			
    			throw new Error("Error copying database");
    		}
        }

    }

    //Check whether a database exists and return a boolean
    private boolean checkDatabase() {

    	SQLiteDatabase checkDB = null;
   		
   		//Check if database exists and if it does store database in checkDB
   		try {
   			String myPath = DB_path + DB_name;
   			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

   		} catch(SQLiteException e) {
   			//Database doesn't exist so do nothing

   		}

   		//Check status of checkDB
   	 	if(checkDB == null){
   	 	 
    		return false;
    	}
 
    	return true;

   }

    //Copy database from assets folder to empty database
    private void copyDatabase() throws IOException{

    	//Set local database to input stream
    	InputStream myInput = dictionaryContext.getAssets().open(DB_name);

    	//Set path to empty database
    	String outFileName = DB_path + DB_name;

    	//Set empty database as output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);

    	//Transfer bytes from the input stream to the output stream
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}

    	//Close streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();

    }

    //Open database
    public void openDatabase() throws SQLException{

       	String myPath = DB_path + DB_name;
       	setDictionaryDatabase(SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY));

    }

    //Close method that closes dictionaryDatabase and super
    @Override
	public synchronized void close() {

    	//Close dictionary if it is null
    	if(getDictionaryDatabase() != null) {
    		getDictionaryDatabase().close();
    	}
    	
    	//Close super
   	    super.close();

	}

    //Override onCreate method using database
	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	//Override onUpgrade using database and old and new versions
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public SQLiteDatabase getDictionaryDatabase() {
		return dictionaryDatabase;
	}

	public void setDictionaryDatabase(SQLiteDatabase dictionaryDatabase) {
		this.dictionaryDatabase = dictionaryDatabase;
	}

}
	
