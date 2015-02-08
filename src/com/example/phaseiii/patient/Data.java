/**
 * 
 */
package com.example.phaseiii.patient;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 *
 */
public class Data {
    private String name; // name of this data
    private Date birthDate; // birthdate of this data
    private String healthCard; // healthcard of this data
    private int age; // age of this data

    /**
     * Creates a new Data with the given name, birth date, health card number
     * and age
     * 
     * @param name
     *            String name of the new Data
     * @param birthDate
     *            Date birth date of the new Data
     * @param healthCard
     *            String health card number of the new Data
     * @param age
     *            int age of the new Data
     */
    public Data(String name, Date birthDate, String healthCard, int age) {
	this.name = name;
	this.birthDate = birthDate;
	this.healthCard = healthCard;
	this.age = age;
    }

    /**
     * Creates a new Data with the given name, birth date and healthcard
     * 
     * @param name
     *            String name of the new Data
     * @param birthDate
     *            Date birth date of the new Data
     * @param healthCard
     *            String health card number of the new Data
     */
    public Data(String name, Date birthDate, String healthCard) {
	this.name = name;
	this.birthDate = birthDate;
	this.healthCard = healthCard;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); // calculating the
	age = Integer.parseInt(sdf.format(new Date())) // age of the
		- Integer.parseInt(sdf.format(birthDate)); // new Data
    }

    /**
     * Returns the name of this Data
     * 
     * @return the name of this Data
     */
    public String getName() {
	return name;
    }

    /**
     * Return the birth date of this Data
     * 
     * @return the birthDate of this Data
     */
    public Date getBirthDate() {
	return birthDate;
    }

    /**
     * Returns the health card of this Data
     * 
     * @return the healthCard the health card of this Data
     */
    public String getHealthCard() {
	return healthCard;
    }

    /**
     * Returns the age of this Data
     * 
     * @return the age of this Data
     */
    public int getAge() {
	return age;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return name + ", " + birthDate + ", " + healthCard + ", " + age;
    }


}
