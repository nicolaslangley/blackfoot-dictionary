package com.langley.blackfoot.dictionary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
//import android.support.v4.app.NavUtils;
import android.widget.ToggleButton;

/*TO-DO
1 - refine layout and presentation
2 - analyze translated string to give phonetic pronounciation??
3 - increase size of database
4 - find way to link database online so it doesn't have to be updated manually?

*/
public class MainActivity extends Activity {
	
	//Definition of EXTRA_WORD to send to TranslateWord.java
	public final static String EXTRA_WORD = "com.langley.blackfoot.dictionary.WORD";
	public static String inputLang;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
