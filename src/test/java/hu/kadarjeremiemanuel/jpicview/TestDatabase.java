package hu.kadarjeremiemanuel.jpicview;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class TestDatabase {
	@Test
	void testkDbConnection() {
        Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName("org.sqlite.JDBC");
        	
        	// Get DB from Classpath
            // https://github.com/xerial/sqlite-jdbc/blob/master/Usage.md#reading-database-files-in-classpaths-or-network-read-only
            String url = "jdbc:sqlite::resource:users.db";
            
            // Connect
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            
            // Testquery, it'll fail if there is no database or table.
            Statement stmt = conn.createStatement();
            
            stmt.execute("SELECT * from user;");
            System.out.println("User table checked!");
            
            stmt.execute("SELECT * from role;");
            System.out.println("Roles table checked!");
            
        } catch (SQLException e) {
            fail(e.getMessage());
        } catch (ClassNotFoundException e1) {
			fail(e1.getMessage());
		} finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                fail(ex.getMessage());
            }
        }
    }
}
