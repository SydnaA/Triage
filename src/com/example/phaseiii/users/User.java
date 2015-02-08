/**
 * 
 */
package com.example.phaseiii.users;

/**
 * @author Andy Kuang-Yen Sheng 1000972566
 * 
 */
public abstract class User {
    protected String username;
    protected String password;

    /**
     * 
     */
    public User(String username, String password) {
	// TODO Auto-generated constructor stub
	this.username = username;
	this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean setPassword(String oldPassword, String newPassword) {
	if(oldPassword.equals(password)) {
	    this.password = newPassword;
	    return true;
	}
	return false;
        
    }

    public String getUsername() {
        return username;
    }
    

}
