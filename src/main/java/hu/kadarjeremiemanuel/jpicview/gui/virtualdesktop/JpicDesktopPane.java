/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import javax.swing.JDesktopPane;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.gui.MainWindow;

/**
 * @author atanii
 *
 */
public final class JpicDesktopPane extends JDesktopPane {
	private static AuthManager am;
	private static MainWindow mw;
	
	private static LoginInternalFrame lw;
	
	public JpicDesktopPane(AuthManager am, MainWindow mw) {
		this.am = am;
		this.mw = mw;
		setDefaultWindowSet();
	}
	
	private void setDefaultWindowSet() {
		lw = new LoginInternalFrame(am, this);
		add(lw);
	}
	
	public void setTitlePostFix(String postfix) {
		mw.setTitlePostFix(postfix);
	}
	
	protected void updateUIOnCredentials() {
		
	}
	
}
