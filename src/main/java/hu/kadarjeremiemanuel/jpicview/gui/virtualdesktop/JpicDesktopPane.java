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
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.auth.RolesAndPermissions;
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
		askForFolder();
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
		// Login
		lw = new LoginInternalFrame(am, this);
		add(lw);
		// Logout
		logoutw = new JInternalFrame("Logout", false, false, false, true);
		logoutw.setLayout(new FlowLayout());
		JButton bttLogout = new JButton("Logout");
		bttLogout.addActionListener(e -> {
			try {
				ibi.setClosed(true);
				for(ImageInternalFrame iif : openedImages) {
					iif.dispose();
				}
				openedImages.clear();
				am.logout();
				logoutw.setVisible(false);
				if (acif != null) {
					acif.dispose();
				}
				if (ueif != null) {
					ueif.dispose();
				}
				showLoginScreen();
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		});
		logoutw.add(bttLogout);
		logoutw.setSize(100, 70);
		add(logoutw);
	}
	
	public void setTitlePostFix(String postfix) {
		mw.setTitlePostFix(postfix);
	}
	
	protected void showImage(String path) {
		ImageInternalFrame iif = new ImageInternalFrame(path);
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
		acif = new AdminControlInternalFrame(am, this);
		add(acif);
		acif.setVisible(true);
	}
	
	private void showImageBrowserScreen(String path) {
		ibi = new ImageBrowserInternalFrame(am, this, path);
		add(ibi);
		ibi.setVisible(true);
		if (am.checkRole(RolesAndPermissions.ADMIN)) {
			try {
				ibi.setIcon(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void showUserEditorScreen(String username) {
		
	}
	
	protected void showUserAddScreen() {	
		ueif = new UserEditorInternalFrame(am);
		add(ueif);
		ueif.setVisible(true);
	}
	
	protected void updateUIOnCredentials() {
		showLogoutScreen();
		if (am.isAuth()) {
			showImageBrowserScreen(path);
		}
		if (am.checkRole(RolesAndPermissions.ADMIN)) {
			showAdminControlPanelScreen();
		}
	}
	
}
