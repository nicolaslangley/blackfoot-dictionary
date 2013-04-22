package com.langley.blackfoot.dictionary;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

/*TO-DO
1 - refine layout and presentation
2 - Add fragments for drop-down menu
*/

public class MainActivity extends Activity {
	
	
	
	
	//Definition of EXTRA_WORD to send to TranslateWord.java
	public final static String EXTRA_WORD = "com.langley.blackfoot.dictionary.WORD";
	public static String inputLang;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Spinner for Drop-Down Navigation
        SpinnerAdapter adapter = ArrayAdapter.createFromResource(this, R.array.action_list,
  	          android.R.layout.simple_spinner_dropdown_item);
        
        // Set action bar to have navigation mode
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        
        // Navigation listener for selection
        OnNavigationListener navigationListener = new OnNavigationListener() {
        	 
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                Toast.makeText(getBaseContext(), "You selected : " + itemPosition  , Toast.LENGTH_SHORT).show();
                return false;
            }
        };
 
        // Set drop down and navigation listener action bar
        getActionBar().setListNavigationCallbacks(adapter, navigationListener);
        
        
    }

    //This is untouched from the default
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void onToggleClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();
        
        if (on) {
            inputLang = "Blackfoot";
        } else {
            inputLang = "English";
        }
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
