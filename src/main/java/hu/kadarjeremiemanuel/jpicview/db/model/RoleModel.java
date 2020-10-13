/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.db.model;

/**
 * @author atanii
 *
 */
public class RoleModel {

	public String rolename;
	public String permission;
	public String fullname;
	public String description;
	
	public RoleModel() {}
	
	public RoleModel(String rolename, String permission, String fullname, String description) {
		this.rolename = rolename;
		this.permission = permission;
		this.fullname = fullname;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return fullname;
	}

}
