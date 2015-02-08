/**
 * 
 */
package com.example.phaseiii.patient;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 * 
 */
public class Patient {

    private Data data; // the personal data of this Patient
    private Map<Date, VitalSigns> vitalSigns; // the maps of vital signs of this
					      // Patient
    private List<Date> vitalSignDate; // the list of dates of when the vital
				      // sign were taken of this Patient
    private Date arrivalTime; // arrival time of this Patient
    private Date timeSeen; // time seen by the doctor of this Patient
    private Map<Date, String> symptoms; // the map of symptoms of this Patient
    private int urgency; // the urgency level of this Patient
    private Prescription prescription;

    /**
     * Creates a new Patient with the given data, vitalsigns, symptoms and
     * arrivalTime
     * 
     * @param data
     *            personal data of the new Patient
     * @param vitalSigns
     *            map of vital signs of the new Patient
     * @param symptoms
     *            map of symptoms of the new Patient
     * @param arrivalTime
     *            arrival time of the new Patient
     */
    public Patient(Data data, Map<Date, VitalSigns> vitalSigns,
	    Map<Date, String> symptoms, Date arrivalTime, Prescription prescription) {
	this.data = data;
	this.vitalSigns = vitalSigns;
	this.symptoms = symptoms;
	this.arrivalTime = arrivalTime;
	urgency = this.getLastElement(vitalSigns.values()).getUrgency();
	if (data.getAge() < 2) {
	    urgency += 1;
	}
	this.prescription=prescription;
    }
    
    public Prescription getPrescription() {
	return prescription;
    }

    private VitalSigns getLastElement(Collection<VitalSigns> set) {
	Iterator<VitalSigns> itr = set.iterator();
	VitalSigns le = itr.next();
	while (itr.hasNext()) {
	    le = itr.next();
	}
	return le;
    }

    /**
     * Returns the data of this Patient
     * 
     * @return the data
     */
    public Data getData() {
	return data;
    }

    /**
     * Set the data of this Patient to the given data
     * 
     * @param data
     *            the data to be patient's data
     */
    public void setData(Data data) {
	this.data = data;
    }

    /**
     * Returns the map of vital sign of this Patient
     * 
     * @return the vitalSigns of this Patient
     */
    public Map<Date, VitalSigns> getVitalSigns() {
	return vitalSigns;
    }

    /**
     * Put the given vital sign into the map of vital sign with the current date
     * 
     * @param vitalSigns
     *            the vitalSigns to be put into the map of vital sign with the
     *            current date
     */
    public void recordVitalSigns(VitalSigns vitals) {
	Date x = new Date();
	vitalSigns.put(x, vitals);
	vitalSignDate.add(x);
    }

    /**
     * Returns the arrival time of this Patient
     * 
     * @return the arrivalTime of this Patient
     */
    public Date getArrivalTime() {
	return arrivalTime;
    }

    /**
     * Set the arrival time of this Patient to the given arrival time
     * 
     * @param arrivalTime
     *            the arrivalTime to set
     */
    public void setArrivalTime(Date arrivalTime) {
	this.arrivalTime = arrivalTime;
    }

    /**
     * Return the time seen by doctor of this Patient
     * 
     * @return the timeSeen by doctor of this Patient
     */
    public Date getTimeSeen() {
	return timeSeen;
    }

    /**
     * Sets the time seen by doctor of this Patient to the given time seen
     * 
     * @param timeSeen
     *            the timeSeen to set as the time seen of this Patient
     */
    public void setTimeSeen(Date timeSeen) {
	this.timeSeen = timeSeen;
    }

    /**
     * Returns the map of symptoms of this Patient
     * 
     * @return the symptoms the symptoms of this Patient
     */
    public Map<Date, String> getSymptoms() {
	return symptoms;
    }

    /**
     * Puts the given symptom to the map of symptom of this Patient along with
     * the current date
     * 
     * @param symptoms
     *            the symptoms to put in to the map of symptom of this Patient
     *            along with the current date
     */
    public void setSymptoms(String symptoms) {
	this.symptoms.put(new Date(), symptoms);
    }

    /**
     * Returns the urgency of this Paitent
     * 
     * @return the urgency of this Paitent
     */
    public int getUrgency() {
	return this.urgency;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

	String dynamicData = "";
	for (int x = 0; x < vitalSigns.size(); x++) {
	    dynamicData += ", " + vitalSigns.keySet().toArray()[x] + ", "
		    + vitalSigns.values().toArray()[x].toString();
	}
	dynamicData += ", *";
	for (int x = 0; x < symptoms.size(); x++) {
	    dynamicData += ", " + symptoms.keySet().toArray()[x] + ", "
		    + symptoms.values().toArray()[x].toString();
	}

	return data + ", " + arrivalTime + ", " + prescription + dynamicData;
    }
}
