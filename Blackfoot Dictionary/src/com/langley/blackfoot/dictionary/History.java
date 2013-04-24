package com.langley.blackfoot.dictionary;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class History extends Activity {
	
	private Activity current = this;
	private boolean onStart = true;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        
        // Spinner for Drop-Down Navigation
        SpinnerAdapter actionBarAdapter = ArrayAdapter.createFromResource(this, R.array.actionbar_list_main,
  	          android.R.layout.simple_spinner_dropdown_item);
        
        // Set action bar to have navigation mode
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getActionBar().setSelectedNavigationItem(1);

        
        // Navigation listener for selection
        OnNavigationListener navigationListener = new OnNavigationListener() { 	 
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            	if (onStart == true) {
            		onStart = false;
            		return false;
            	}
            	if (itemPosition == 0) {
            		Intent intent = new Intent(current, MainActivity.class);
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
        
        TextView hw = (TextView) findViewById(R.id.historyView);
        hw.setText("History");
    }
    
    //This is untouched from the default
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	

}
