/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import javax.swing.JDesktopPane;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.auth.Roles;
import hu.kadarjeremiemanuel.jpicview.gui.MainWindow;

/**
 * @author atanii
 *
 */
public final class JpicDesktopPane extends JDesktopPane {
	private static AuthManager am;
	private static MainWindow mw;
	
	private static LoginInternalFrame lw;
	private static ImageBrowserInternalFrame ibi;
	
	public JpicDesktopPane(AuthManager am, MainWindow mw) {
		this.am = am;
		this.mw = mw;
		setDefaultWindowSet();
		showLoginScreen();
	}
	
	private void setDefaultWindowSet() {
		lw = new LoginInternalFrame(am, this);
		add(lw);
		ibi = new ImageBrowserInternalFrame(am, this);
		add(ibi);
	}
	
	public void setTitlePostFix(String postfix) {
		mw.setTitlePostFix(postfix);
	}
	
	protected void showImage(String path) {
		add(new ImageInternalFrame(path));
	}
	
	private void showLoginScreen() {
		lw.setVisible(true);
	}
	
	private void showImageBrowserScreen() {
		ibi.setVisible(true);
	}
	
	protected void updateUIOnCredentials() {
		if (am.checkRole(Roles.GUEST)) {
			showImageBrowserScreen();
		}
	}
	
}
