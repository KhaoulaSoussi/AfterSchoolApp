package com.mycompany.afterschoolweb;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Khaoula Ait Soussi
 */
public class Course {
    private String courseCode;
    private String courseTitle;
    private String courseLevel;
    private int pricePerHour;
    
    public Course(){
        
    }
    public Course(String courseLevel,String courseCode){
        this.courseLevel = courseLevel;
        this.courseCode = courseCode;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public String getCourseTitle() {
        return courseTitle;
    }
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
    public String getCourseLevel() {
        return courseLevel;
    }
    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }
    public int getPricePerHour() {
        return pricePerHour;
    }
    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
