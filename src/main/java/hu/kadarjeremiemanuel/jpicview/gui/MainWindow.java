/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop.JpicDesktopPane;
import hu.kadarjeremiemanuel.jpicview.utils.SharedValues;

/**
 * @author atanii
 *
 */
public final class MainWindow extends JFrame {
	private static AuthManager am;
	private static JpicDesktopPane dkPane;
	
	public MainWindow(AuthManager am, String title) {
		this.am = am;
		setTitle(title);
		setPane();
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setPane() {
		// pre settings
		dkPane = new JpicDesktopPane(am, this);
		Container contentPane = getContentPane();
		
		// properties
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(SharedValues.WIDTH, SharedValues.HEIGHT);
		setTitle(SharedValues.TITLE + "(NOT LOGGED IN)");
		
		// settings
		contentPane.add(dkPane);
	}
	
	public void setTitlePostFix(String postfix) {
		setTitle(SharedValues.TITLE + " " + postfix);
	}
}
