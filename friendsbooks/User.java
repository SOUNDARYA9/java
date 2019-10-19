/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friendsbooks;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author soundaryalanka
 */
public class User {
     private int id;
    private String firstName;
    private String lastName;
    private Date dob;
    private char gender;
    private String school;
    private String useraccountId;
    private String password;
    private java.sql.Timestamp lastUpdated;
    private java.sql.Timestamp lastNotiChecked;

    public User() {
    }
    
    public User(String useraccountId, String password) {
        this.useraccountId = useraccountId;
        this.password = password;
    }

    public User(String firstName, String lastName, Date dob, char gender, String school, String useraccountId, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.school = school;
        this.useraccountId = useraccountId;
        this.password = password;
    }

    public Timestamp getLastNotiChecked() {
        return lastNotiChecked;
    }

    public void setLastNotiChecked(Timestamp lastNotiChecked) {
        this.lastNotiChecked = lastNotiChecked;
    }
    
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUserAccountId() {
        return useraccountId;
    }

    public void setUserAccountId(String useraccountId) {
        this.useraccountId = useraccountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
