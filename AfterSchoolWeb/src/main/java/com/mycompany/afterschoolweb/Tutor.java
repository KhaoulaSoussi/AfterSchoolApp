/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.afterschoolweb;

/**
 *
 * @author Khaoula Ait Soussi
 */
public class Tutor {
    private int tutorId; 
    private int tutorBalance;
    private String tutorLevel;
    private String tutorFname;
    private String tutorLname;
    
    public Tutor(){
    }
    
    public Tutor(int tutorId){
        this.tutorId = tutorId;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public int getTutorBalance() {
        return tutorBalance;
    }

    public void setTutorBalance(int tutorBalance) {
        this.tutorBalance = tutorBalance;
    }

    public String getTutorLevel() {
        return tutorLevel;
    }

    public void setTutorLevel(String tutorLevel) {
        this.tutorLevel = tutorLevel;
    }

    public String getTutorFname() {
        return tutorFname;
    }

    public void setTutorFname(String tutorFname) {
        this.tutorFname = tutorFname;
    }

    public String getTutorLname() {
        return tutorLname;
    }

    public void setTutorLname(String tutorLname) {
        this.tutorLname = tutorLname;
    }
    
    
}
