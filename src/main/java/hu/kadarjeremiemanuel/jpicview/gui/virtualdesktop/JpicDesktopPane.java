/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.auth.RolesEnum;
import hu.kadarjeremiemanuel.jpicview.gui.MainWindow;
import hu.kadarjeremiemanuel.jpicview.utils.JpicConstants;

/**
 * @author atanii
 *
 */
public final class JpicDesktopPane extends JDesktopPane implements Refreshable {
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
	
	public void setTitlePostFix(String postfix) {
		mw.setTitlePostFix(postfix);
	}
	
	protected void showImageInternalFrame(String path) {
		add(new SingleImageViewer(path));
	}
	
	private void showLoginScreen() {
		add(new LoginForm(am, this));
	}
	
	private void showLogoutScreen() {
		add(new Logout(am, this));
	}
	
	private void showAdminControlPanelScreen() {
		add(new AdminControl(this));
	}
	
	private void showImageBrowserScreen(String path) {
		add(new ImageBrowser(am, this, path));
	}
	
	protected void showUserEditorScreen(String username, Refreshable ac) {
		if (ueif != null) { ueif.dispose(); }
		ueif = new UserEditor(username, ac);
		add(ueif);
		ueif.setVisible(true);
	}
	
	protected void showUserAddScreen(Refreshable ac) {
		if (ueif != null) { ueif.dispose(); }
		ueif = new UserEditor(ac);
		add(ueif);
		ueif.setVisible(true);
	}
	
	protected void setPath(String path) {
		this.path = path;
	}

	@Override
	public void refresh() {
		removeAll();
		updateUI();
		if (am.isAuth()) {
			showLogoutScreen();
			showImageBrowserScreen(path);
			if (am.checkRole(RolesEnum.ADMIN)) {
				showAdminControlPanelScreen();
			}
		} else {
			showLoginScreen();
		}
	}
	
}
