/**
 * 
 */
package com.example.phaseiii.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import com.example.phaseiii.patient.BloodPressure;
import com.example.phaseiii.patient.Data;
import com.example.phaseiii.patient.Patient;
import com.example.phaseiii.patient.Prescription;
import com.example.phaseiii.patient.VitalSigns;
import com.example.phaseiii.users.Nurse;
import com.example.phaseiii.users.Physician;
import com.example.phaseiii.users.User;


/**
 * @author Andy Kuang-Yen Sheng 1000972566
 * 
 */
public class DataHandler {
    private List<User> users; // list of the users
    private Map<Integer, Patient> patients; // map of the patients
    public static final String PATIENT_DATA = "PatientInfo.txt"; // the filename
								 // of patient
								 // information
    public static final String NURSE_DATA = "NurseInfo.txt"; // the filename of
							     // the nurse login
							     // information
    public static final String PHYSICIAN_DATA = "PHYSICIANInfo.txt"; // the filename of
							     // the physician
							     // login
							     // information

    public DataHandler(File dir, String fileName) throws IOException,
	    NumberFormatException, ParseException {
	users = new ArrayList<User>();
	patients = new HashMap<Integer, Patient>();
	File file = new File(dir, fileName);
	if (file.exists()) {
	    if (fileName.equals(this.PATIENT_DATA)) {
		this.loadPatients(file.getPath());
	    } else {
		this.loadUser(file.getPath());
	    }
	} else {
	    if (fileName.equals(this.PATIENT_DATA)) {
		new File(dir, PATIENT_DATA).createNewFile();
	    } else if(fileName.equals(this.NURSE_DATA)) {
		new File(dir, NURSE_DATA).createNewFile();
	    } else if(fileName.equals(this.PHYSICIAN_DATA)) {
		new File(dir, PHYSICIAN_DATA).createNewFile();
	    }
	}
    }

    /**
     * Reads off the disk and adds the users into the list of users
     * 
     * @param filePath
     *            String filepath of the file's path
     * @throws FileNotFoundException
     *             if the given filepath cannot be located or does not exist
     */
    private void loadUser(String filePath) throws FileNotFoundException {
	Scanner scanner = new Scanner(new FileInputStream(filePath));
	users.clear();
	while (scanner.hasNextLine()) {
	    if(filePath.endsWith(this.NURSE_DATA)) {
		users.add(new Nurse(scanner.nextLine(), scanner.nextLine()));
	    } else {
		users.add(new Physician(scanner.nextLine(), scanner.nextLine()));
	    }
	}
	scanner.close();
    }

    /**
     * Reads the patients off the file located at the filepath and recreates the
     * patient into a map
     * 
     * @param filePath
     *            String filepath of the file's path
     * @throws FileNotFoundException
     *             if the given filepath's file cannot be found
     * @throws NumberFormatException
     *             if the desired format of the Date is invalid
     * @throws ParseException
     *             if the String representation of the date cannot be parsed
     *             back to recreate a Date object
     */
    private void loadPatients(String filePath) throws FileNotFoundException,
	    NumberFormatException, ParseException {
	Scanner scanner = new Scanner(new FileInputStream(filePath));
	patients.clear();
	String[] patientData;
	int key = 0;
	while (scanner.hasNextLine()) {
	    patientData = scanner.nextLine().split(", ");
	    Data tempData = new Data(patientData[0],
		    new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
			    Locale.ENGLISH).parse(patientData[1]),
		    patientData[2], Integer.parseInt(patientData[3]));
	    Date tempDate1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
		    Locale.ENGLISH).parse(patientData[4]);
	    
	    Prescription prep=new Prescription(patientData[5], patientData[6]);
	    
	    

	    Map<Date, VitalSigns> temp = new HashMap<Date, VitalSigns>();
	    int mark = 7;
	    for (int x = 0; !patientData[(x * 5) + 7].equals("*"); x++) {
		temp.put(
			new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
				Locale.ENGLISH).parse(patientData[(x * 5) + 7]),
			new VitalSigns(
				Double.parseDouble(patientData[(x * 5) + 7 + 1]),
				Double.parseDouble(patientData[(x * 5) + 7 + 2]),
				new BloodPressure(
					Integer.parseInt(patientData[(x * 5) + 7 + 3]),
					Integer.parseInt(patientData[(x * 5) + 7 + 4]))));
		mark = (x * 5) + 7 + 5;
	    }
	    Map<Date, String> temp2 = new HashMap<Date, String>();
	    mark++;
	    for (int x = mark; x < patientData.length - 1; x++) {

		temp2.put(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
			Locale.ENGLISH).parse(patientData[x]), patientData[++x]);
	    }
	    patients.put(key++, new Patient(tempData, temp, temp2, tempDate1, prep));

	}
	scanner.close();
    }
    /**
     * Writes the map of patients using the given FileOutputStream
     * 
     * @param outputStream
     *            the FileOutputStream used to write the patients
     */
    public void savePatientsToFile(FileOutputStream outputStream) {
	for (int x = 0; x < patients.size(); x++) {
	    try {
		outputStream.write((patients.get(x).toString() + "\n")
			.getBytes());
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    /**
     * Writes the list of users using the given FileOutputStream
     * 
     * @param outputStream
     *            the FileOutputStream used to write the users
     */
    public void saveUsersToFile(FileOutputStream outputStream) {
	for (int x = 0; x < users.size(); x++) {
	    try {
		outputStream.write((users.get(x).getUsername() + "\n")
			.getBytes());
		outputStream.write((users.get(x).getPassword() + "\n")
			.getBytes());
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }
    
    /**
     * Returns true if the given username and password matches the list of users
     * otherwise false
     * 
     * @param username
     *            String username
     * @param password
     *            String password
     * @return true if the given username and password matches the list of users
     *         otherwise false
     */
    public boolean login(String username, String password) {
	System.out.println("Users");
	for(int x=0;x<users.size();x++) {
	    System.out.println(users.get(x).getUsername());
	    System.out.println(users.get(x).getPassword());
	}
	System.out.println();
	if (username == null || password == null || this.users.size() == 0) {
	    return false;
	}
	for (int x = 0; x < users.size(); x++) {
	    if (users.get(x).getUsername().equals(username)
		    && users.get(x).getPassword().equals(password)) {
		return true;
	    }
	}
	return false;
    }
    
    /**
     * Return true if the given username is available
     * 
     * @param username
     *            the String username that want to be checked for availability
     * @return true if the given username is available otherwise false
     */
    public boolean isAvailable(String username) {
	if (username == null || username.equals("")) {
	    return false;
	}
	for (int x = 0; x < users.size(); x++) {
	    if (users.get(x).getUsername().equals(username)) {
		return false;
	    }
	}
	return true;
    }
    
    /**
     * Added the given patient to the last place in the map of patients
     * 
     * @param patient
     *            the patient that is added as the last element in the map of
     *            patients
     */
    public void appendNewPatient(Patient patient) {
	patients.put(patients.size(), patient);
    }
    
    /**
     * Creates a new Nurse and adds to the list of users. Returns true if
     * successful, otherwise false
     * 
     * @param username
     *            the desired username
     * @param password
     *            the desired password
     * @return true if successful, otherwise false
     */
    public boolean createNewNurse(String username, String password) {
	if (username == null || password == null) {
	    return false;
	}
	return this.users.add(new Nurse(username, password));
    }
    
    /**
     * Creates a new Physician and adds to the list of users. Returns true if
     * successful, otherwise false
     * 
     * @param username
     *            the desired username
     * @param password
     *            the desired password
     * @return true if successful, otherwise false
     */
    public boolean createNewPhysician(String username, String password) {
	if (username == null || password == null) {
	    return false;
	}
	return this.users.add(new Physician(username, password));
    }
    /**
     * Returns the map of Patients
     * 
     * @return the map of Patients
     */
    public Map<Integer, Patient> getPatients() {
	return this.patients;
    }
    
    public Patient findPatientByCardNumber(String cardNum) {
	for(int x=0;x<patients.size();x++) {
	    if(patients.get(x).getData().getHealthCard().equals(cardNum)) {
		return patients.get(x);
	    }
	}
	return null;
    }

}