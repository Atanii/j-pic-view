/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.auth;

import javax.swing.JOptionPane;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.env.Environment;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import hu.kadarjeremiemanuel.jpicview.utils.SharedValues;

/**
 * @author atanii
 *
 */
public final class AuthManager {
	private static AuthManager instance;
	
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
		Environment env = new BasicIniEnvironment(SharedValues.SHIRO_INI_PATH);
		securityManager = env.getSecurityManager();
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
            	JOptionPane.showMessageDialog(null, "There is no user with username of " + token.getPrincipal(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } catch (IncorrectCredentialsException ice) {
            	JOptionPane.showMessageDialog(null, "Password for account " + token.getPrincipal() + " was incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
            	return false;
            } catch (LockedAccountException lae) {
            	JOptionPane.showMessageDialog(null, "The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.", "Error", JOptionPane.ERROR_MESSAGE);
            	return false;
            }
            catch (AuthenticationException ex) {
            	JOptionPane.showMessageDialog(null, "Unexpected error: " + token.getPrincipal(), "Error", JOptionPane.ERROR_MESSAGE);
            	return false;
            }
        }
		return res;
	}
	
	public boolean checkRole(String role) {
		setUserAndSession();
		return user.hasRole(role);
	}
	
	public boolean checkRole(RolesAndPermissions role) {
		setUserAndSession();
		return user.hasRole(role.getRoleName());
	}
	
	public boolean checkPermission(String permission) {
		setUserAndSession();
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
