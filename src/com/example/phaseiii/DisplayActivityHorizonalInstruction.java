/**
 * 
 */
package com.example.phaseiii;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 *
 */
public class DisplayActivityHorizonalInstruction extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
   	super.onCreate(savedInstanceState);
   	setContentView(R.layout.activity_display_instructions);
    }
    
    public void onConfigurationChanged(Configuration newConfig) {
	super.onConfigurationChanged(newConfig);
	if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
	    Intent intent=new Intent(this, DisplayActivityNurse.class);
	    startActivity(intent);
	    overridePendingTransition(R.anim.rotate_out,R.anim.rotate_in);
	}
    }

}
