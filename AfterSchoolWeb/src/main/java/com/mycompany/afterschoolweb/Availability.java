/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.afterschoolweb;
import java.sql.*;

/**
 *
 * @author Khaoula Ait Soussi
 */
public class Availability {
    private int avCode;
    private java.sql.Date av_date;
    private String av_start_time;
    private String av_end_time;

    public int getAvCode() {
        return avCode;
    }

    public void setAvCode(int avCode) {
        this.avCode = avCode;
        
    }

    public java.sql.Date getAv_date() {
        return av_date;
    }

    public void setAv_date(java.sql.Date av_date) {
        this.av_date = av_date;
    }

    public String getAv_start_time() {
        return av_start_time;
    }

    public void setAv_start_time(String av_start_time) {
        this.av_start_time = av_start_time;
    }

    public String getAv_end_time() {
        return av_end_time;
    }

    public void setAv_end_time(String av_end_time) {
        this.av_end_time = av_end_time;
    }   
}
