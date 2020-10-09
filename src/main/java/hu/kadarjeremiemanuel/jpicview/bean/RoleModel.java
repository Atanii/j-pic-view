/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.bean;

/**
 * @author atanii
 *
 */
public final class RoleModel {

	public int id;
	public String rolename;
	public String permission;
	public String fullname;
	public String description;
	
	public RoleModel() {
		
	}
	
	public RoleModel(int id, String rolename, String permission, String fullname, String description) {
		this.id = id;
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