/**
 * 
 */
package com.example.phaseiii;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phaseiii.files.DataHandler;
import com.example.phaseiii.patient.BloodPressure;
import com.example.phaseiii.patient.VitalSigns;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 *
 */
public class PatientList extends ListActivity {
    private DataHandler dh;
    private String patientName;
    private String mode;
    private Map<Date, String> symptoms;
    private String[] symptomArr; // String array of all the symptoms
    private String[] dates; // String array of all the dates
    private Context context=this;
    private Map<Date, VitalSigns> vitalSigns;
    private String type;
   
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Intent intent=this.getIntent();
	mode = (String) intent.getSerializableExtra("mode");
	type = (String) intent.getSerializableExtra("type");
	try {
	    dh = new DataHandler(this.getApplicationContext().getFilesDir(),
		    DataHandler.PATIENT_DATA);
	    if(mode.equals("PatientView")) {
		 String[] patientNames = new String[dh.getPatients().values()
		                		    .size() + 1];
		 patientNames[0] = "back";
		 for (int x = 0; x < patientNames.length - 1; x++) {
			patientNames[x + 1] = dh.getPatients().get(x).getData()
		                			.getName();
		}
		setListAdapter(new PatientArrayAdapter(this, patientNames));
	    } else if(mode.equals("SymptomList")) {
		patientName=(String) intent.getSerializableExtra("name");
		for (int x = 0; x < dh.getPatients().size(); x++) {
			if (dh.getPatients().get(x).getData().getName()
				.equals(patientName)) {
			    symptoms = dh.getPatients().get(x).getSymptoms();
			    Iterator<Date> itr = dh.getPatients().get(x)
				    .getSymptoms().keySet().iterator();
			    dates = new String[symptoms.size()];
			    int i = 0;
			    while (itr.hasNext()) {
				dates[i++] = itr.next().toString();
			    }
			}
		    }
		    symptomArr = new String[dates.length + 1];
		    symptomArr[0] = patientName;
		    for (int x = 0; x < dates.length; x++) {
			symptomArr[x + 1] = dates[x];
		    }
		    setListAdapter(new PatientArrayAdapter(this, symptomArr));
	    } else if(mode.equals("VitalList")) {
		patientName=(String) intent.getSerializableExtra("name");
		for (int x = 0; x < dh.getPatients().size(); x++) {
			if (dh.getPatients().get(x).getData().getName()
				.equals(patientName)) {
			    vitalSigns = dh.getPatients().get(x).getVitalSigns();
			    Iterator<Date> itr = dh.getPatients().get(x)
				    .getVitalSigns().keySet().iterator();
			    dates = new String[vitalSigns.size()];
			    int i = 0;
			    while (itr.hasNext()) {
				dates[i++] = itr.next().toString();
			    }
			}
		    }
		    
		    if(type.equals("Nurse")) {
			String[] passDate = new String[dates.length + 2];
			    passDate[0] = patientName;
			passDate[dates.length + 1] = "New Data";
			for (int x = 0; x < dates.length; x++) {
			    passDate[x + 1] = dates[x];
			}
			setListAdapter(new PatientArrayAdapter(this, passDate));
		    } else {
			String[] passDate = new String[dates.length + 1];
			    passDate[0] = patientName;
			for (int x = 0; x < dates.length; x++) {
			    passDate[x + 1] = dates[x];
			}
			setListAdapter(new PatientArrayAdapter(this, passDate));
		    }
		    
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

    /* (non-Javadoc)
     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
    protected void onListItemClick(ListView l, View v, int position, long id) {
	if(mode.equals("PatientView")) {
	    if (position == 0) {
		Intent newIntent = new Intent(this, DisplayActivityNurse.class);
		startActivity(newIntent);
	    } else {
		String selectedPatientName = (String) getListAdapter().getItem(
		    position);
		Intent newIntent = new Intent(this, PatientInfo.class);
		newIntent.putExtra("name", selectedPatientName);
		newIntent.putExtra("type", type);
		startActivity(newIntent);
	    }
	} else if(mode.equals("SymptomList")) {
	    String selectedSymptom = (String) getListAdapter().getItem(position);
		if (position == 0) {
		    Intent intentTwo = new Intent(this, PatientInfo.class);
		    intentTwo.putExtra("name", this.patientName);
		    intentTwo.putExtra("type", type);
		    startActivity(intentTwo);
		} else {
		    final Dialog dialog = new Dialog(this);
		    dialog.setContentView(R.layout.dialog_symptom);
		    dialog.setTitle("Patient Symptom Description");
		    ((TextView) dialog.findViewById(R.id.current_time_field))
			    .setText(dates[position - 1]);
		    try {
			((TextView) dialog.findViewById(R.id.symtom_description_field))
				.setText(symptoms.get(new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
					.parse(dates[position - 1])));
		    } catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
		    
		    if(type.equals("Physician")) {
			    ((TextView) dialog.findViewById(R.id.symtom_description_field)).setClickable(false);
			    ((TextView) dialog.findViewById(R.id.symtom_description_field)).setFocusable(false);
			    ((TextView) dialog.findViewById(R.id.symtom_description_field)).setOnClickListener(null);
			    ((Button) dialog.findViewById(R.id.add_symptom_button)).setVisibility(Button.INVISIBLE);
		    } else {
		    
		    Button dialog_addButton = (Button) dialog
			    .findViewById(R.id.add_symptom_button);
		    dialog_addButton.setText("Add new");
		    dialog_addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			    final Dialog dialogTwo = new Dialog(dialog.getContext());
			    dialogTwo.setContentView(R.layout.dialog_symptom);
			    dialogTwo.setTitle("Patient Symptom Description");
			    ((TextView) dialogTwo.findViewById(R.id.current_time_field))
				    .setText(new Date().toString());
			    Button dialog_addButton = (Button) dialogTwo
				    .findViewById(R.id.add_symptom_button);
			    dialog_addButton.setText("Add new");
			    dialog_addButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				    symptoms.put(
					    new Date(),
					    ((EditText) dialogTwo
						    .findViewById(R.id.symtom_description_field))
						    .getText().toString());
				    System.out.println(((EditText) dialogTwo
					    .findViewById(R.id.symtom_description_field))
					    .getText().toString());
				    ((EditText) dialogTwo
					    .findViewById(R.id.symtom_description_field))
					    .setText("");
				    ((EditText) dialogTwo
					    .findViewById(R.id.symtom_description_field))
					    .setHint("Symtoms Description Here");
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
				    dialogTwo.dismiss();
				}
			    });
			    Button dialog_cancelButton = (Button) dialogTwo
				    .findViewById(R.id.cancel_symptom_button);
			    dialog_cancelButton.setText("Back");
			    dialog_cancelButton
				    .setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
					    dialogTwo.dismiss();
					}
				    });
			    dialogTwo.show();
			    dialog.dismiss();
			}
		    });
		    }
		    Button dialog_cancelButton = (Button) dialog
			    .findViewById(R.id.cancel_symptom_button);
		    dialog_cancelButton.setText("Back");
		    dialog_cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			    dialog.dismiss();
			}
		    });
		    dialog.show();

		}
	} else if(mode.equals("VitalList")) {
	    String selectedSymptom = (String) getListAdapter().getItem(position);
		if (position == 0) {
		    Intent intentTwo = new Intent(this, PatientInfo.class);
		    intentTwo.putExtra("name", this.patientName);
		    intentTwo.putExtra("type", type);
		    startActivity(intentTwo);
		    return;
		} 
		if ((position == dates.length + 1)&&type.equals("Nurse")) {
			final Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.dialog_vitalsign);
			dialog.setTitle("Input New VitalSign Data");
		    	Button dialogButton = (Button) dialog.findViewById(R.id.landscape_view_symptoms_button);
		    	dialogButton.setOnClickListener(new OnClickListener() {
		    	    @Override
		    	    public void onClick(View v) {
		    		dialog.dismiss();
		    	    }
		    	});
		    	Button dialogButton1 = (Button) dialog.findViewById(R.id.find_patient_button);
		    	dialogButton1.setOnClickListener(new OnClickListener() {
		    	    @Override
		    	    public void onClick(View v) {
		    		vitalSigns.put(
		    			new Date(),
		    			new VitalSigns(
					    Double.parseDouble(((EditText) dialog.findViewById(R.id.dialog_healthcard_field))
						    .getText().toString()),
					    Double.parseDouble(((EditText) dialog.findViewById(R.id.editText2))
						    .getText().toString()),
					    new BloodPressure(
						    Integer.parseInt(((EditText) dialog.findViewById(R.id.editText3))
							    .getText().toString()),
						    Integer.parseInt(((EditText) dialog.findViewById(R.id.editText4))
							    .getText().toString()))));
		    		try {
		    		    dh.savePatientsToFile(context.getApplicationContext()
		    			    .openFileOutput(DataHandler.PATIENT_DATA,
		    				    MODE_PRIVATE));
		    		} catch (FileNotFoundException e) {
		    		    // TODO Auto-generated catch block
		    		    e.printStackTrace();
		    		}
		    		dialog.dismiss();
		    	    }
		    	});
		    	dialog.show();
		    	return;
		    }
		    final Dialog dialog = new Dialog(this);
		    dialog.setContentView(R.layout.dialog_vitalsign);
		    dialog.setTitle("Patient VitalSign Description");

		    try {
			((EditText) dialog.findViewById(R.id.dialog_healthcard_field))
				.setText(vitalSigns.get(
					new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss z yyyy",
						Locale.ENGLISH)
						.parse(dates[position - 1]))
					.getTemperature()
					+ "");
			((EditText) dialog.findViewById(R.id.editText2))
				.setText(vitalSigns.get(
					new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss z yyyy",
						Locale.ENGLISH)
						.parse(dates[position - 1]))
					.getHeartRate()
					+ "");
			((EditText) dialog.findViewById(R.id.editText3))
				.setText(vitalSigns
					.get(new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss z yyyy",
						Locale.ENGLISH)
						.parse(dates[position - 1]))
					.getBloodPressure().getSystolic()
					+ "");
			((EditText) dialog.findViewById(R.id.editText4))
				.setText(vitalSigns
					.get(new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss z yyyy",
						Locale.ENGLISH)
						.parse(dates[position - 1]))
					.getBloodPressure().getDiastolic()
					+ "");
		    } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }

		    ((EditText) dialog.findViewById(R.id.dialog_healthcard_field))
			    .setKeyListener(null);
		    ((EditText) dialog.findViewById(R.id.editText2))
			    .setKeyListener(null);
		    ((EditText) dialog.findViewById(R.id.editText3))
			    .setKeyListener(null);
		    ((EditText) dialog.findViewById(R.id.editText4))
			    .setKeyListener(null);

		    ((Button) dialog.findViewById((R.id.landscape_view_symptoms_button)))
			    .setVisibility(Button.INVISIBLE);

		    Button dialog_addButton = (Button) dialog
			    .findViewById(R.id.find_patient_button);
		    dialog_addButton.setText("back");
		    dialog_addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			    dialog.dismiss();
			}
		    });
		    dialog.show();
		}
	
    }
}
