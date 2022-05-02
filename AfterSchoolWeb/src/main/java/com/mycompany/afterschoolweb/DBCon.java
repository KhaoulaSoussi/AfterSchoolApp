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
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class DBCon {
    private static Connection con;

    public static Connection getCon() {
        try {
            Context ctx = new InitialContext();
            if (ctx == null) {
                throw new Exception("Error - No Context");
            }
            // /jdbc/postgres is the name of the resource in context.xml
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/postgres");
            if (ds != null) {
                con = ds.getConnection();
            }
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return con;
    }
}
