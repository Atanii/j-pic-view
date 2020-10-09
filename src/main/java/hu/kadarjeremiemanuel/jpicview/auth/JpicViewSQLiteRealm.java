package hu.kadarjeremiemanuel.jpicview.auth;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import hu.kadarjeremiemanuel.jpicview.utils.SharedValues;

public class JpicViewSQLiteRealm extends JdbcRealm {

	public JpicViewSQLiteRealm()
    {
        this.dataSource = getDataSource();
        this.authenticationQuery = SharedValues.AUTHENTICATION_QUERY;
        this.userRolesQuery = SharedValues.USER_ROLES_QUERY;
        this.permissionsQuery = SharedValues.PERMISSIONS_QUERY;
        this.setCredentialsMatcher(new HashedCredentialsMatcher("SHA-512"));
    }

    public BasicDataSource getDataSource()
    {
        var dataSource = new BasicDataSource();
        dataSource.setUrl(SharedValues.DBPATH);
        dataSource.setDriverClassName(SharedValues.JDBC_CLASSPATH);
        return dataSource;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        return super.doGetAuthenticationInfo(token);
    }
}