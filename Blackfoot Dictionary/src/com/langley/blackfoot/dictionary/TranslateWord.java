package com.langley.blackfoot.dictionary;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.*;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class TranslateWord extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Get intent
        Intent intent = getIntent();
        String[] word = intent.getStringArrayExtra(MainActivity.EXTRA_WORD);
        String translatedWord;
        
        //Check that database is available and if it is not, create it
        DictionaryDatabaseHelper dictionary = new DictionaryDatabaseHelper (getApplicationContext());
        
        try {
			dictionary.createDatabase();
		} catch (IOException e) {
			throw new Error("Error creating database");
		}
        
        //Open database
        try {
        	dictionary.openDatabase();
        } catch (SQLException sqle) {
        	throw sqle;
        }
       
        //Check whether given word is in the database and store it in translatedWord
        try {
        	Cursor cursor = dictionary.getDictionaryDatabase().query("Words", null, "english= '" + word[0].toLowerCase().trim() + "' or blackfoot= '" + word[0].toUpperCase().trim() + "' ", null, null, null, null);
        	cursor.moveToFirst();
        	if (cursor.getCount() != 0) {
        		
        		//Depending on whether input type is blackfoot or english return different word
        		if (word[1] == null || word[1].equals("English")) {
        			translatedWord = cursor.getString(2);
        		} else {
        			translatedWord = cursor.getString(1);
        		}
        	} else {
        		translatedWord = "This word is not in our database";
        	}
            cursor.close();
        } catch (SQLiteException sqle) {
        	translatedWord = "This word is not in our database";
        }
       
        //Close dictionaryDatabaseHelper object
        dictionary.close();
        
        //Display translated word
        //Display translated word
        setContentView(R.layout.translated);
        
        TextView tw = (TextView) findViewById(R.id.translateView);
        tw.setText(translatedWord);
        
        
        WebView engine = (WebView) findViewById(R.id.webLink);  
        engine.loadUrl("http://www.native-languages.org/blackfoot_guide.htm");

    }
	
}
