/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop.JpicDesktopPane;
import hu.kadarjeremiemanuel.jpicview.utils.JpicConstants;

/**
 * @author atanii
 *
 */
public final class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MainWindow(AuthManager am, String title) {
		initUI(am);
	}
	
	private void initUI(AuthManager am) {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(JpicConstants.MAINWINDOW_WIDTH, JpicConstants.MAINWINDOW_HEIGHT);
		setTitle(JpicConstants.TITLE + "(NOT LOGGED IN)");
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		getContentPane().add(new JpicDesktopPane(am, this));
	}
	
	public void setTitlePostFix(String postfix) {
		setTitle(JpicConstants.TITLE + " " + postfix);
	}
}
