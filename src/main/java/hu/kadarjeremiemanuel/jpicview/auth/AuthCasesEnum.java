package hu.kadarjeremiemanuel.jpicview.auth;

/**
 * 
 * @author atanii
 *
 */
public enum AuthCasesEnum {
	AUTHENTICATED("User is Authenticated."),
	
	NOT_AUTHENTICATED("User is not authenticated!"),
	
	UNKNOWN_ACCOUNT("There is no user with this username!"),
    INCORRECT_CREDENTIALS("Password is incorrect!"),
	LOCKED_ACCOUNT("The account for this username is locked.  " +
            "Please contact your administrator to unlock it."),
	
	AUTHENTICATION_EXCEPTION("Unexpected error: %s"),
	UNEXPECTED_EXCEPTION("Unexpected error: %s");
 
    private String authCase;
 
    AuthCasesEnum(String roleName) {
        this.authCase = roleName;
    }
 
    public String getCaseMessage() {
        return authCase;
    }
    
    public boolean toBoolean() {
    	return this == AUTHENTICATED;
    }
}
