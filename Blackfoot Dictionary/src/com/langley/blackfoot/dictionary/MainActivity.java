package com.langley.blackfoot.dictionary;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

/*TO-DO
1 - refine layout and presentation
2 - Add fragments for drop-down menu
3 - fix action bar position for new activities
*/

public class MainActivity extends Activity implements OnItemSelectedListener {
	
	//Definition of EXTRA_WORD to send to TranslateWord.java
	public final static String EXTRA_WORD = "com.langley.blackfoot.dictionary.WORD";
	public static String inputLang;
	private Spinner inputLangSpinner;
	private Activity current = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Spinner for Drop-Down Navigation
        SpinnerAdapter actionBarAdapter = ArrayAdapter.createFromResource(this, R.array.actionbar_list_main,
  	          android.R.layout.simple_spinner_dropdown_item);
        
        // Set action bar to have navigation mode
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getActionBar().setSelectedNavigationItem(0);

        
        // Navigation listener for selection
        OnNavigationListener navigationListener = new OnNavigationListener() { 	 
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            	if (itemPosition == 1) {
            		Intent intent = new Intent(current, History.class);
            		startActivity(intent);
            	} else if (itemPosition == 2) {
            		Intent intent = new Intent(current, About.class);
            		startActivity(intent);
            	}
                return false;
            }
        };
 
        // Set drop down and navigation listener action bar
        getActionBar().setListNavigationCallbacks(actionBarAdapter, navigationListener);
        
        // Set up spinner for input language choice
        inputLangSpinner = (Spinner) findViewById(R.id.inputLangSpinner);
        inputLangSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> inputLangAdapter = ArrayAdapter.createFromResource(this,R.array.lang_list, android.R.layout.simple_spinner_item);
        inputLangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputLangSpinner.setAdapter(inputLangAdapter);
        
    }

    //This is untouched from the default
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    // Handle spinner item selection
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    	inputLang = inputLangSpinner.getSelectedItem().toString();
    }
    
    public void onNothingSelected (AdapterView<?> parent) {
    	// Do nothing
    }
    
    //Creates new intent and gets text from input to sent to TranslateWord.java
    public void translateWord(View view) {
    	Intent intent = new Intent(this, TranslateWord.class);
    	EditText editText = (EditText) findViewById(R.id.query);
    	String[] toTranslate = {editText.getText().toString(), inputLang};
    	intent.putExtra(EXTRA_WORD, toTranslate);
    	startActivity(intent);	
    }   
}
