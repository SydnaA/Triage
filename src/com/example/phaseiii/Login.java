/**
 * 
 */
package com.example.phaseiii;

import java.io.IOException;
import java.text.ParseException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.phaseiii.files.DataHandler;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 * 
 */
public class Login extends Activity {
    private String type;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login_page);
	Intent intent = getIntent();
	type = (String) intent.getSerializableExtra("type");
	((TextView) findViewById(R.id.landscape_patient_name_field))
		.setText(type + " Login Page!");
    }

    public void loginUser(View v) {
	try {
	    DataHandler dh;
	    Intent newIntent;
	    if(type.equals("Nurse")) {
		dh=new DataHandler(this.getApplicationContext().getFilesDir(), DataHandler.NURSE_DATA);
		if(dh.login(((EditText) findViewById(R.id.username_field)).getText()+"", ((EditText) findViewById(R.id.password_field)).getText()+"")) {
		   newIntent=new Intent(this, DisplayActivityNurse.class);
		   startActivity(newIntent);
		   return;
		}
	    } else if(type.equals("Physician")) {
		dh=new DataHandler(this.getApplicationContext().getFilesDir(), DataHandler.PHYSICIAN_DATA);
		if(dh.login(((EditText) findViewById(R.id.username_field)).getText()+"", ((EditText) findViewById(R.id.password_field)).getText()+"")) {
			   newIntent=new Intent(this, DisplayActivityPhysician.class);
			   startActivity(newIntent);
			   return;
		}
	    }
	    new AlertDialog.Builder(this)
	    .setTitle("Login Error")
	    .setMessage(
		    "Username or password incorrect. Please try again.")
	    .setNeutralButton("Yes!",
		    new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
				int which) {
			    dialog.cancel();
			}
		    }).show();
	    ((EditText) findViewById(R.id.username_field)).setHint("Please try again");
	    ((EditText) findViewById(R.id.password_field)).setHint("and here too");

	} catch (NumberFormatException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public void signUp(View v) {
	Intent newIntent = new Intent(this, Registration.class);
	newIntent.putExtra("type", type);
	startActivity(newIntent);
    }
}
