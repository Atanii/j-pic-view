/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.auth.RolesAndPermissions;
import hu.kadarjeremiemanuel.jpicview.bean.RoleModel;
import hu.kadarjeremiemanuel.jpicview.utils.SharedValues;

/**
 * @author atanii
 *
 */
public final class DatabaseHandler {
	
	private static final boolean adminCheck(AuthManager am) {
		if (!am.checkRole(RolesAndPermissions.ADMIN) || !am.checkPermission("*")) {
			JOptionPane.showMessageDialog(
					null,
					"Illegal access! You haven't got the required permission to perform this action!",
					"Security Error",
					JOptionPane.ERROR_MESSAGE
			);
			return false;
		}
		return true;
	}
	
	public static final RoleModel[] getRoles(AuthManager am) {
		if (!adminCheck(am)) { return null; }
		var roles = new RoleModel[0];
		Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(SharedValues.JDBC_CLASSPATH);
        	// Get DB
            var url = SharedValues.DBPATH;
            // Connect
            conn = DriverManager.getConnection(url);
            
            // GetUserRoles
            var stmt = conn.createStatement();
            stmt.execute("SELECT count(*) from role WHERE rolename != \"admin\";");
            var res = stmt.getResultSet();
            roles = new RoleModel[res.getInt(1)];
            
            // 	GetUserRoles
            stmt = conn.createStatement();
            stmt.execute("SELECT * from role WHERE rolename != \"admin\";");
            res = stmt.getResultSet();
            
            // Get Data
            int i = 0;
            while (res.next()) {
            	roles[i] = new RoleModel(
            			res.getInt(1),
            			res.getString(2),
            			res.getString(3),
            			res.getString(4),
            			res.getString(5)
    			);
            	i++;
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
        return roles;
	}
	
	public static final Object[][] getUserRoleMatrix(AuthManager am) {
		if (!adminCheck(am)) { return null; }
		var dt = new Object[0][0];
		Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(SharedValues.JDBC_CLASSPATH);
        	// Get DB
            var url = SharedValues.DBPATH;
            // Connect
            conn = DriverManager.getConnection(url);
            
            // Get Roles
            var stmt = conn.createStatement();
            
            stmt.execute("SELECT count(*) from user_role_matrix;");
            var res = stmt.getResultSet();
            dt = new Object[res.getInt(1)][3];
            
            stmt.execute("SELECT * from user_role_matrix;");
            res = stmt.getResultSet();
            
            // Get Data
            int i = 0;
            while (res.next()) {
            	// System.out.println(res.getInt(1) + "; " + res.getString(2) + "; " + res.getString(3));
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
	
	public static final boolean deleteUser(String username, AuthManager am) {
		if (!adminCheck(am)) { return false; }
		Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(SharedValues.JDBC_CLASSPATH);
        	// Get DB
            var url = SharedValues.DBPATH;
            // Connect
            conn = DriverManager.getConnection(url);
            
            // Delete user
            var stmt = conn.prepareStatement("DELETE FROM user WHERE username = ? AND username != \"admin\" AND username != \"guest\";");
            stmt.setString(1, username);
            stmt.execute();
            if (stmt.getUpdateCount() != 0) {
            	return true;
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
        return false;
	}
	
	public static final boolean addUser(String username, String plaintextPassword, String rolename, AuthManager am) {
		if (rolename.equals(RolesAndPermissions.ADMIN.getRoleName())) { 
			JOptionPane.showMessageDialog(
					null,
					"Only one administrator is permitted, you cannot create one more!",
					"Security Error",
					JOptionPane.ERROR_MESSAGE
			);
			return false;
		}
		Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(SharedValues.JDBC_CLASSPATH);
        	// Get DB
            var url = SharedValues.DBPATH;
            // Connect
            conn = DriverManager.getConnection(url);
            
            // Insert new user into user table
            var stmt = conn.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?);");
            stmt.setString(1, username);
            stmt.setString(2,  plaintextPassword);
            stmt.execute();
            if (stmt.getUpdateCount() == 0) {
            	return false;
            }
            // Insert user into user_role
            stmt = conn.prepareStatement("INSERT INTO user_role (username, rolename) VALUES (?, ?);");
            stmt.setString(1, username);
            stmt.setString(2,  rolename);
            stmt.execute();
            if (stmt.getUpdateCount() == 0) {
            	return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (ClassNotFoundException e1) {
			e1.printStackTrace();
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
	
	public static final boolean updateUser(String username, String newUsername, String rolename, String newRolename, AuthManager am) {
		if (rolename.equals(RolesAndPermissions.ADMIN.getRoleName())) { 
			JOptionPane.showMessageDialog(
					null,
					"Only one administrator is permitted, you cannot create one more!",
					"Security Error",
					JOptionPane.ERROR_MESSAGE
			);
			return false;
		}
		Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(SharedValues.JDBC_CLASSPATH);
        	// Get DB
            var url = SharedValues.DBPATH;
            // Connect
            conn = DriverManager.getConnection(url);
            
            // Update username
            var stmt = conn.prepareStatement(
            		"UPDATE user SET username = ? WHERE username = ? AND username != \"admin\" AND username != \"guest\";"
    		);
            stmt.setString(1, newUsername);
            stmt.setString(2,  username);
            stmt.execute();
            if (stmt.getUpdateCount() == 0) {
            	return false;
            }
            
            // 	Update username
            stmt = conn.prepareStatement(
            		"UPDATE user_role SET rolename = ? WHERE username = ? AND username != \"admin\" AND username != \"guest\" AND rolename != \"admin\" AND rolename != \"guest\";"
    		);
            stmt.setString(1, newRolename);
            stmt.setString(2,  username);
            stmt.execute();
            if (stmt.getUpdateCount() == 0) {
            	return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (ClassNotFoundException e1) {
			e1.printStackTrace();
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
	
	public static final boolean checkDbConnection() {
        Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(SharedValues.JDBC_CLASSPATH);
        	
        	// Get DB
            var url = SharedValues.DBPATH;
            
            // Connect
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            
            // Testquery, it'll fail if there is no database or table.
            var stmt = conn.createStatement();
            
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
