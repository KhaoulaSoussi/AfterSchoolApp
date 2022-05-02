/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.afterschoolweb;

/**
 *
 * @author Khaoula Ait Soussi
 */
public class Offering {
    private int tutorId; 
    private String courseCode;
    
    public Offering(int tutorId, String courseCode){
        this.tutorId = tutorId;
        this.courseCode = courseCode;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    
}
