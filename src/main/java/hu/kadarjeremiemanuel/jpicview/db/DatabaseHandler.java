/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hazelcast.map.impl.query.Result;

import hu.kadarjeremiemanuel.jpicview.utils.SharedValues;

/**
 * @author atanii
 *
 */
public final class DatabaseHandler {
	public static final Object[][] getUserRoleMatrix() {
		Object[][] dt = new Object[0][0];
		Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(SharedValues.JDBC_CLASSPATH);
        	// Get DB
            String url = SharedValues.DBPATH;
            // Connect
            conn = DriverManager.getConnection(url);
            
            // GetUserRoles
            Statement stmt = conn.createStatement();
            
            stmt.execute("SELECT count(*) from user_role_matrix;");
            ResultSet res = stmt.getResultSet();
            dt = new Object[res.getInt(1)][3];
            
            stmt.execute("SELECT * from user_role_matrix;");
            res = stmt.getResultSet();
            
            // Get Data
            int i = 0;
            while (res.next()) {
            	System.out.println(res.getInt(1) + "; " + res.getString(2) + "; " + res.getString(3));
            	dt[i][0] = res.getString(1);
            	dt[i][1] = res.getString(2);
            	dt[i++][2] = res.getString(3);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return dt;
	}
	
	public static final boolean checkDbConnection() {
        Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(SharedValues.JDBC_CLASSPATH);
        	
        	// Get DB
            String url = SharedValues.DBPATH;
            
            // Connect
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            
            // Testquery, it'll fail if there is no database or table.
            Statement stmt = conn.createStatement();
            
            stmt.execute("SELECT * from user;");
            System.out.println("User table checked!");
            
            stmt.execute("SELECT * from role;");
            System.out.println("Role table checked!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        }
        return true;
    }
}
