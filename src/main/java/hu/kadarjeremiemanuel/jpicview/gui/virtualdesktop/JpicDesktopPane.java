/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.auth.RolesEnum;
import hu.kadarjeremiemanuel.jpicview.gui.MainWindow;
import hu.kadarjeremiemanuel.jpicview.utils.JpicConstants;

/**
 * @author atanii
 *
 */
public final class JpicDesktopPane extends JDesktopPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AuthManager am;
	private MainWindow mw;
	
	private UserEditor ueif;
	
	private BufferedImage wallpaper;
	
	private String path;
	
	public JpicDesktopPane(AuthManager am, MainWindow mw) {
		this.am = am;
		this.mw = mw;
		initUI();
	}
	
	private void initUI() {
		setWallpaper();
		showLoginScreen();
	}
	
	private void setWallpaper() {
		try {
			wallpaper = ImageIO.read(getClass().getResource(JpicConstants.WALLPAPER_RESOURCE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(wallpaper, 0, 0, null);
    }
	
	protected void reset() {
		removeAll();
		updateUI();
		showLoginScreen();
	}
	
	public void setTitlePostFix(String postfix) {
		mw.setTitlePostFix(postfix);
	}
	
	protected void newImageInternalFrame(String path) {
		add(new SingleImageViewer(path));
	}
	
	private void showLoginScreen() {
		add(new LoginForm(am, this));
	}
	
	private void showLogoutScreen() {
		add(new Logout(this));
	}
	
	private void showAdminControlPanelScreen() {
		if (am.checkRole(RolesEnum.ADMIN)) {
			var acif = new AdminControl(am, this);
			add(acif);
			acif.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(
					null,
					"Only administrators are permitted to access this menu!",
					"Security",
					JOptionPane.ERROR_MESSAGE
			);
		}
	}
	
	private void showImageBrowserScreen(String path) {
		add(new ImageBrowser(am, this, path));
	}
	
	protected void showUserEditorScreen(String username) {
		if (ueif != null) { ueif.dispose(); }
		ueif = new UserEditor(am, username);
		add(ueif);
		ueif.setVisible(true);
	}
	
	protected void showUserAddScreen() {
		if (ueif != null) { ueif.dispose(); }
		ueif = new UserEditor(am);
		add(ueif);
		ueif.setVisible(true);
	}
	
	protected void setPath(String path) {
		this.path = path;
	}
	
	protected void updateUIOnCredentials() {
		showLogoutScreen();
		if (am.isAuth()) {
			showImageBrowserScreen(path);
			if (am.checkRole(RolesEnum.ADMIN)) {
				showAdminControlPanelScreen();
			}
		}
	}
	
}
