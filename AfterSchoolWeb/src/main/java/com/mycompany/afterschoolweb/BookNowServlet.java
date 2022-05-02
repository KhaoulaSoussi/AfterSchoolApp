/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.afterschoolweb;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Khaoula Ait Soussi
 */
@WebServlet(name = "BookNowServlet", urlPatterns = {"/BookNowServlet"})
public class BookNowServlet extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        try (PrintWriter out = response.getWriter()) {
            Connection con = DBCon.getCon();
            DataAccess dao = new DataAccess(con);
            String op = request.getParameter("operation");
            if(op.equals("course")) {
                String level = request.getParameter("id");
                List<Course> cList = dao.getCourseByLevel(level);
                Gson json = new Gson();
                String courseList = json.toJson(cList);
                response.setContentType("text/html");
                response.getWriter().write(courseList);
            }
            else if (op.equals("tutor")) {
                String course = request.getParameter("id");
                List<Tutor> tList = dao.getTutorByCourse(course);
                Gson json = new Gson();
                String tutorList = json.toJson(tList);
                response.setContentType("text/html");
                response.getWriter().write(tutorList);
            }
            else if (op.equals("availability")) {
                String tutor = request.getParameter("id1");
                int type = Integer.parseInt(request.getParameter("id2"));
                java.sql.Date date = java.sql.Date.valueOf(request.getParameter("id3"));
                List<Availability> aList = dao.getAvailabiltiyByTutorAndType(tutor, type, date);
                Gson json = new Gson();
                String availabilityList = json.toJson(aList);
                response.setContentType("text/html");
                response.getWriter().write(availabilityList);
            }
            else if(op.equals("confirm")){
                String course = request.getParameter("id1");
                String date = request.getParameter("id4");
                int type = Integer.parseInt(request.getParameter("id2"));
                String tutor = request.getParameter("id3");
                String time = request.getParameter("id5");
                
                String start_time = time.substring(6,14);
                String end_time = time.substring(19);
                System.out.println(start_time + " " + end_time);
                String session_code = "ses";
                int tutorId = 0;
                String coursecode = "crs";
                int seats = 14; //Assuming the session is a group session (values will change if it's indiv)
                String status = "Open";
                int studId = Integer.parseInt(request.getParameter("id6"));
                try{
                    Statement stmt = con.createStatement();
                    //Get tutor ID
                    ResultSet tut = stmt.executeQuery("SELECT user_id FROM Tutor WHERE CONCAT(user_fname, ' ' , user_lname) = '" + tutor+ "';");
                    if(tut.next()){
                        tutorId = Integer.parseInt(tut.getString("user_id"));
                    }
                    ResultSet crs = stmt.executeQuery("SELECT course_code FROM Course WHERE course_title = '" + course + "';");
                    if(crs.next()){
                        coursecode = crs.getString("course_code");
                    }
                    //Checking if the session already exists in the Sessions table
                    ResultSet sessions = stmt.executeQuery("SELECT session_code FROM Session WHERE session_date = '" + date + "' AND session_start_time = '" + start_time +  "' AND session_end_time = '" + end_time +  "' AND tutor_id = "+tutorId+";");
                    System.out.println(sessions);
                    if(sessions.next()){
                        System.out.println("HERE");
                        session_code = sessions.getString("session_code");
                        stmt.executeUpdate("Update Session SET session_seats_av = session_seats_av - 1 WHERE session_code = '"+session_code+"' ;");
                        
                        stmt.executeUpdate("INSERT INTO booking(student_id, session_code) VALUES (" +studId+ ",'"+ session_code +"');");
                    }
                    else{
                        System.out.println("HEEEERE");
                        ResultSet sessionsCount = stmt.executeQuery("SELECT count(*) AS num FROM Session;");
                        if(sessionsCount.next()){
                            int sessionNumber = sessionsCount.getInt("num") + 1;
                            session_code = "ses"+sessionNumber;
                        }
                        if(type == 2){ //Individual Session
                            seats = 0;
                            status = "Closed";
                        }
                        System.out.println("HEREEEE");
                        stmt.executeUpdate("INSERT INTO Session(session_code, session_date, session_start_time, session_end_time, session_seats_av, session_status, course_code, tutor_id) VALUES\n" +
                                        "('" + session_code + "', '" + date + "', '"+start_time+"', '"+end_time+"', " + seats + ", '"+status+"', '"+coursecode+"', " + tutorId + ");");
                        System.out.println("HEREIIII");
                        stmt.executeUpdate("INSERT INTO booking(student_id, session_code) VALUES (" +studId+ ",'"+ session_code +"');");
                                               
                    }
                    
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
