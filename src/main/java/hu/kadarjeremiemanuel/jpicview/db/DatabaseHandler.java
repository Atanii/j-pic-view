/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import org.apache.shiro.crypto.hash.Sha512Hash;

import hu.kadarjeremiemanuel.jpicview.db.bean.RoleModel;
import hu.kadarjeremiemanuel.jpicview.db.bean.UserModel;
import hu.kadarjeremiemanuel.jpicview.utils.JpicConstants;

/**
 * @author atanii
 *
 */
public final class DatabaseHandler {
	
	private DatabaseHandler() {}
	
	private static final String SELECT_USER =
			"SELECT username, rolename from user_role WHERE username = ?;";
	
	private static final String GET_ROLE_COUNT =
			"SELECT count(*) from role WHERE rolename != \"admin\";";
	private static final String GET_ROLES =
			"SELECT * from role WHERE rolename != \"admin\";";
	
	private static final String GET_USER_ROLE_MATRIX_COUNT =
			"SELECT count(*) from user_role_matrix;";
	private static final String GET_USER_ROLE_MATRIX =
			"SELECT * from user_role_matrix;";
	
	private static final String DELETE_USER =
			"DELETE FROM user WHERE username = ? AND username != \"admin\" AND username != \"guest\";";
	
	private static final String GET_USER_COUNT_WITH_NAME =
			"SELECT count(*) from user where username = ?;";
	
	private static final String INSERT_NEW_USER =
			"INSERT INTO user (username, password) VALUES (?, ?);";
	private static final String INSERT_INTO_USER_ROLE =
			"INSERT INTO user_role (username, rolename) VALUES (?, ?);";
	
	private static final String UPDATE_USERNAME =
			"UPDATE user SET username = ? WHERE username = ? AND username != \"admin\" AND username != \"guest\";";
	private static final String UPDATE_USER_ROLE =
			"UPDATE user_role SET username = ?, rolename = ? WHERE username = ? AND username != \"admin\" AND username != \"guest\" AND rolename != \"admin\" AND rolename != \"guest\";";
	
	public static final UserModel getUserForEdit(String username) {
		UserModel userToEdit = null;
		Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(JpicConstants.JDBC_CLASSPATH);
        	// Get DB
            var url = JpicConstants.DBPATH;
            // Connect
            conn = DriverManager.getConnection(url);
            
            // GetUserRoles
            var stmt = conn.prepareStatement(SELECT_USER);
            stmt.setString(1, username);
            stmt.execute();
            var res = stmt.getResultSet();
            
            while (res.next()) {
            	userToEdit = new UserModel(
            			res.getString(1),
            			res.getString(2)
    			);
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
        return userToEdit;
	}
	
	public static final RoleModel[] getRoles() {
		var roles = new RoleModel[0];
		Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(JpicConstants.JDBC_CLASSPATH);
        	// Get DB
            var url = JpicConstants.DBPATH;
            // Connect
            conn = DriverManager.getConnection(url);
            
            // GetUserRoles
            var stmt = conn.createStatement();
            stmt.execute(GET_ROLE_COUNT);
            var res = stmt.getResultSet();
            roles = new RoleModel[res.getInt(1)];
            
            // 	GetUserRoles
            stmt = conn.createStatement();
            stmt.execute(GET_ROLES);
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
	
	public static final Object[][] getUserRoleMatrix() {
		var dt = new Object[0][0];
		Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(JpicConstants.JDBC_CLASSPATH);
        	// Get DB
            var url = JpicConstants.DBPATH;
            // Connect
            conn = DriverManager.getConnection(url);
            
            // Get Roles
            var stmt = conn.createStatement();
            
            stmt.execute(GET_USER_ROLE_MATRIX_COUNT);
            var res = stmt.getResultSet();
            dt = new Object[res.getInt(1)][3];
            
            stmt.execute(GET_USER_ROLE_MATRIX);
            res = stmt.getResultSet();
            
            // Get Data
            int i = 0;
            while (res.next()) {
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
	
	public static final boolean deleteUser(String username) {
		Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(JpicConstants.JDBC_CLASSPATH);
        	// Get DB
            var url = JpicConstants.DBPATH;
            // Connect
            conn = DriverManager.getConnection(url);
            
            // Delete user
            var stmt = conn.prepareStatement(DELETE_USER);
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
	
	public static final boolean addUser(String username, String plainTextPassword, String rolename) {
		Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(JpicConstants.JDBC_CLASSPATH);
        	// Get DB
            var url = JpicConstants.DBPATH;
            // Connect
            conn = DriverManager.getConnection(url);
            
         // Get Roles
            var checkStmt = conn.prepareStatement(GET_USER_COUNT_WITH_NAME);
            checkStmt.execute();
            if (checkStmt.getResultSet().getInt(1) != 0) {
            	JOptionPane.showMessageDialog(
    					null,
    					"Username is not unique!",
    					"Registration Error",
    					JOptionPane.ERROR_MESSAGE
    			);
            	return false;
            }
            
            // Insert new user into user table
            var stmt = conn.prepareStatement(INSERT_NEW_USER);
            stmt.setString(1, username);
            stmt.setString(2, (new Sha512Hash(plainTextPassword)).toString());
            stmt.execute();
            if (stmt.getUpdateCount() == 0) {
            	return false;
            }
            // Insert user into user_role
            stmt = conn.prepareStatement(INSERT_INTO_USER_ROLE);
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
	
	public static final boolean updateUser(String username, String newUsername, String rolename, String newRolename) {
		Connection conn = null;
        try {
        	// Get JDBC
        	Class.forName(JpicConstants.JDBC_CLASSPATH);
        	// Get DB
            var url = JpicConstants.DBPATH;
            // Connect
            conn = DriverManager.getConnection(url);
            
            // Update username
            var stmt = conn.prepareStatement(
            		UPDATE_USERNAME
    		);
            stmt.setString(1, newUsername);
            stmt.setString(2,  username);
            stmt.execute();
            if (stmt.getUpdateCount() == 0) {
            	return false;
            }
            
            // 	Update username
            stmt = conn.prepareStatement(
            		UPDATE_USER_ROLE
    		);
            // ON UPDATE CASCADE somehow doesn't work in this case, so I update the foreign key manually too.
            stmt.setString(1, newUsername);
            stmt.setString(2, newRolename);
            stmt.setString(3,  username);
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
        	Class.forName(JpicConstants.JDBC_CLASSPATH);
        	
        	// Get DB
            var url = JpicConstants.DBPATH;
            
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
