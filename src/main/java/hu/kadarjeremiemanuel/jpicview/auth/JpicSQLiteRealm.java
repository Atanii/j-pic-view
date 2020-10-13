package hu.kadarjeremiemanuel.jpicview.auth;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import hu.kadarjeremiemanuel.jpicview.utils.JpicConstants;

/**
 * 
 * @author atanii
 *
 */
public class JpicSQLiteRealm extends JdbcRealm {

	private static final String AUTHENTICATION_QUERY = "SELECT password FROM user WHERE username = ?";
	private static final String USER_ROLES_QUERY = "SELECT rolename FROM user_role WHERE username = ?";
	private static final String PERMISSIONS_QUERY = "SELECT permission FROM role WHERE rolename = ?";

	public JpicSQLiteRealm() {
		this.dataSource = getDataSource();
		this.authenticationQuery = AUTHENTICATION_QUERY;
		this.userRolesQuery = USER_ROLES_QUERY;
		this.permissionsQuery = PERMISSIONS_QUERY;
		this.setCredentialsMatcher(new HashedCredentialsMatcher("SHA-512"));
	}

	public BasicDataSource getDataSource() {
		var dataSource = new BasicDataSource();
		dataSource.setUrl(JpicConstants.DBPATH);
		dataSource.setDriverClassName(JpicConstants.JDBC_CLASSPATH);
		return dataSource;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		return super.doGetAuthenticationInfo(token);
	}
}