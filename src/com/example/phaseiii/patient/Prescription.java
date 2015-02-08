/**
 * 
 */
package com.example.phaseiii.patient;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 *
 */
public class Prescription {
    private String medication;
    private String instructions;
    /**
     * @param medication
     * @param instructions
     */
    public Prescription(String medication, String instructions) {
	this.medication = medication;
	this.instructions = instructions;
    }
    public Prescription() {
	this.medication="none";
	this.instructions="none";
    }
    public String getMedication() {
        return medication;
    }
    public void setMedication(String medication) {
        this.medication = medication;
    }
    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return medication+", "+instructions;
    }
    
    

}
