/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.db.model;

/**
 * @author atanii
 *
 */
public class UserModel {

	public String username;
	public String rolename;
	
	public UserModel() {}

	public UserModel(String username, String rolename) {
		this.username = username;
		this.rolename = rolename;
	}
	
	@Override
	public String toString() {
		return username + " - " + rolename;
	}

}
