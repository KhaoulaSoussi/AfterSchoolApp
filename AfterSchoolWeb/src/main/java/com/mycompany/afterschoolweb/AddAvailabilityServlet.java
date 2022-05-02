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
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Khaoula Ait Soussi
 */
@WebServlet(name = "AddAvailabilityServlet", urlPatterns = {"/AddAvailabilityServlet"})
public class AddAvailabilityServlet extends HttpServlet {

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
            if(op.equals("displayAv")) {
                int tutorId = Integer.parseInt(request.getParameter("id"));
                List<Availability> avList = dao.getAvailability(tutorId);
                Gson json = new Gson();
                String courseList = json.toJson(avList);
                response.setContentType("text/html");
                response.getWriter().write(courseList);
            }
            else if(op.equals("addAv")) {
                int av_code = 1;
                String date = request.getParameter("d");
                int tutorId = Integer.parseInt(request.getParameter("id"));
                String start_time = request.getParameter("st");
                String end_time = request.getParameter("et");
                Statement stmt = con.createStatement();
                ResultSet sessionsCount = stmt.executeQuery("SELECT count(*) AS num FROM Availability;");
                if(sessionsCount.next()){
                    av_code = sessionsCount.getInt("num") + 1;
                }
                stmt.executeUpdate("INSERT INTO Availability(av_code, av_date, av_start_time, av_end_time, tutor_id) VALUES (" +av_code+ ",'"+ date +"', '" + start_time +"', '" + end_time + "'," + tutorId + ");");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddAvailabilityServlet.class.getName()).log(Level.SEVERE, null, ex);
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
