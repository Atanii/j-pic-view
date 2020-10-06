/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @author atanii
 *
 */
public final class AuthManager {
	private static AuthManager instance;
	
	private static final String RES_PATH = "classpath:shiro.ini";
	private SecurityManager securityManager;
	private Subject user;
	private Session userSess;
	
	private AuthManager() {
		initSecurity();
	}
	
	public static AuthManager getInstance() {
		if (instance == null) {
			instance = new AuthManager();
		}
		return instance;
	}
	
	private void initSecurity() {
		@SuppressWarnings("deprecation")
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(RES_PATH);
        securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
	}
	
	private void setUserAndSession() {
		user = SecurityUtils.getSubject();
        userSess = user.getSession();
	}
	
	public boolean isAuth() {
		setUserAndSession();
		return user.isAuthenticated();
	}
	
	public boolean login(String name, String pswd) {
		boolean res = isAuth();
		if (!res) {
            UsernamePasswordToken token = new UsernamePasswordToken(name, pswd);
            token.setRememberMe(true);
            try {
                user.login(token);
                res = isAuth();
            } catch (UnknownAccountException uae) {
                System.out.println("There is no user with username of " + token.getPrincipal());
                return false;
            } catch (IncorrectCredentialsException ice) {
            	System.out.println("Password for account " + token.getPrincipal() + " was incorrect!");
            	return false;
            } catch (LockedAccountException lae) {
            	System.out.println("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            	return false;
            }
            catch (AuthenticationException ex) {
            	System.out.println("Unexpected error: " + token.getPrincipal());
            	return false;
            }
        }
		return res;
	}
	
	public boolean checkRole(String role) {
		return user.hasRole(role);
	}
	
	public boolean checkRole(Roles role) {
		return user.hasRole(role.getRoleName());
	}
	
	public boolean checkPermission(String permission) {
		return user.isPermitted(permission);
	}
	
	public void setSessVal(String key, String val) {
		setUserAndSession();
		userSess.setAttribute(key, val);
	}
	
	public String getSessVal(String key) {
		setUserAndSession();
		return (String) userSess.getAttribute(key);
	}
	
	public void logout() {
		user.logout();
	}
	
}
