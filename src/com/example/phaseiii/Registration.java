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
public class Registration extends Activity {
    private String type;
    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_registration);
	Intent intent = getIntent();
	type = (String) intent.getSerializableExtra("type");
	((TextView) findViewById(R.id.patient_name_field))
		.setText(type + " Sign Up Page!");
    }
    
    public void registerUser(View v) {
	try {
	    DataHandler dh=null;
		if(type.equals("Nurse")) {
		    dh=new DataHandler(this.getApplicationContext().getFilesDir(), DataHandler.NURSE_DATA);
		} else if(type.equals("Physician")) {
		    dh=new DataHandler(this.getApplicationContext().getFilesDir(), DataHandler.PHYSICIAN_DATA);
		}
		if(dh.isAvailable(((EditText) findViewById(R.id.username_field)).getText()+"")) {
		    if(((EditText) findViewById(R.id.password_field_one)).getText()!=null&&!((EditText) findViewById(R.id.password_field_one)).getText().equals(" ")&&((((EditText) findViewById(R.id.password_field_one)).getText()+"").equals(((EditText) findViewById(R.id.password_field_two)).getText()+""))) {
			Intent newIntent;
			if(type.equals("Nurse")) {
			    dh.createNewNurse(((EditText) findViewById(R.id.username_field)).getText()+"", ((EditText) findViewById(R.id.password_field_one)).getText()+"");
			    dh.saveUsersToFile(this.getApplicationContext().openFileOutput(DataHandler.NURSE_DATA, MODE_PRIVATE));
			    newIntent = new Intent(this, DisplayActivityNurse.class);
			    newIntent.putExtra("type", "Nurse");
			    startActivity(newIntent);
			    return;
			} else if(type.equals("Physician")) {
			    dh.createNewPhysician(((EditText) findViewById(R.id.username_field)).getText()+"", ((EditText) findViewById(R.id.password_field_one)).getText()+"");
			    dh.saveUsersToFile(this.getApplicationContext().openFileOutput(DataHandler.PHYSICIAN_DATA, MODE_PRIVATE));
			    newIntent = new Intent(this, DisplayActivityPhysician.class);
			    newIntent.putExtra("type", "Physician");
			    startActivity(newIntent);
			    return;
			}
			
		    } else {
			    new AlertDialog.Builder(this)
			    .setTitle("Error")
			    .setMessage(
				    "Password not the same or invalid password. Please try again or try using a different password.")
			    .setNeutralButton("Okay",
				    new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
						int which) {
					    dialog.cancel();
					}
				    }).show();
			    ((TextView) findViewById(R.id.password_field_one))
				.setHint("Your desired password here");
			    ((TextView) findViewById(R.id.password_field_two))
				.setHint("same password as above but once again :)");
			    
		    }
		} else {
		    new AlertDialog.Builder(this)
		    .setTitle("Error")
		    .setMessage(
			    "Username not available or invalid. Please try another.")
		    .setNeutralButton("Okay",
			    new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
					int which) {
				    dialog.cancel();
				}
			    }).show();
		}
		
	    
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
    
    
}
