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
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.env.Environment;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import hu.kadarjeremiemanuel.jpicview.utils.JpicConstants;

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
		Environment env = new BasicIniEnvironment(JpicConstants.SHIRO_INI_PATH);
		securityManager = env.getSecurityManager();
		SecurityUtils.setSecurityManager(securityManager);
	}

	private void setUserAndSession() {
		user = SecurityUtils.getSubject();
		userSess = user.getSession();
	}

	public boolean isAuth() {
		return user.isAuthenticated();
	}

	public AuthCasesEnum login(String name, String pswd) {
		setUserAndSession();
		AuthCasesEnum res = isAuth() ? AuthCasesEnum.AUTHENTICATED : AuthCasesEnum.NOT_AUTHENTICATED;
		if (!res.toBoolean()) {
			UsernamePasswordToken token = new UsernamePasswordToken(name, pswd);
			token.setRememberMe(true);
			try {
				user.login(token);
				setUserAndSession();
				res = isAuth() ? AuthCasesEnum.AUTHENTICATED : AuthCasesEnum.NOT_AUTHENTICATED;
			} catch (UnknownAccountException uae) {
				return AuthCasesEnum.UNKNOWN_ACCOUNT;
			} catch (IncorrectCredentialsException ice) {
				return AuthCasesEnum.INCORRECT_CREDENTIALS;
			} catch (LockedAccountException lae) {
				return AuthCasesEnum.LOCKED_ACCOUNT;
			} catch (AuthenticationException ex) {
				return AuthCasesEnum.AUTHENTICATION_EXCEPTION;
			}
		}
		return res;
	}

	public boolean checkRole(String role) {
		return user.hasRole(role);
	}

	public boolean checkRole(RolesEnum role) {
		return user.hasRole(role.getRoleName());
	}

	public boolean checkPermission(String permission) {
		return user.isPermitted(permission);
	}

	public void setSessVal(String key, String val) {
		userSess.setAttribute(key, val);
	}

	public String getSessVal(String key) {
		return (String) userSess.getAttribute(key);
	}

	public void logout() {
		user.logout();
	}

}
