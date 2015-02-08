/**
 * 
 */
package com.example.phaseiii;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 *
 */
public class PatientArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] patients;

    /**
     * Creates a PatientArrayAdapter with the given context and String array of Patients
     * @param context the context of the new PatientArrayAdapter
     * @param patients the array of patients of the new PatientArrayAdapter
     */
    public PatientArrayAdapter(Context context, String[] patients) {
	super(context, R.layout.list_patient, patients);
	this.context = context;
	this.patients = patients;
    }

    /* (non-Javadoc)
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
	LayoutInflater inflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	View rowView = inflater.inflate(R.layout.list_patient, parent, false);
	TextView patientName = (TextView) rowView
		.findViewById(R.id.patient_name_field);
	patientName.setText(patients[position]);
	return rowView;
    }
}
