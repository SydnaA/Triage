package com.example.phaseiii;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class LoginPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }
    
    public void loginPhysician(View v) {
	Intent intent = new Intent(this, Login.class);
	intent.putExtra("type", "Physician");
	startActivity(intent);
    }
    
    public void loginNurse(View v) {
   	Intent intent = new Intent(this, Login.class);
   	intent.putExtra("type", "Nurse");
   	startActivity(intent);
       }

}
