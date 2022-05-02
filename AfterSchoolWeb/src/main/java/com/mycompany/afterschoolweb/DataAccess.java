/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.afterschoolweb;

/**
 *
 * @author Khaoula Ait Soussi
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DataAccess {
    Connection con;
    PreparedStatement pst;
    String query;
    ResultSet rs;
    
    public DataAccess(Connection con) {
        this.con = con;
    }
    
    public List<Course> getCourseByLevel(String level){
        List<Course> list = new ArrayList<>();
        try{
            query = "select * from course where course_level=?";
            pst = this.con.prepareStatement(query);
            pst.setString(1, level);
            rs = pst.executeQuery();
            while(rs.next()){
                Course course = new Course();
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseLevel(rs.getString("course_level"));
                course.setCourseTitle(rs.getString("course_title"));
                course.setPricePerHour(rs.getInt("course_price_hr"));
                list.add(course);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<Tutor> getTutorByCourse(String course_title){
        List<Tutor> list = new ArrayList<>();
        try{
            query = "select * from tutor where user_id IN (select tutor_ID from offering natural join course where course_title = ?)";
            pst = this.con.prepareStatement(query);
            pst.setString(1, course_title);
            rs = pst.executeQuery();
            while(rs.next()){
                Tutor tutor = new Tutor();
                tutor.setTutorId(rs.getInt("user_id"));
                tutor.setTutorLevel(rs.getString("tutor_level"));
                tutor.setTutorFname(rs.getString("user_fname"));
                tutor.setTutorLname(rs.getString("user_lname"));
                list.add(tutor);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Availability> getAvailabiltiyByTutorAndType(String tutorName, int type, java.sql.Date date){
        List<Availability> list = new ArrayList<>();
        try{
            if(type == 1){  //Group Session
                /*query = "SELECT DISTINCT av_start_time, av_end_time, av_date FROM availability\n" +
                        "JOIN Tutor ON tutor_id = user_id where CONCAT(user_fname, ' ' , user_lname) = ? AND av_date = ?\n" +
                        "EXCEPT\n" +
                        "SELECT session_start_time, session_end_time, session_date FROM Session "
                        + "Where session_status = 'Closed' " +
                        " UNION\n" +
                        "SELECT session_start_time, session_end_time, session_date FROM Session "
                        + "JOIN Tutor ON tutor_id = user_id where CONCAT(user_fname, ' ' , user_lname) = ? AND session_date = ?\n"
                        + "AND session_status = 'Open';";*/
                query = "SELECT av_start_time, av_end_time, av_date FROM availability "
                        + "JOIN Tutor ON tutor_id = user_id where CONCAT(user_fname, ' ' , user_lname) = ? AND (av_status = 'Open' OR av_status = 'Unbooked') AND av_date = ?;";
                pst = this.con.prepareStatement(query);
                pst.setString(1, tutorName);
                pst.setDate(2, date);
                rs = pst.executeQuery();
                while(rs.next()){
                    Availability av = new Availability();
                    av.setAv_date(rs.getDate("av_date"));
                    av.setAv_start_time(rs.getTime("av_start_time").toString());
                    av.setAv_end_time(rs.getTime("av_end_time").toString());
                    list.add(av);
                }
            }
            else if(type == 2){  //Individual Session
                /*query = "SELECT DISTINCT av_date, av_start_time, av_end_time FROM availability JOIN Tutor ON tutor_id = user_id WHERE CONCAT(user_fname, ' ' , user_lname) = ? AND av_date = ?" +
                        "EXCEPT SELECT session_date, session_start_time, session_end_time FROM SESSION;";*/
                query = "SELECT DISTINCT av_date, av_start_time, av_end_time FROM availability JOIN Tutor ON tutor_id = user_id WHERE CONCAT(user_fname, ' ' , user_lname) = ? AND av_date = ?" +
                        "AND av_status = 'Unbooked';";
                pst = this.con.prepareStatement(query);
                pst.setString(1, tutorName);
                pst.setDate(2, date);
                rs = pst.executeQuery();
                while(rs.next()){
                    Availability av = new Availability();
                    av.setAv_date(rs.getDate("av_date"));
                    av.setAv_start_time(rs.getTime("av_start_time").toString());
                    av.setAv_end_time(rs.getTime("av_end_time").toString());
                    list.add(av);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Availability> getAvailability(int tutorId){
        List<Availability> list = new ArrayList<>();
        try{
            LocalDate dt = LocalDate.now();
            Statement stmt = this.con.createStatement();
            rs = stmt.executeQuery("SELECT av_date, av_start_time, av_end_time FROM Availability AS A JOIN Tutor AS T ON T.user_id = A.tutor_id WHERE av_date > '" + dt + "' AND A.tutor_id = " + tutorId + " ORDER BY av_date ASC;");
            while(rs.next()){
                Availability av = new Availability();
                av.setAv_date(rs.getDate("av_date"));
                av.setAv_start_time(rs.getTime("av_start_time").toString());
                av.setAv_end_time(rs.getTime("av_end_time").toString());
                list.add(av);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}

  