/**
 * 
 */
package com.example.phaseiii;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.phaseiii.files.DataHandler;
import com.example.phaseiii.patient.BloodPressure;
import com.example.phaseiii.patient.Data;
import com.example.phaseiii.patient.Patient;
import com.example.phaseiii.patient.Prescription;
import com.example.phaseiii.patient.VitalSigns;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 *
 */
public class NewPatientActivity extends Activity {
    private Data personalData;
    private Map<Date, VitalSigns> vitalSigns; // vital signs of the new patient
    private Map<Date, String> symptoms; // symptoms of the new patient

    protected void onCreate(Bundle savedInstanceState) {
   	super.onCreate(savedInstanceState);
   	setContentView(R.layout.activity_new_patient);
	symptoms = new HashMap<Date, String>();
	vitalSigns = new HashMap<Date, VitalSigns>();
    }
    
    /**
     * Creates a dialog to collect personal data of the new patient
     * 
     * @param v
     */
    public void gatherData(View v) {
	final Dialog dialog = new Dialog(this);
	dialog.setContentView(R.layout.dialog_data);
	dialog.setTitle("Patient Data Input");

	final DatePicker datePicker = (DatePicker) dialog
		.findViewById(R.id.datePicker1);
	datePicker.init(Calendar.getInstance().get(Calendar.YEAR), Calendar
		.getInstance().get(Calendar.MONTH),
		Calendar.getInstance().get(Calendar.DAY_OF_MONTH), null);

	Button dialogButton = (Button) dialog.findViewById(R.id.done_button);
	dialogButton.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		if (((EditText) dialog.findViewById(R.id.patient_name_field))
			.getText().toString() == null
			& ((EditText) dialog.findViewById(R.id.heathcard_field))
				.getText().toString() == null) {
		    ((EditText) dialog.findViewById(R.id.patient_name_field))
			    .setHint("Name missing");
		    ((EditText) dialog.findViewById(R.id.heathcard_field))
			    .setHint("Health card number missing");
		    personalData = null;
		} else {
		    Date date;
		    try {
			date = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH).parse(datePicker.getYear()
				+ "-" + datePicker.getMonth() + "-"
				+ datePicker.getDayOfMonth());
			personalData = new Data(((EditText) dialog
				.findViewById(R.id.patient_name_field))
				.getText().toString(), date, ((EditText) dialog
				.findViewById(R.id.heathcard_field)).getText()
				.toString());
		    } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }

		}
		dialog.dismiss();
	    }
	});
	dialog.show();

    }

    /**
     * Creates a new dialog to add a new symptom of the patient
     * 
     * @param v
     */
    public void addSymptom(View v) {
	final Dialog dialog = new Dialog(this);
	dialog.setContentView(R.layout.dialog_symptom);
	dialog.setTitle("Patient Symptom Description");
	((TextView) dialog.findViewById(R.id.current_time_field))
		.setText(new Date().toString());
	Button dialog_addButton = (Button) dialog
		.findViewById(R.id.add_symptom_button);
	dialog_addButton.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		symptoms.put(new Date(), ((EditText) dialog
			.findViewById(R.id.symtom_description_field)).getText()
			.toString());
		;
		((EditText) dialog.findViewById(R.id.symtom_description_field))
			.setText("");
		((EditText) dialog.findViewById(R.id.symtom_description_field))
			.setHint("Symtoms Description Here");
		dialog.dismiss();
	    }
	});
	Button dialog_cancelButton = (Button) dialog
		.findViewById(R.id.cancel_symptom_button);
	dialog_cancelButton.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		((EditText) dialog.findViewById(R.id.symtom_description_field))
			.setText("");
		((EditText) dialog.findViewById(R.id.symtom_description_field))
			.setHint("Symtoms Description Here");
		dialog.dismiss();
	    }
	});
	dialog.show();
    }

    /**
     * Adds a new patient using the personal data, vitalsign, symptoms to the
     * list of patients and navigates the user back to the DisplayActivity
     * 
     * @param v
     */
    public void addNewPatient(View v) {
	DataHandler dh;
	try {
	    dh = new DataHandler(this.getApplicationContext().getFilesDir(),
		    DataHandler.PATIENT_DATA);
	    vitalSigns
		    .put(new Date(),
			    new VitalSigns(
				    Double.parseDouble(((EditText) findViewById(R.id.temperature_field))
					    .getText().toString()),
				    Double.parseDouble(((EditText) findViewById(R.id.heartRate_field))
					    .getText().toString()),
				    new BloodPressure(
					    Integer.parseInt(((EditText) findViewById(R.id.bloodpressure_systolic_field))
						    .getText().toString()),
					    Integer.parseInt(((EditText) findViewById(R.id.bloodpressure_diastolic_field))
						    .getText().toString()))));
	    Patient pat = new Patient(personalData, vitalSigns, symptoms,
		    new Date(), new Prescription());

	    dh.appendNewPatient(pat);
	    try {
		dh.savePatientsToFile(this
			.getApplicationContext()
			.openFileOutput(DataHandler.PATIENT_DATA, MODE_PRIVATE));
	    } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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

	Intent newIntent = new Intent(this, DisplayActivityNurse.class);
	startActivity(newIntent);
    }


}
