package com.langley.blackfoot.dictionary;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.*;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import java.util.regex.*;

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
        		
        		//Depending on whether input type is Blackfoot or English return different word
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
       
        // Close dictionaryDatabaseHelper object
        dictionary.close();

        // Display translated word
        setContentView(R.layout.translated);
        
        TextView tw = (TextView) findViewById(R.id.translateView);
        tw.setText(translatedWord);
        
        if (!translatedWord.equals("This word is not in our database")){
        	
            // Create IPA pronounciation string
            // Diphthong replacements
            String ipaWord = translatedWord.replaceAll("AI", "ə");
            ipaWord = ipaWord.replaceAll("AO", "ɑʷ");
            ipaWord = ipaWord.replaceAll("OI", "ɔɪ");
            
            // Long vowel replacements
            ipaWord = ipaWord.replaceAll("AA", "ɑː");
            ipaWord = ipaWord.replaceAll("II", "ɪː");
            ipaWord = ipaWord.replaceAll("OO", "ɘʊ");
            
            // Vowel replacements
            ipaWord = ipaWord.replaceAll("A", "ɑ");
            ipaWord = ipaWord.replaceAll("I", "ɪ");
            ipaWord = ipaWord.replaceAll("O", "ɘʊ");   
            
            // Long consonant replacements
            ipaWord = ipaWord.replaceAll("NN", "nː");
            ipaWord = ipaWord.replaceAll("MM", "mː");
            ipaWord = ipaWord.replaceAll("SS", "sː");
            
            // Consonant replacements
            ipaWord = ipaWord.replaceAll("N", "n");
            ipaWord = ipaWord.replaceAll("M", "m");        
            ipaWord = ipaWord.replaceAll("S", "s");
            ipaWord = ipaWord.replaceAll("'", "?");
            ipaWord = ipaWord.replaceAll("P", "b");
            ipaWord = ipaWord.replaceAll("T", "d");
            ipaWord = ipaWord.replaceAll("K", "g");
            
            // Display IPA pronounciation string
        	TextView ipaView = (TextView) findViewById(R.id.ipaView);
        	ipaView.setText(ipaWord);
        
        }
    }
	
}
