/**
 * 
 */
package com.example.phaseiii.patient;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 *
 */
public class BloodPressure {
    private int systolic; // the systolic of this blood pressure
    private int diastolic; // the diatolic of this blood pressure

    /**
     * Creates a new Bloodpressure with the given systolic and diastolic
     * measurement
     * 
     * @param systolic
     *            int systolic of the the Bloodpressure
     * @param diastolic
     *            int diastolic of the the Bloodpressure
     */
    public BloodPressure(int systolic, int diastolic) {
	this.systolic = systolic;
	this.diastolic = diastolic;
    }

    /**
     * Returns the systolic of this Bloodpressure
     * 
     * @return the systolic
     */
    public int getSystolic() {
	return systolic;
    }

    /**
     * Returns the diastolic of this Bloodpressure
     * 
     * @return the diastolic
     */
    public int getDiastolic() {
	return diastolic;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return systolic + ", " + diastolic;
    }

}
