/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.shiro.crypto.hash.Sha512Hash;

import hu.kadarjeremiemanuel.jpicview.db.model.RoleModel;
import hu.kadarjeremiemanuel.jpicview.db.model.UserModel;
import hu.kadarjeremiemanuel.jpicview.db.model.UserRoleModel;
import hu.kadarjeremiemanuel.jpicview.utils.JpicConstants;

/**
 * @author atanii
 *
 */
public final class DatabaseHandler {

	private DatabaseHandler() {
	}

	private static final String SELECT_USER = "SELECT username, rolename from available_user_roles WHERE username = ?;";

	private static final String GET_ROLE_COUNT = "SELECT count(*) from available_roles;";
	private static final String GET_ROLES = "SELECT * from available_roles;";

	private static final String GET_USER_ROLE_MATRIX = "SELECT * from user_role_matrix;";

	private static final String DELETE_USER = "DELETE FROM user WHERE username = ?;";

	private static final String GET_USER_COUNT_WITH_NAME = "SELECT count(*) from available_users where username = ?;";

	private static final String INSERT_NEW_USER = "INSERT INTO user (username, password) VALUES (?, ?);";
	private static final String INSERT_INTO_USER_ROLE = "INSERT INTO user_role (username, rolename) VALUES (?, ?);";

	private static final String UPDATE_USERNAME = "UPDATE user SET username = ? WHERE username = ?;";
	private static final String UPDATE_USER_ROLE = "UPDATE user_role SET username = ?, rolename = ? WHERE username = ?;";

	public static final UserModel getUserForEdit(String username) {
		UserModel userToEdit = null;
		try (Connection conn = DriverManager.getConnection(JpicConstants.DBPATH)) {
			// GetUserRoles
			var stmt = conn.prepareStatement(SELECT_USER);
			stmt.setString(1, username);
			stmt.execute();
			var res = stmt.getResultSet();

			while (res.next()) {
				userToEdit = new UserModel(res.getString(1), res.getString(2));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return userToEdit;
	}

	public static final RoleModel[] getRoles() {
		var roles = new RoleModel[0];
		try (Connection conn = DriverManager.getConnection(JpicConstants.DBPATH)) {
			// GetUserRoles
			var stmt = conn.createStatement();
			stmt.execute(GET_ROLE_COUNT);
			var res = stmt.getResultSet();
			roles = new RoleModel[res.getInt(1)];

			// GetUserRoles
			stmt = conn.createStatement();
			stmt.execute(GET_ROLES);
			res = stmt.getResultSet();

			// Get Data
			int i = 0;
			while (res.next()) {
				roles[i] = new RoleModel(res.getString(1), res.getString(2), res.getString(3), res.getString(4));
				i++;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return roles;
	}

	public static final ArrayList<UserRoleModel> getUserRoleMatrix() {
		var data = new ArrayList<UserRoleModel>();
		try (Connection conn = DriverManager.getConnection(JpicConstants.DBPATH)) {
			// Get Roles
			var stmt = conn.createStatement();
			stmt.execute(GET_USER_ROLE_MATRIX);
			var res = stmt.getResultSet();

			// Get Data
			while (res.next()) {
				data.add(new UserRoleModel(res.getString(1), res.getString(2), res.getString(3)));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return data;
	}

	public static final boolean deleteUser(String username) {
		try (Connection conn = DriverManager.getConnection(JpicConstants.DBPATH)) {
			// Delete user
			var stmt = conn.prepareStatement(DELETE_USER);
			stmt.setString(1, username);
			stmt.execute();
			if (stmt.getUpdateCount() != 0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static final boolean addUser(String username, String plainTextPassword, String rolename) {
		try (Connection conn = DriverManager.getConnection(JpicConstants.DBPATH)) {
			// Get Roles
			var checkStmt = conn.prepareStatement(GET_USER_COUNT_WITH_NAME);
			checkStmt.execute();
			if (checkStmt.getResultSet().getInt(1) != 0) {
				JOptionPane.showMessageDialog(null, "Username is not unique!", "Registration Error",
						JOptionPane.ERROR_MESSAGE);
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
			stmt.setString(2, rolename);
			stmt.execute();
			if (stmt.getUpdateCount() == 0) {
				return false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public static final boolean updateUser(String username, String newUsername, String rolename, String newRolename) {
		try (Connection conn = DriverManager.getConnection(JpicConstants.DBPATH)) {
			// Update username
			var stmt = conn.prepareStatement(UPDATE_USERNAME);
			stmt.setString(1, newUsername);
			stmt.setString(2, username);
			stmt.execute();
			if (stmt.getUpdateCount() == 0) {
				return false;
			}

			// Update username
			stmt = conn.prepareStatement(UPDATE_USER_ROLE);
			// ON UPDATE CASCADE somehow doesn't work in this case, so I update the foreign
			// key manually too.
			stmt.setString(1, newUsername);
			stmt.setString(2, newRolename);
			stmt.setString(3, username);
			stmt.execute();
			if (stmt.getUpdateCount() == 0) {
				return false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public static final boolean checkDb() {
		try (Connection conn = DriverManager.getConnection(JpicConstants.DBPATH)) {
			// Connected
			System.out.println("Connection to SQLite has been established.");
			// The query is going to fail if there is no database or table.
			var stmt = conn.createStatement();
			// User table.
			stmt.execute("SELECT * from user;");
			System.out.println("User table checked!");
			// Role table.
			stmt.execute("SELECT * from role;");
			System.out.println("Role table checked!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
}
