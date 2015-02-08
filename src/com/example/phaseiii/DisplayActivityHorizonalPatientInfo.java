/**
 * 
 */
package com.example.phaseiii;

import java.io.IOException;
import java.text.ParseException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.phaseiii.files.DataHandler;
import com.example.phaseiii.patient.Patient;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 *
 */
public class DisplayActivityHorizonalPatientInfo extends Activity {
    private String type;
    private Patient patient;
    private DataHandler dh;
    private final int EDIT_PERSONAL_DATA=1;
    private final int VIEW_SYMPTOMS=2;
    private final int EDIT_VITALSIGNS=3;
    private final int RELEASED=0;
    private int touched;

    protected void onCreate(Bundle savedInstanceState) {
   	super.onCreate(savedInstanceState);
   	setContentView(R.layout.activity_display_patient_info);
   	Intent intent = this.getIntent();
   	touched=RELEASED;
	type = (String) intent.getSerializableExtra("type");
	String patientName=(String) intent.getSerializableExtra("name");
	try {
	    dh = new DataHandler(this.getApplicationContext().getFilesDir(),
	    	    DataHandler.PATIENT_DATA);
	    
	    for (int x = 0; x < dh.getPatients().size(); x++) {
		if (dh.getPatients().get(x).getData().getName()
			.equals(patientName)) {
		    this.patient = dh.getPatients().get(x);
		}
	    }
	    
	    ((TextView) findViewById(R.id.landscape_patient_name_field)).setText(patientName);
	    ((TextView) findViewById(R.id.landscape_arrival_time_field)).setText(patient.getArrivalTime().toString());
	    if(patient.getTimeSeen()!=null) {
		((TextView) findViewById(R.id.landscape_times_seen_by_doctor_field)).setText(patient.getTimeSeen().toString());
	    } else {
		((TextView) findViewById(R.id.landscape_times_seen_by_doctor_field)).setText("Not Yet Seen");
	    }
	    ((TextView) findViewById(R.id.landscape_urgency_level_field)).setText("Urgency: "+patient.getUrgency());
	    
	    ((Button) findViewById(R.id.landscape_edit_personal_data_button)).setOnTouchListener(new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
		    if(event.getAction() == MotionEvent.ACTION_DOWN) {
			    touched=EDIT_PERSONAL_DATA;
			    ((Button) findViewById(R.id.landscape_edit_personal_data_button)).setBackgroundColor(Color.GREEN);
			} else if(event.getAction() == MotionEvent.ACTION_UP){
			    touched=RELEASED;			    
			    ((Button) findViewById(R.id.landscape_edit_personal_data_button)).setBackgroundResource(android.R.drawable.btn_default);
			}
		    return false;
		}
	    });
	    
	    ((Button) findViewById(R.id.landscape_view_symptoms_button)).setOnTouchListener(new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
		    if(event.getAction() == MotionEvent.ACTION_DOWN) {
			    touched=VIEW_SYMPTOMS;
			    ((Button) findViewById(R.id.landscape_view_symptoms_button)).setBackgroundColor(Color.GREEN);
			} else if(event.getAction() == MotionEvent.ACTION_UP){
			    touched=RELEASED;
			    ((Button) findViewById(R.id.landscape_view_symptoms_button)).setBackgroundResource(android.R.drawable.btn_default);
			}
		    return false;
		}
	    });
	    
	    ((Button) findViewById(R.id.landscape_view_vitalsign_button)).setOnTouchListener(new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
		    if(event.getAction() == MotionEvent.ACTION_DOWN) {
			    touched=EDIT_VITALSIGNS;
			    ((Button) findViewById(R.id.landscape_view_vitalsign_button)).setBackgroundColor(Color.GREEN);
			} else if(event.getAction() == MotionEvent.ACTION_UP){
			    touched=RELEASED;
			    ((Button) findViewById(R.id.landscape_view_vitalsign_button)).setBackgroundResource(android.R.drawable.btn_default);
			}
		    return false;
		}
	    });
	    
	    
	    
	    
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
    
    public void onConfigurationChanged(Configuration newConfig) {
	super.onConfigurationChanged(newConfig);
	if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
	    Intent intent;
	    switch(touched) {
	    	case RELEASED:
	    	    intent=new Intent(this, DisplayActivityNurse.class);
		    startActivity(intent);
		    overridePendingTransition(R.anim.rotate_out,R.anim.rotate_in);
	    	    break;
	    	case EDIT_PERSONAL_DATA:
	    	    intent=new Intent(this, PatientInfo.class);
	    	    intent.putExtra("type", type);
	    	    intent.putExtra("name", patient.getData().getName());
		    startActivity(intent);
		    overridePendingTransition(R.anim.rotate_out,R.anim.rotate_in);
	    	    break;
	    	case VIEW_SYMPTOMS:
	    	    intent=new Intent(this, PatientList.class);
	    	    intent.putExtra("mode", "SymptomList");
	    	    intent.putExtra("type", type);
	    	    intent.putExtra("name", patient.getData().getName());
		    startActivity(intent);
		    overridePendingTransition(R.anim.rotate_out,R.anim.rotate_in);
	    	    break;
	    	case EDIT_VITALSIGNS:
	    	intent=new Intent(this, PatientList.class);
	    	    intent.putExtra("mode", "VitalList");
	    	    intent.putExtra("type", type);
	    	    intent.putExtra("name", patient.getData().getName());
		    startActivity(intent);
		    overridePendingTransition(R.anim.rotate_out,R.anim.rotate_in);
	    	    break;
	    }
	   
	}
    }
}
