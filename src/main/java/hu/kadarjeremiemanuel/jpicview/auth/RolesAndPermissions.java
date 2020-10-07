/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.auth;

/**
 * @author atanii
 *
 */
public enum RolesAndPermissions {
	ADMIN("admin"), 
    GUEST("guest"), 
    BROWSE("browse"),
	PNG("png"),
	JPG("jpg"),
	GIF("gif");
 
    private String role;
 
    RolesAndPermissions(String roleName) {
        this.role = roleName;
    }
 
    public String getRoleName() {
        return role;
    }
    
    public String getViewPermission() {
        return role + ":view";
    }
}
