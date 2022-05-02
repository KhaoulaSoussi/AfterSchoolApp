/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.afterschoolweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author ACER
 */
@WebServlet(name = "Profile", urlPatterns = {"/profile"})
public class Profile extends HttpServlet {

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

        int i = Integer.parseInt(request.getParameter("ID"));
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            Context ctx = new InitialContext();
            if (ctx == null) {
                throw new Exception("Error - No Context");
            }
            // /jdbc/postgres is the name of the resource in context.xml
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/postgres");
            if (ds != null) {
                Connection conn = ds.getConnection();
                if (conn != null) {
                    Statement stmt = conn.createStatement();
                    ResultSet r = stmt.executeQuery("SELECT user_fname, user_lname, user_phonenum, user_dob, user_type FROM Users WHERE user_id = " + i + ";");
                    if (r.next()) {
                        out.println("<!DOCTYPE html>\n"
                                + "<html>\n"
                                + "<head>\n"
                                + "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n"
                                + "<title>Profile Details</title>\n"
                                + "<style>\n"
                                + "* {\n"
                                + "  box-sizing: border-box;\n"
                                + "}\n"
                                + "body {\n"
                                + " background-color: #cccccc;\n"
                                + " background-size: cover;\n"
                                + " background-image: linear-gradient(rgba(0, 0, 0, 0.527),rgba(0, 0, 0, 0.5)) , url(pictures/homebackground.jpg);\n"
                                + "}\n"
                                + ".card {\n"
                                + "  background-color: #F5EFFF;\n"
                                + "  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);\n"
                                + "  width : 50%;\n"
                                + "  margin: auto;\n"
                                + "  text-align: center;\n"
                                + "  font-family: arial;\n"
                                + "  margin-top: 6%;\n"
                                + "  border-radius: 10px;\n"
                                + "  padding-bottom: 50px;\n"
                                + "  padding-top: 30px;\n"
                                + "}\n"
                                + ".title {\n"
                                + "  color: grey;\n"
                                + "  font-size: 18px;\n"
                                + "}\n"
                                + ".column {\n"
                                + "  float: left;\n"
                                + "  width: 50%;\n"
                                + "  padding: 10px;\n"
                                + "  height: 300px;\n"
                                + "}\n"
                                + ".row:after {\n"
                                + "  content: \"\";\n"
                                + "  display: table;\n"
                                + "  clear: both;\n"
                                + "}\n"
                                + "</style>\n"
                                + "<link rel=\"shortcut icon\" href=\"pictures/logo.png\" />\n"
                                + "</head>\n");
                        out.println("<body>\n"
                                + "<div class=\"card\">\n"
                                + "  <h1 class=\"header\">Profile Details</h1>\n"
                                + "  <br><br>  \n"
                                + "        <div class=\"title\"><i><u>Name</u>: " + r.getString("user_fname") + " " + r.getString("user_lname") + "</i></div><br>\n"
                                + "        <div class=\"title\"><i><u>Phone Number</u>: " + r.getString("user_phonenum") + "</i></div><br>\n"
                                + "        <div class=\"title\"><i><u>Date of Birth</u>: " + r.getString("user_dob") + "</i></div>\n"
                                + "  <br>\n");
                        if (r.getString("user_type").equals("S")) {
                            Statement stmt2 = conn.createStatement();
                            ResultSet res = stmt2.executeQuery("SELECT academic_level, stud_balance FROM Student WHERE user_id = " + i + ";");
                            if (res.next()) {
                                out.println("<div class=\"title\"><i><u>Academic Level</u>: " + res.getString("academic_level") + "</i></div><br>\n"
                                        + "  <div class=\"title\"><i><u>Balance</u>: " + res.getString("stud_balance") + " MAD</i> </div> <br>\n");
                            }
                        } else if (r.getString("user_type").equals("T")) {
                            Statement stmt3 = conn.createStatement();
                            ResultSet resu = stmt3.executeQuery("SELECT tutor_balance FROM Tutor WHERE user_id = " + i + ";");
                            if (resu.next()) {
                                out.println("<div class=\"title\"><i><u>Balance</u>: " + resu.getString("tutor_balance") + " MAD</i></div><br>");
                            }
                        }
                        else if (r.getString("user_type").equals("A")) {
                            Statement stmt3 = conn.createStatement();
                            ResultSet resu = stmt3.executeQuery("SELECT admin_role FROM Admin WHERE user_id = " + i + ";");
                            if (resu.next()) {
                                String admin_role = resu.getString("admin_role");
                                if (admin_role.equals("O")){
                                    out.println("<div class=\"title\"><i><u>Admin Role</u>: Owner</i></div><br>");
                                }
                                else if (admin_role.equals("S")){
                                    out.println("<div class=\"title\"><i><u>Admin Role</u>: Secretary</i></div><br>");
                                }
                                else{
                                    out.println("<div class=\"title\"><i><u>Admin Role</u>: Manager</i></div><br>");
                                }
                            }
                        }
                        out.println("</div>");
                        out.println("</body>");
                        out.println("</html>");
                    }
                } else {
                    out.println("Error: conn is null ");
                }
            }
        } catch (SQLException ex) {
            out.println("SQLException: " + ex);
        } catch (Exception e) {
            out.println("Exception caught");
            out.println(e.toString());
        }
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
