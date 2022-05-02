/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.afterschoolweb;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.*;
import javax.sql.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ACER
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/portal"})
public class LoginServlet extends HttpServlet {

    String id;
    String password;
    LocalDate dt = LocalDate.now();
    LocalTime tm = LocalTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH mm ss");
    String text = tm.format(formatter);
    LocalTime parsedTime = LocalTime.parse(text, formatter);
    
    

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

        id = request.getParameter("ID");
        password = request.getParameter("password");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
        out.println("<title>After School | Student</title>");
        out.println("<meta name=\"description\" content=\"Core HTML Project\">");
        out.println("<meta name=\"viewpor\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");

        out.println("<link rel=\"stylesheet\" href=\"vendor/bootstrap/bootstrap.min.css\">");
        out.println("<link rel=\"stylesheet\" href=\"vendor/select2/select2.min.css\">");
        out.println("<link rel=\"stylesheet\" href=\"vendor/owlcarousel/owl.carousel.min.css\">");
        out.println("<link rel=\"stylesheet\" href=\"vendor/lightcase/lightcase.css\">");

        out.println("<link href=\"https://fonts.googleapis.com/css?family=Lato:300,400|Work+Sans:300,400,700\" rel=\"stylesheet\">");

        out.println("<link rel=\"stylesheet\" href=\"style.min.css\">");
        out.println("<link rel=\"stylesheet\" href=\"https://cdn.linearicons.com/free/1.0.0/icon-font.min.css\">");
        out.println("<link href=\"https://file.myfontastic.com/7vRKgqrN3iFEnLHuqYhYuL/icons.css\" rel=\"stylesheet\">");

        out.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.js\"></script>");
        out.println("<link rel=\"shortcut icon\" href=\"pictures/logo.png\" />");
        out.println("</head>");

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
                    int i = Integer.parseInt(id);
                    Statement stmt = conn.createStatement();
                    ResultSet r = stmt.executeQuery("SELECT user_fname, user_lname, user_type FROM Account NATURAL JOIN Users WHERE user_id = " + i + " AND user_password = '" + password + "';");
                    if (r.next()) {
                        if (r.getString("user_type").equals("S")) {
                            out.println("<body data-spy=\"scroll\" data-target=\"#navbar-nav-header\" class=\"static-layout\">");
                            out.println("<div class = \"row\">");
                            out.println("<div class = \"col-md-6 col-sm-4\">\n"
                                    + "     <h2 style=\"color: white; font-family:cursive;\"><i>&ensp;After School</i></h2>\n"
                                    + "  </div> ");

                            out.println("<div class = \"col-md-6 col-sm-4\">");
                            out.println("<div class=\"dropdown\">");
                            out.println("<button class=\"dropbtn\"><h4><i>" + r.getString("user_fname") + " " + r.getString("user_lname") + "</i></h4></button>");
                            out.println("<div class=\"dropdown-content\">");
                            out.println("<form method='post' action='./profile'>");
                            out.println("<input type='hidden' placeholder='id' name='ID' value = '" + i + "'>");
                            out.println("<input class=\"nav-element\" type=\"submit\" name=\"student\" value=\"My Profile\" style=\"\n" +
                                    "    color: black;\n" +
                                    "    padding: 12px 16px;\n" +
                                    "    text-decoration: none;\n" +
                                    "    display: block;\n" +
                                    "    background-color: transparent;\n" +
                                    "    border: none; cursor: pointer; line-height: 1.5;\n" +
                                    "    text-align: left;\n" +
                                    "    font-family: 'Lato', sans-serif;\n" +
                                    "    font-weight: 300;\n" +
                                    "    font-size: 1.6rem;\n" +
                                    "    box-sizing: border-box;\n" +
                                    "\">");
                            out.println("</form>");
                            out.println("<a href=\"Loginpage.html\" style=\"color: red;\">Log Out</a>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<div class=\"boxed-page\">");
                            out.println("<section id=\"gtco-who-we-are\" class=\"bg-white\">");
                            out.println("<div class=\"row\">");
                            out.println("<div class=\"col-md-6 col-sm-4 counter-item\">");
                            out.println("<div class = \"BookNowBox\">");
                            out.println("<h1 style=\"color: white\"><br>Learn Without Limits<br>with After School</h1>");
                            out.println("<br><br><br><br><br>");
                            out.println("<h4 style=\"color : white\">Get Started Now!</h4>");
                            out.println("<form method='post' action='BookNow.jsp?studId="+ i +"'>");
                            out.println("<button type=\"submit\" class=\"button\" style=\"vertical-align:middle; color: #1d1d8c\"><span>Book Session</span></button>");
                            out.println("</form>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<div class=\"col-md-6 col-sm-4\">");
                            out.println("<br><h2>Upcoming Sessions. . .</h2>");
                            out.println("<br><br>");
                            Statement stmt2 = conn.createStatement();
                            ResultSet res = stmt2.executeQuery("SELECT * FROM Booking NATURAL JOIN Session WHERE student_id = " + i + " AND ((session_date = '" + dt + "' AND session_start_time > '" + parsedTime + "') OR session_date > '" + dt + "');");
                            out.println("<div class=\"scroll\">");
                            out.println("<h5 style=\"color: #141470\">");
                            int flag = 0;
                            while (res.next()) {
                                String n = res.getString("course_code");
                                String nm = res.getString("room_num");
                                out.println("<img src=\"pictures/arrow.png\" height=\"20\">" + n + " | Room: " + nm + "<br><br>");
                                flag = 1;
                            }
                            if (flag == 0) {
                                out.println("Oups!! You Have no Upcoming Sessions");
                            }
                            out.println("</h5>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</section>");
                            out.println("<section id=\"gtco-counter\" class=\"overlay bg-fixed\">");
                            out.println("<div class=\"container\">");
                            out.println("<div class=\"section-content\">");
                            out.println("<div class=\"row\">");
                            Statement stmt3 = conn.createStatement();
                            ResultSet stats = stmt3.executeQuery("SELECT attended_hours, stud_balance FROM Student WHERE user_id = " + i + ";");
                            Statement stmt4 = conn.createStatement();
                            ResultSet ses = stmt4.executeQuery("SELECT COUNT(session_code) AS cnt FROM Student AS S JOIN Booking AS B ON S.user_id = B.student_id WHERE S.user_id= " + i + ";");
                            Statement stmt5 = conn.createStatement();
                            ResultSet mostses = stmt5.executeQuery("SELECT course_code, count(course_code) AS cnt FROM Student AS S JOIN Booking AS B ON S.user_id = B.student_id NATURAL JOIN SESSION WHERE S.user_id = " + i + "GROUP BY course_code ORDER BY cnt DESC LIMIT 1;");
                            if (stats.next()) {
                                out.println("<div class=\"col-md-3 col-sm-6 counter-item\">");
                                out.println("<span class=\"number\" data-from=\"0\" data-to=\"34\" data-refresh-interval=\"100\">" + stats.getString("attended_hours") + "</span>");
                                out.println("<h4>Attended Hours</h4>");
                                out.println("</div>");
                                out.println("<div class=\"col-md-3 col-sm-6 counter-item\">");
                                out.println("<span class=\"number\" data-from=\"0\" data-to=\"29\" data-refresh-interval=\"100\">" + stats.getString("stud_balance") + "</span>");
                                out.println("<h4>Balance</h4>");
                                out.println("</div>");
                            }
                            if (ses.next()) {
                                out.println("<div class=\"col-md-3 col-sm-6 counter-item\">");
                                out.println("<span class=\"number\" data-from=\"0\" data-to=\"32\" data-refresh-interval=\"100\">" + ses.getString("cnt") + "</span>");
                                out.println("<h4>Booked Sessions</h4>");
                                out.println("</div>");
                            } else {
                                out.println("<div class=\"col-md-3 col-sm-6 counter-item\">");
                                out.println("<span class=\"number\" data-from=\"0\" data-to=\"32\" data-refresh-interval=\"100\">0</span>");
                                out.println("<h4>Booked Sessions</h4>");
                                out.println("</div>");
                            }
                            if (mostses.next()) {
                                out.println("<div class=\"col-md-3 col-sm-6 counter-item\">");
                                out.println("<span class=\"number\" data-from=\"0\" data-to=\"38\" data-refresh-interval=\"100\">" + mostses.getString("course_code") + "</span>");
                                out.println("<h4>Most Booked Course</h4>");
                                out.println("</div>");
                            } else {
                                out.println("<div class=\"col-md-3 col-sm-6 counter-item\">");
                                out.println("<span class=\"number\" data-from=\"0\" data-to=\"38\" data-refresh-interval=\"100\">None</span>");
                                out.println("<h4>Most Booked Course</h4>");
                                out.println("</div>");
                            }
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</section>");
                            out.println("</section>");
                            out.println("</div>");
                        } else if (r.getString("user_type").equals("T")) {
                            out.println(" <!DOCTYPE html>\n"
                                    + "<html lang=\"en\">\n"
                                    + "<head>\n"
                                    + "    <meta charset=\"utf-8\">\n"
                                    + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                                    + "    <title>After School | Professor</title>\n"
                                    + "    <meta name=\"description\" content=\"Core HTML Project\">\n"
                                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"
                                    + "    <link rel=\"stylesheet\" href=\"vendor/bootstrap/bootstrap.min.css\">\n"
                                    + "    <link rel=\"stylesheet\" href=\"vendor/select2/select2.min.css\">\n"
                                    + "    <link rel=\"stylesheet\" href=\"vendor/owlcarousel/owl.carousel.min.css\">\n"
                                    + "    <link rel=\"stylesheet\" href=\"vendor/lightcase/lightcase.css\">\n"
                                    + "    <link href=\"https://fonts.googleapis.com/css?family=Lato:300,400|Work+Sans:300,400,700\" rel=\"stylesheet\">\n"
                                    + "    <link rel=\"stylesheet\" href=\"professor.css\">\n"
                                    + "    <link rel=\"stylesheet\" href=\"https://cdn.linearicons.com/free/1.0.0/icon-font.min.css\">\n"
                                    + "    <link href=\"https://file.myfontastic.com/7vRKgqrN3iFEnLHuqYhYuL/icons.css\" rel=\"stylesheet\">\n"
                                    + "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.js\"></script>\n"
                                    + "</head>\n");
                            out.println("<body data-spy=\"scroll\" data-target=\"#navbar-nav-header\" class=\"static-layout\">");
                            out.println("<div class = \"row\">");
                            out.println("<div class = \"col-md-6 col-sm-4\">\n");
                            out.println("<div class=\"center_name\"><i>After School</i></div>");
                            out.println("</div>");
                            out.println("<div class = \"col-md-6 col-sm-4\">");
                            out.println("<div class=\"dropdown\">");
                            out.println("<button class=\"dropbtn\"><h4><i>" + r.getString("user_fname") + " " + r.getString("user_lname") + "</i></h4></button>");
                            out.println("<div class=\"dropdown-content\">");
                            out.println("<form method='post' action='./profile'>");
                            out.println("<input type='hidden' placeholder='id' name='ID' value = '" + i + "'>");
                            out.println("<input class=\"nav-element\" type=\"submit\" name=\"professor\" value=\"My Profile\" style=\"\n" +
                                    "    color: black;\n" +
                                    "    padding: 12px 16px;\n" +
                                    "    text-decoration: none;\n" +
                                    "    display: block;\n" +
                                    "    background-color: transparent;\n" +
                                    "    border: none; cursor: pointer; line-height: 1.5;\n" +
                                    "    text-align: left;\n" +
                                    "    font-family: 'Lato', sans-serif;\n" +
                                    "    font-weight: 300;\n" +
                                    "    font-size: 1.6rem;\n" +
                                    "    box-sizing: border-box;\n" +
                                    "\">");
                            out.println("</form>");
                            out.println("<form method='post' action='./profcourses'>");
                            out.println("<input type='hidden' placeholder='id' name='ID' value = '" + i + "'>");
                            out.println("<input class=\"nav-element\" type=\"submit\" name=\"professor\" value=\"My Courses\" style=\"\n" +
                                    "    color: black;\n" +
                                    "    padding: 12px 16px;\n" +
                                    "    text-decoration: none;\n" +
                                    "    display: block;\n" +
                                    "    background-color: transparent;\n" +
                                    "    border: none; cursor: pointer; line-height: 1.5;\n" +
                                    "    text-align: left;\n" +
                                    "    font-family: 'Lato', sans-serif;\n" +
                                    "    font-weight: 300;\n" +
                                    "    font-size: 1.6rem;\n" +
                                    "    box-sizing: border-box;\n" +
                                    "\">");
                            out.println("</form>");
                            out.println("<a href=\"Loginpage.html\" style=\"color: red;\">Log Out</a>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<div class=\"boxed-page\">");
                            out.println("<section id=\"gtco-who-we-are\" class=\"bg-white\" style=\"border-top-right-radius: 20px; border-top-left-radius:20px;\">");
                            out.println("<div class=\"row\">");
                            out.println("<div class=\"col-md-6 col-sm-4 counter-item\">");
                            out.println("<div class = \"BookNowBox\">");
                            out.println("<h1 style=\"color: white\"><br>Teach Without Limits<br>with After School</h1>");
                            out.println("<br><br><br><br><br>");
                            out.println("<h4 style=\"color : white\">Get Started Now!</h4>");
                            out.println("<form method='post' action='addViewAvailability.jsp?tutorId="+i+"'>");
                            out.println("<input type='hidden' placeholder='id' name='ID' value = '" + i + "'>");
                            out.println("<button class=\"button\" style=\"vertical-align:middle; color: #1d1d8c\"><span>Add Availability</span></button>");
                            out.println("</form>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<div class=\"col-md-6 col-sm-4\">");
                            out.println("<br><h2>Upcoming Sessions. . .</h2>");
                            out.println("<br><br>");
                            Statement stmt2 = conn.createStatement();
                            ResultSet res = stmt2.executeQuery("SELECT course_code, room_num FROM Session WHERE tutor_id = " + i + " AND ((session_date = '" + dt + "' AND session_start_time > '" + parsedTime + "') OR session_date > '" + dt + "');");
                            out.println("<div class=\"scroll\">\n"
                                    + "            <h5 style=\"color: #141470\">");
                            int flag = 0;
                            while (res.next()) {
                                String n = res.getString("course_code");
                                String nm = res.getString("room_num");
                                out.println("<img src=\"pictures/arrow.png\" height=\"20\">" + n + " | Room: " + nm + "<br><br>");
                                flag = 1;
                            }
                            if (flag == 0) {
                                out.println("Oups!! You Have no Upcoming Sessions");
                            }
                            out.println("</h5>");
                            out.println("</div>");
                            out.println("</div>\n");
                            out.println("</div>\n");
                            out.println("</section>");
                            out.println("<section id=\"gtco-counter\" class=\"overlay bg-fixed\">");
                            out.println("<div class=\"container\">");
                            out.println("<div class=\"section-content\">");
                            out.println("<div class=\"row\">");
                            Statement stmt3 = conn.createStatement();
                            ResultSet stats = stmt3.executeQuery("SELECT tutor_balance FROM Tutor WHERE user_id = " + i + ";");
                            if (stats.next()) {
                                out.println("<div class=\"col-md-6 col-sm-4 counter-item\">");
                                out.println("<span class=\"number\" data-from=\"0\" data-to=\"32\" data-refresh-interval=\"100\">" + stats.getString("tutor_balance") + "</span>");
                                out.println("<h4>Hours Worked</h4>");
                                out.println("</div>");
                            }
                            out.println("<div class=\"col-md-6 col-sm-4 counter-item\">");
                            out.println("<span class=\"number\" data-from=\"0\" data-to=\"29\" data-refresh-interval=\"100\">29</span>");
                            out.println("<h4>Balance</h4>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</section>");
                            out.println("</div>");
                            out.println("</body>");
                            out.println("</html>");
                        }
                        else {
                            out.println("<!DOCTYPE html>\n"
                                    + "<html lang=\"en\">\n"
                                    + "<head>\n"
                                    + "    <meta charset=\"utf-8\">\n"
                                    + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                                    + "    <title>After School | Admin</title>\n"
                                    + "    <meta name=\"description\" content=\"Core HTML Project\">\n"
                                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"
                                    + "    <link rel=\"stylesheet\" href=\"vendor/bootstrap/bootstrap.min.css\">\n"
                                    + "    <link rel=\"stylesheet\" href=\"vendor/select2/select2.min.css\">\n"
                                    + "    <link rel=\"stylesheet\" href=\"vendor/owlcarousel/owl.carousel.min.css\">\n"
                                    + "    <link rel=\"stylesheet\" href=\"vendor/lightcase/lightcase.css\">\n"
                                    + "    <link href=\"https://fonts.googleapis.com/css?family=Lato:300,400|Work+Sans:300,400,700\" rel=\"stylesheet\">\n"
                                    + "    <link rel=\"stylesheet\" href=\"Admin.css\">\n"
                                    + "    <link rel=\"stylesheet\" href=\"https://cdn.linearicons.com/free/1.0.0/icon-font.min.css\">\n"
                                    + "    <link href=\"https://file.myfontastic.com/7vRKgqrN3iFEnLHuqYhYuL/icons.css\" rel=\"stylesheet\">\n"
                                    + "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.js\"></script>\n"
                                    + "</head>");
                            out.println("<body data-spy=\"scroll\" data-target=\"#navbar-nav-header\" class=\"static-layout\">");
                            out.println("<div class = \"row\">");
                            out.println("<div class = \"col-md-6 col-sm-4\">");
                            out.println("<div class=\"center_name\"><i>After School</i></div>");
                            out.println("</div>");
                            out.println("<div class = \"col-md-6 col-sm-4\">");
                            out.println("<div class=\"dropdown\">");
                            out.println("<button class=\"dropbtn\"><h4><i>" + r.getString("user_fname") + " " + r.getString("user_lname") + "</i></h4></button>");
                            out.println("<div class=\"dropdown-content\">");
                            out.println("<form method='post' action='./profile'>");
                            out.println("<input type='hidden' placeholder='id' name='ID' value = '" + i + "'>");
                            out.println("<input class=\"nav-element\" type=\"submit\" name=\"student\" value=\"My Profile\" style=\"\n" +
                                    "    color: black;\n" +
                                    "    padding: 12px 16px;\n" +
                                    "    text-decoration: none;\n" +
                                    "    display: block;\n" +
                                    "    background-color: transparent;\n" +
                                    "    border: none; cursor: pointer; line-height: 1.5;\n" +
                                    "    text-align: left;\n" +
                                    "    font-family: 'Lato', sans-serif;\n" +
                                    "    font-weight: 300;\n" +
                                    "    font-size: 1.6rem;\n" +
                                    "    box-sizing: border-box;\n" +
                                    "\">");
                            out.println("</form>");
                            out.println("<a href=\"Loginpage.html\" style=\"color: red;\">Log Out</a>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<div class=\"boxed-page\">");
                            out.println("<section id=\"gtco-who-we-are\" class=\"bg-white\">");
                            out.println("<div class=\"row\">");
                            out.println("<div class=\"col-md-6 col-sm-4 counter-item\">");
                            out.println("<div class = \"BookNowBox\">");
                            out.println("<h1 style=\"color: white\"><br>Welcome back, ADMIN</h1>");
                            out.println("<br><br><br><br><br>");
                            out.println("<h4 style=\"color : white\">Manage Users!</h4>");
                            out.println("<a href=\"AddStudent.html\">");
                            out.println("<button class=\"button\" style=\"vertical-align:middle;\"><span>Add Student</span></button>");
                            out.println("</a>");
                            out.println("<button class=\"button\" style=\"vertical-align:middle;\"><span>Add Tutor</span></button>");
                            out.println("</div> ");
                            out.println("</div>");
                            out.println("<div class=\"col-md-6 col-sm-4\">");
                            out.println("<br><h2>Current Sessions :</h2>");
                            out.println("<br><br>");
                            out.println("<div class=\"scroll\">");
                            out.println("<h5><u>");
                            Statement stmt2 = conn.createStatement();
                            ResultSet res = stmt2.executeQuery("SELECT course_code, room_num FROM Session WHERE session_date = '" + dt + "' AND session_start_time <= '" + parsedTime + "' AND session_end_time >= '" + parsedTime + "';");
                            int flag = 0;
                            while(res.next()){
                                flag = 1;
                                out.println("<img src=\"pictures/arrow.png\" height=\"20\"><i>" + res.getString("course_code") + " | " + res.getString("room_num") + "</i><br><br>");
                            }
                            if (flag == 0){
                                out.println("There are no Sessions Conducted Currently!");
                            }
                            out.println("</u></h5>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</section>");
                            Statement stmt3 = conn.createStatement();
                            ResultSet tutors = stmt3.executeQuery("SELECT count(user_id) AS cnt FROM Tutor;");
                            Statement stmt4 = conn.createStatement();
                            ResultSet students = stmt4.executeQuery("SELECT count(user_id) AS cnt FROM Student;");
                            out.println("<section id=\"gtco-counter\" class=\"overlay bg-fixed\">");
                            out.println("<div class=\"container\">");
                            out.println("<div class=\"section-content\">");
                            out.println("<div class=\"row\">");
                            out.println("<div class=\"col-md-6 col-sm-4 counter-item\">");
                            if (students.next()){
                                out.println("<span class=\"number\" data-from=\"0\" data-to=\"38\" data-refresh-interval=\"100\">" + students.getString("cnt") + "</span>");
                            }
                            out.println("<h4>Students Number</h4>");
                            out.println("</div>");
                            out.println("<div class=\"col-md-6 col-sm-4 counter-item\">");
                            
                            if (tutors.next()){
                                out.println("<span class=\"number\" data-from=\"0\" data-to=\"29\" data-refresh-interval=\"100\">"  + tutors.getString("cnt") + "</span>");
                            }
                            out.println("<h4>Tutors Number</h4>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</section>");
                            out.println("</div>");
                            out.println("</body>");
                            out.println("</html>");
                        }
                    } else {
                        out.println("<p> NO SUCH USER </p>");
                        out.println("<a href=\"Loginpage.html\"> Go back to login page </a></div>");
                    }
                } else {
                    out.println("Error: conn is null ");
                }
            }
        } catch (SQLException ex) {
            out.println("SQLException: " + ex);
        } catch (Exception e) {
//e.printStackTrace();
            out.println("Exception caught");
            out.println(e.toString());
        }
        out.println("</body>");
        out.println("</html>");
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
