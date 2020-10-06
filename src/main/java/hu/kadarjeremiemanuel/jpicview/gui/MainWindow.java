/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui;

import java.awt.Container;

import javax.swing.JFrame;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop.JpicDesktopPane;
import hu.kadarjeremiemanuel.jpicview.utils.Strings;

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
	}
	
	private void setPane() {
		// pre settings
		dkPane = new JpicDesktopPane(am, this);
		Container contentPane = getContentPane();
		
		// properties
		setSize(1024, 768);
		setTitle(Strings.TITLE + "(NOT LOGGED IN)");
		
		// settings
		contentPane.add(dkPane);
	}
	
	public void setTitlePostFix(String postfix) {
		setTitle(Strings.TITLE + " " + postfix);
	}
}
