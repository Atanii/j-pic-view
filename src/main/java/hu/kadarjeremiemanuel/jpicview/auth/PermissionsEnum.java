/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.auth;

/**
 * @author atanii
 *
 */
public enum PermissionsEnum {
	ADMIN("*"), 
	GUEST("view:png"),
	REGULAR("view:png, jpg, gif"),
	PNG("view:png"),
	JPG("view:jpg"),
	GIF("view:gif"),
	PNG_JPG("view:jpg, png"),
	PNG_GIF("view:png, gif"),
	JPG_GIF("view:jpg, gif");
 
    private String permission;
 
    PermissionsEnum(String permission) {
        this.permission = permission;
    }
    
    public String getPermission() {
        return permission;
    }
}
