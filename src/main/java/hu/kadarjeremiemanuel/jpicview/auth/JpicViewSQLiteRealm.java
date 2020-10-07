package hu.kadarjeremiemanuel.jpicview.auth;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import hu.kadarjeremiemanuel.jpicview.utils.SharedValues;

public class JpicViewSQLiteRealm extends JdbcRealm {

	public JpicViewSQLiteRealm()
    {
        this.dataSource = getDataSource();
        this.authenticationQuery = SharedValues.AUTHENTICATION_QUERY;
        this.userRolesQuery = SharedValues.USER_ROLES_QUERY;
        this.permissionsQuery = SharedValues.PERMISSIONS_QUERY;
    }

    public BasicDataSource getDataSource()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(SharedValues.DBPATH);
        dataSource.setDriverClassName(SharedValues.JDBC_CLASSPATH);
        return dataSource;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    {
        AuthenticationInfo info = super.doGetAuthenticationInfo(token);
        return info;
    }
}