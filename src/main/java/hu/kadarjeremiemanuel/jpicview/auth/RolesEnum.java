package hu.kadarjeremiemanuel.jpicview.auth;

/**
 * @author atanii
 *
 */
public enum RolesEnum {
	ADMIN("admin"), 
    GUEST("guest"),
    REGULAR("regular"),
	PNG("png"),
	JPG("jpg"),
	GIF("gif"),
	PNG_JPG("png_jpg"),
	PNG_GIF("png_gif"),
	JPG_GIF("jpg_gif");
 
    private String role;
 
    RolesEnum(String roleName) {
        this.role = roleName;
    }
 
    public String getRoleName() {
        return role;
    }
}
