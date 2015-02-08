/**
 * 
 */
package com.example.phaseiii.patient;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 *
 */
public class VitalSigns {
    private double temperature; // temperature of this Vitalsign
    private double heartRate; // heart rate of this Vitalsign
    private BloodPressure bloodPressure; // blood pressure of this Vitalsign
    private int urgency; // urgency level of this Vitalsign

    /**
     * Creates a new VitalSign with the given temperature, heartrate and
     * bloodpressure
     * 
     * @param temperature
     *            the given temperature of the new VitalSign
     * @param heartRate
     *            the given heartRate of the new VitalSign
     * @param bloodPressure
     *            the given bloodPressure of the new VitalSign
     */
    public VitalSigns(double temperature, double heartRate,
	    BloodPressure bloodPressure) {
	this.temperature = temperature;
	this.heartRate = heartRate;
	this.bloodPressure = bloodPressure;
	setUrgency();
    }

    /**
     * Returns the temperature of this Vitalsign
     * 
     * @return the temperature of the new VitalSign
     */
    public double getTemperature() {
	return temperature;
    }

    /**
     * Returns the heart rate of the new VitalSign
     * 
     * @return the heartRate of the new VitalSign
     */
    public double getHeartRate() {
	return heartRate;
    }

    /**
     * Returns the blood pressure of the new VitalSign
     * 
     * @return the bloodPressure of the new VitalSign
     */
    public BloodPressure getBloodPressure() {
	return bloodPressure;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return temperature + ", " + heartRate + ", " + bloodPressure;
    }

    /**
     * Calculates and sets the urgency of the new VitalSign
     */
    public void setUrgency() {
	urgency = 0;
	if (temperature >= 39.0) {
	    urgency += 1;
	}
	if (bloodPressure.getSystolic() >= 140
		|| bloodPressure.getDiastolic() >= 90) {
	    urgency += 1;
	}
	if (heartRate >= 100 || heartRate <= 50) {
	    urgency += 1;
	}
    }

    /**
     * Return the urgency of the new VitalSign
     * 
     * @return the int urgency of the new VitalSign
     */
    public int getUrgency() {
	return urgency;
    }


}
