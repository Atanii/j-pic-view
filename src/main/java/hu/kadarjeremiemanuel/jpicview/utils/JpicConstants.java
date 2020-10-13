package hu.kadarjeremiemanuel.jpicview.utils;

/**
 * 
 * @author atanii
 *
 */
public final class JpicConstants {
	
	private JpicConstants() {}
	
	// GUI ////////////////////////////////////////////////////////////////////////////
	public static final String TITLE = "JpicView";
	
	public static final int MAINWINDOW_WIDTH = 1024;
	public static final int MAINWINDOW_HEIGHT = 768;
	
	public static final String WALLPAPER_RESOURCE_PATH = "/purple-370132.png";
	
	// DATABASE ///////////////////////////////////////////////////////////////////////
	public static final String JDBC_CLASSPATH = "org.sqlite.JDBC";
	
	// Get DB from Classpath resource
    // https://github.com/xerial/sqlite-jdbc/blob/master/Usage.md#reading-database-files-in-classpaths-or-network-read-only
	public static final String DBPATH = "jdbc:sqlite::resource:users.db";
	
	// SHIRO //////////////////////////////////////////////////////////////////////////
	public static final String SHIRO_INI_PATH = "classpath:shiro.ini";
}
