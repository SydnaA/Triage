/**
 * 
 */
package com.example.phaseiii;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.phaseiii.files.DataHandler;
import com.example.phaseiii.patient.Patient;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 *
 */
public class DisplayActivityNurse extends Activity {
    private DataHandler dh;
    private Context context=this;
    private int selection;
    private String patientNameHeld;
    private final String TYPE="Nurse";

    protected void onCreate(Bundle savedInstanceState) {
   	super.onCreate(savedInstanceState);
   	setContentView(R.layout.activity_display_nurse);
   	

	((EditText) findViewById(R.id.author_field)).setOnClickListener(null);
	((EditText) findViewById(R.id.author_field)).setSelected(false);
	((EditText) findViewById(R.id.author_field)).setFocusable(false);
	
	try {
	    dh = new DataHandler(this.getApplicationContext().getFilesDir(), DataHandler.PATIENT_DATA);
	    Iterator<Patient> itr = dh.getPatients().values().iterator();
	    List<Patient> pat = new ArrayList<Patient>();
	    while (itr.hasNext()) {
		pat.add(itr.next());
	    }
	    for(int x=0;x<pat.size();x++){
		if(pat.get(x).getTimeSeen()!=null) {
		    pat.remove(x);
		}
	    }
	    this.createUrgencyTable(getUrgencyMap(pat));
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
     * Creates a table from most urgent to least urgent given a urgency list
     * 
     * @param urgencyList
     *            the urgencyList of all the patient
     */
    private void createUrgencyTable(Map<Integer, List<Patient>> urgencyList) {
	TableLayout table = (TableLayout) this.findViewById(R.id.TableLayout1);
	for (int x = urgencyList.size() - 1; x >= 0; x--) {
	    for (int y = 0; y < urgencyList.get(x).size(); y++) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final TableRow row = (TableRow) inflater.inflate(
			R.layout.row_urgency, null, false);
		((TextView) row.findViewById(R.id.row_urgency_field)).setText(x
			+ "");
		((TextView) row.findViewById(R.id.row_name_field))
			.setText(urgencyList.get(x).get(y).getData().getName());
		SimpleDateFormat sfd = new SimpleDateFormat("h:mm a");
		((TextView) row.findViewById(R.id.row_arrival_field))
			.setText("Arrival Time: ");
		((TextView) row.findViewById(R.id.row_lastseen_field))
		    .setText(sfd.format(urgencyList.get(x).get(y)
			    .getArrivalTime()));
		if (x >= 3) {
		    row.setBackgroundColor(Color.RED);
		} else if (x == 2) {
		    row.setBackgroundColor(Color.YELLOW);
		} else if (x < 2) {
		    row.setBackgroundColor(Color.GREEN);
		}
		row.setOnTouchListener(new OnTouchListener() {
		    public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN) {
			    patientNameHeld=((TextView) row.findViewById(R.id.row_name_field)).getText().toString();
			} else if(event.getAction() == MotionEvent.ACTION_UP){
			    patientNameHeld=null;
			}
			return false;
		    }
	    	});
		table.addView(row);
	    }
	}
    }
    

    /**
     * Returns a Map where list of patients is categorized to their urgency
     * level
     * 
     * @param patients
     *            the list of all the patients
     * @return
     */
    private Map<Integer, List<Patient>> getUrgencyMap(List<Patient> patients) {
	Map<Integer, List<Patient>> listTwo = new HashMap<Integer, List<Patient>>();
	listTwo.put(0, new ArrayList<Patient>());
	listTwo.put(1, new ArrayList<Patient>());
	listTwo.put(2, new ArrayList<Patient>());
	listTwo.put(3, new ArrayList<Patient>());
	listTwo.put(4, new ArrayList<Patient>());

	for (int x = 0; x < patients.size(); x++) {
	    switch (patients.get(x).getUrgency()) {
	    case 0:
		listTwo.get(0).add(patients.get(x));
		break;
	    case 1:
		listTwo.get(1).add(patients.get(x));
		break;
	    case 2:
		listTwo.get(2).add(patients.get(x));
		break;
	    case 3:
		listTwo.get(3).add(patients.get(x));
		break;
	    case 4:
		listTwo.get(4).add(patients.get(x));
		break;

	    }
	}
	return listTwo;
    }

    /**
     * Navigates to the NewPatientActivity class while passing the name of the
     * user
     * 
     * @param v
     */
    public void addNewPatient(View v) {
	Intent newIntent = new Intent(this, NewPatientActivity.class);
	startActivity(newIntent);
    }

    /**
     * Navigates to the PatientList class while passing the name of the user
     * 
     * @param v
     */
    public void openListAll(View v) {
	Intent newIntent = new Intent(this, PatientList.class);
	newIntent.putExtra("mode", "PatientView");
	newIntent.putExtra("type", this.TYPE);
	startActivity(newIntent);
    }
    
    public void onConfigurationChanged(Configuration newConfig) {
	super.onConfigurationChanged(newConfig);
	if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    if(patientNameHeld!=null) {
		Intent intent=new Intent(this, DisplayActivityHorizonalPatientInfo.class);
		intent.putExtra("name", patientNameHeld);
		intent.putExtra("type", this.TYPE);
		startActivity(intent);
		overridePendingTransition(R.anim.rotate_out,R.anim.rotate_in);
	    } else {
		Intent intent = new Intent(this, DisplayActivityHorizonalInstruction.class);
		startActivity(intent);
		overridePendingTransition(R.anim.rotate_out,R.anim.rotate_in);
	    }
	}
    }
}
