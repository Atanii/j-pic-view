/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.db.model;

/**
 * @author atanii
 *
 */
public class UserRoleModel {

	public String username;
	public String rolename;
	public String description;
	
	public UserRoleModel() {}

	public UserRoleModel(String username, String rolename, String description) {
		this.username = username;
		this.rolename = rolename;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return username + " - " + rolename + " - " + description;
	}

}
