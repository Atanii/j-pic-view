/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.auth.RolesEnum;
import hu.kadarjeremiemanuel.jpicview.gui.MainWindow;

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
	
	private LoginInternalFrame lw;
	private JInternalFrame logoutw;
	private ImageBrowserInternalFrame ibi;
	private AdminControlInternalFrame acif;
	private UserEditorInternalFrame ueif; 
	private List<ImageInternalFrame> openedImages;
	
	private String path;
	
	private BufferedImage wallpaper;
	
	public JpicDesktopPane(AuthManager am, MainWindow mw) {
		this.am = am;
		this.mw = mw;
		openedImages = new LinkedList<>();
		setWallpaper();
		setDefaultWindowSet();
		showLoginScreen();
	}
	
	private void setWallpaper() {
		try {
			wallpaper = ImageIO.read(getClass().getResource("/landscape-768423.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(wallpaper, 0, 0, null);
    }
	
	private void setDefaultWindowSet() {
		// Login
		lw = new LoginInternalFrame(am, this);
		add(lw);
		// Logout
		logoutw = new JInternalFrame("Logout", false, false, false, true);
		logoutw.setLayout(new FlowLayout());
		var bttLogout = new JButton("Logout");
		bttLogout.addActionListener(e -> {
			reset();
		});
		logoutw.add(bttLogout);
		logoutw.setSize(100, 70);
		add(logoutw);
	}
	
	private void reset() {
		removeAll();
		updateUI();
		setDefaultWindowSet();
		showLoginScreen();
	}
	
	public void setTitlePostFix(String postfix) {
		mw.setTitlePostFix(postfix);
	}
	
	protected void showImage(String path) {
		var iif = new ImageInternalFrame(path);
		openedImages.add(iif);
		add(iif);
	}
	
	private void showLoginScreen() {
		lw.setVisible(true);
	}
	
	private void showLogoutScreen() {
		logoutw.setVisible(true);
		try {
			logoutw.setIcon(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
	}
	
	private void showAdminControlPanelScreen() {
		if (am.checkRole(RolesEnum.ADMIN)) {
			acif = new AdminControlInternalFrame(am, this);
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
	
	protected void setPath(String path) {
		this.path = path;
	}
	
	private void showImageBrowserScreen(String path) {
		ibi = new ImageBrowserInternalFrame(am, this, path);
		add(ibi);
		ibi.setVisible(true);
		if (am.checkRole(RolesEnum.ADMIN)) {
			try {
				ibi.setIcon(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void showUserEditorScreen(String username) {
		if (ueif != null) { ueif.dispose(); }
		ueif = new UserEditorInternalFrame(am, username);
		add(ueif);
		ueif.setVisible(true);
	}
	
	protected void showUserAddScreen() {
		if (ueif != null) { ueif.dispose(); }
		ueif = new UserEditorInternalFrame(am);
		add(ueif);
		ueif.setVisible(true);
	}
	
	protected void updateUIOnCredentials() {
		showLogoutScreen();
		if (am.isAuth()) {
			showImageBrowserScreen(path);
		}
		if (am.checkRole(RolesEnum.ADMIN)) {
			showAdminControlPanelScreen();
		}
	}
	
}
