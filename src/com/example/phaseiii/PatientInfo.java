/**
 * 
 */
package com.example.phaseiii;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.phaseiii.files.DataHandler;
import com.example.phaseiii.patient.Data;
import com.example.phaseiii.patient.Patient;
import com.example.phaseiii.patient.VitalSigns;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 * 
 */
public class PatientInfo extends Activity {
    private String patientName; // the patientName
    private Patient patient; // the current patient holder
    private DataHandler dh;
    private String type;
    private Context context = this;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_patient_info_display);
	Intent intent = getIntent();
	patientName = (String) intent.getSerializableExtra("name");
	((TextView) findViewById(R.id.patient_name)).setText(patientName);
	type = (String) intent.getSerializableExtra("type");
	if (type.equals("Physician")) {
	    ((Button) findViewById(R.id.edit_personal_data_button))
		    .setText("View Personal Data");
	} else if (type.equals("Nurse")) {
	    ((Button) findViewById(R.id.edit_personal_data_button))
		    .setText("Edit Personal Data");
	    ((Button) findViewById(R.id.perscription_button))
		    .setVisibility(Button.INVISIBLE);

	}
	try {
	    dh = new DataHandler(this.getApplicationContext().getFilesDir(),
		    DataHandler.PATIENT_DATA);
	    for (int x = 0; x < dh.getPatients().size(); x++) {
		if (dh.getPatients().get(x).getData().getName()
			.equals(patientName)) {
		    patient = dh.getPatients().get(x);
		}
	    }
	    if (type.equals("Physician")) {
		patient.setTimeSeen(new Date());
	    }
	    if (patient.getTimeSeen() == null) {
		((TextView) findViewById(R.id.time_last_seen_field))
			.setText("Not Yet Seen.");
	    } else {
		((TextView) findViewById(R.id.time_last_seen_field))
			.setText(patient.getTimeSeen().toString());
	    }
	    ((TextView) findViewById(R.id.arrivalTime_field)).setText(patient
		    .getArrivalTime().toString());

	    ((TextView) findViewById(R.id.urgency_field)).setText(this
		    .getLastValue(patient.getVitalSigns().values())
		    .getUrgency()
		    + "");
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

    /**
     * Returns the last vitalsign of this collection of vital signs
     * 
     * @param vitalsign
     *            the collection of vitalsigns
     * @return the last vitalsign of this collection of vital signs
     */
    private VitalSigns getLastValue(Collection<VitalSigns> vitalsign) {
	Iterator<VitalSigns> itr = vitalsign.iterator();
	VitalSigns vitalSign = itr.next();
	while (itr.hasNext())
	    vitalSign = itr.next();
	return vitalSign;
    }

    public void updateLastSeenTime(View v) {
	if (type.equals("Physician")) {
	    new AlertDialog.Builder(this)
		    .setTitle("Error")
		    .setMessage("Only nurses are allowed to set time seen.")
		    .setNeutralButton("Yes!",
			    new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
					int which) {
				    dialog.cancel();
				}
			    }).show();
	    return;
	}
	final Dialog dialog = new Dialog(this);
	dialog.setContentView(R.layout.dialog_time_picker);
	dialog.setTitle("Set Time");
	final TimePicker timePicker = (TimePicker) dialog
		.findViewById(R.id.timePicker1);
	final Calendar calendar = Calendar.getInstance();
	if (patient.getTimeSeen() != null) {
	    calendar.setTime(patient.getTimeSeen());
	}
	timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
	timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
	Button dialogSetTimeButton = (Button) dialog
		.findViewById(R.id.set_time_button);
	dialogSetTimeButton.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Date date;
		if (patient.getTimeSeen() == null) {
		    date = new Date();
		} else {
		    date = patient.getTimeSeen();
		}
		calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
		calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
		date = calendar.getTime();
		patient.setTimeSeen(date);
		if (patient.getTimeSeen() == null) {
		    ((TextView) findViewById(R.id.time_last_seen_field))
			    .setText("Not Yet Seen.");
		} else {
		    ((TextView) findViewById(R.id.time_last_seen_field))
			    .setText(patient.getTimeSeen().toString());
		    try {
			dh.savePatientsToFile(context.getApplicationContext()
				.openFileOutput(dh.PATIENT_DATA, MODE_PRIVATE));
		    } catch (FileNotFoundException e) {
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
     * Creates a new dialog with the personal data of this PatientInfo and saves
     * the new adjustment to the patient when the dialog is closed
     * 
     * @param v
     */
    public void editPersonalData(View v) {
	final Dialog dialog = new Dialog(this);
	dialog.setContentView(R.layout.dialog_data);
	dialog.setTitle("Patient Data");
	final DatePicker datePicker = (DatePicker) dialog
		.findViewById(R.id.datePicker1);
	datePicker.init(patient.getData().getBirthDate().getYear(), patient
		.getData().getBirthDate().getMonth(), patient.getData()
		.getBirthDate().getDay(), null);

	((TextView) dialog.findViewById(R.id.patient_name_field))
		.setText(patient.getData().getName());
	((TextView) dialog.findViewById(R.id.heathcard_field)).setText(patient
		.getData().getHealthCard());
	if (type.equals("Physician")) {
	    ((TextView) dialog.findViewById(R.id.patient_name_field))
		    .setClickable(false);
	    ((TextView) dialog.findViewById(R.id.patient_name_field))
		    .setFocusable(false);
	    ((TextView) dialog.findViewById(R.id.patient_name_field))
		    .setOnClickListener(null);

	    ((TextView) dialog.findViewById(R.id.heathcard_field))
		    .setClickable(false);
	    ((TextView) dialog.findViewById(R.id.heathcard_field))
		    .setFocusable(false);
	    ((TextView) dialog.findViewById(R.id.heathcard_field))
		    .setOnClickListener(null);

	    datePicker.setClickable(false);
	    datePicker.setFocusable(false);
	    datePicker.setOnClickListener(null);
	}
	Button dialogButton = (Button) dialog.findViewById(R.id.done_button);
	dialogButton.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		if (type.equals("Nurse")) {
		    if (((EditText) dialog
			    .findViewById(R.id.patient_name_field)).getText()
			    .toString() == null
			    & ((EditText) dialog
				    .findViewById(R.id.heathcard_field))
				    .getText().toString() == null) {
			((EditText) dialog
				.findViewById(R.id.patient_name_field))
				.setHint("Name missing");
			((EditText) dialog.findViewById(R.id.heathcard_field))
				.setHint("Health card number missing");
		    } else {
			Date date;
			try {
			    date = new SimpleDateFormat("yyyy-MM-dd",
				    Locale.ENGLISH).parse(datePicker.getYear()
				    + "-" + datePicker.getMonth() + "-"
				    + datePicker.getDayOfMonth());
			    patient.setData(new Data(
				    ((EditText) dialog
					    .findViewById(R.id.patient_name_field))
					    .getText().toString(),
				    date,
				    ((EditText) dialog
					    .findViewById(R.id.heathcard_field))
					    .getText().toString()));
			    try {
				dh.savePatientsToFile(context
					.getApplicationContext()
					.openFileOutput(
						dh.PATIENT_DATA,
						MODE_PRIVATE));
			    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }
			    dialog.dismiss();
			} catch (ParseException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		    }
		} else if (type.equals("Physician")) {
		    dialog.dismiss();
		}
	    }
	});
	dialog.show();
    }

    /**
     * Navigates to vitalsignslist class
     * 
     * @param v
     */
    public void viewAllVitalSigns(View v) {
	Intent newIntent = new Intent(this, PatientList.class);
	newIntent.putExtra("mode", "VitalList");
	newIntent.putExtra("type", type);
	newIntent.putExtra("name", patientName);
	startActivity(newIntent);
    }

    /**
     * Navigates to back to the patient list class
     * 
     * @param v
     */
    public void back(View v) {
	if (type.equals("Physician")) {
	    Intent newIntent = new Intent(this, DisplayActivityPhysician.class);
	    startActivity(newIntent);
	    return;
	}

	Intent newIntent = new Intent(this, PatientList.class);
	newIntent.putExtra("mode", "PatientView");
	newIntent.putExtra("type", type);
	startActivity(newIntent);
    }

    public void openPrescription(View v) {
	final Dialog dialog = new Dialog(this);
	dialog.setContentView(R.layout.dialog_symptom);
	dialog.setTitle("Patient Prescription Info");
	((TextView) dialog.findViewById(R.id.current_time_field))
		.setText(patient.getPrescription().getMedication());
	((TextView) dialog.findViewById(R.id.symtom_description_field))
		.setText(patient.getPrescription().getMedication());
	Button dialog_addButton = (Button) dialog
		.findViewById(R.id.add_symptom_button);
	dialog_addButton.setText("Change");
	dialog_addButton.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		patient.getPrescription().setMedication(
			((TextView) dialog
				.findViewById(R.id.current_time_field))
				.getText()
				+ "");
		patient.getPrescription().setInstructions(
			((TextView) dialog
				.findViewById(R.id.symtom_description_field))
				.getText()
				+ "");

		try {
		    dh.savePatientsToFile(context.getApplicationContext()
			    .openFileOutput(dh.PATIENT_DATA, MODE_PRIVATE));
		} catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		dialog.dismiss();
	    }
	});
	Button dialog_cancelButton = (Button) dialog
		.findViewById(R.id.cancel_symptom_button);
	dialog_cancelButton.setText("Back");
	dialog_cancelButton.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		dialog.dismiss();
	    }
	});
	dialog.show();

    }

    /**
     * Navigates to the symptom list class
     * 
     * @param v
     */
    public void viewSymptoms(View v) {
	Intent newIntent = new Intent(this, PatientList.class);
	newIntent.putExtra("mode", "SymptomList");
	newIntent.putExtra("type", type);
	newIntent.putExtra("name", patientName);
	startActivity(newIntent);
    }

    public void onConfigurationChanged(Configuration newConfig) {
	super.onConfigurationChanged(newConfig);
	if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
		&& type.equals("Nurse")) {
	    Intent intent = new Intent(this,
		    DisplayActivityHorizonalPatientInfo.class);
	    intent.putExtra("name", patientName);
	    intent.putExtra("type", this.type);
	    startActivity(intent);
	    overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);

	}
    }

}
