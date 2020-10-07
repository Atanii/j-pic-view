/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

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
	
	private static String path;
	
	public JpicDesktopPane(AuthManager am, MainWindow mw) {
		this.am = am;
		this.mw = mw;
		setDefaultWindowSet();
		askForFolder();
		showLoginScreen();
	}
	
	private void askForFolder() {
		JFileChooser jfc = new JFileChooser();
		
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setDialogTitle("Choose a directory with images (the program will exit if you cancel)");
		
		int res = jfc.showSaveDialog(null); 
        if (res == JFileChooser.APPROVE_OPTION) { 
            path = jfc.getSelectedFile().getAbsolutePath();
        } else {
        	System.exit(0);
        }
	}
	
	private void setDefaultWindowSet() {
		lw = new LoginInternalFrame(am, this);
		add(lw);
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
	
	private void showImageBrowserScreen(String path) {
		ibi = new ImageBrowserInternalFrame(am, this, path);
		add(ibi);
		ibi.setVisible(true);
	}
	
	protected void updateUIOnCredentials() {
		if (am.checkRole(Roles.GUEST)) {
			showImageBrowserScreen(path);
		}
	}
	
}
