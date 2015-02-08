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

import com.example.phaseiii.files.DataHandler;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 * 
 */
public class DisplayActivityPhysician extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_display_physician);
    }

    public void searchPatient(View v) {
	try {
	    DataHandler dh = new DataHandler(this.getApplicationContext()
		    .getFilesDir(), DataHandler.PATIENT_DATA);
	    if (dh.findPatientByCardNumber(((EditText) this
		    .findViewById(R.id.healthcard_field)).getText() + "") != null) {
		Intent newIntent = new Intent(this, PatientInfo.class);
		newIntent
			.putExtra(
				"name",
				dh.findPatientByCardNumber(
					((EditText) this
						.findViewById(R.id.healthcard_field))
						.getText()
						+ "").getData().getName());
		newIntent.putExtra("type", "Physician");
		startActivity(newIntent);
		return;
	    }
	    new AlertDialog.Builder(this)
		    .setTitle("Health Card Number Error")
		    .setMessage(
			    "Health Card Number does not exist or invalid. Please try again.")
		    .setNeutralButton("Yes!",
			    new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
					int which) {
				    dialog.cancel();
				}
			    }).show();
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
