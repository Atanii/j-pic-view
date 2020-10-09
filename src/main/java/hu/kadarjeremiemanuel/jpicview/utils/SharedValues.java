package hu.kadarjeremiemanuel.jpicview.utils;

public final class SharedValues {
	// MAINWINDOW /////////////////////////////////////////////////////////////////////
	public static final String TITLE = "JpicView";
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	
	// IMAGES /////////////////////////////////////////////////////////////////////////
	public static final String IMAGE_DIR_CLASSPATH = "images/";
	
	// DATABASE ///////////////////////////////////////////////////////////////////////
	public static final String JDBC_CLASSPATH = "org.sqlite.JDBC";
	
	// Get DB from Classpath resource
    // https://github.com/xerial/sqlite-jdbc/blob/master/Usage.md#reading-database-files-in-classpaths-or-network-read-only
	public static final String DBPATH = "jdbc:sqlite::resource:users.db";
	
	// SHIRO //////////////////////////////////////////////////////////////////////////
	public static final String SHIRO_INI_PATH = "classpath:shiro.ini";
	
	public static final String AUTHENTICATION_QUERY = "SELECT password FROM user WHERE username = ?";
	public static final String USER_ROLES_QUERY = "SELECT rolename FROM user_role WHERE username = ?";
	public static final String PERMISSIONS_QUERY = "SELECT permission FROM role WHERE rolename = ?";
}
