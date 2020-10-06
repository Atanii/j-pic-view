/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.auth;

/**
 * @author atanii
 *
 */
public enum Roles {
	ADMIN("admin"), 
    GUEST("guest"), 
    DOWNLOAD("download"), 
    ALLVIEW("allview"),
	PNG("png"),
	JPG("jpg"),
	GIF("gif");
 
    private String role;
 
    Roles(String roleName) {
        this.role = roleName;
    }
 
    public String getRoleName() {
        return role;
    }
}
