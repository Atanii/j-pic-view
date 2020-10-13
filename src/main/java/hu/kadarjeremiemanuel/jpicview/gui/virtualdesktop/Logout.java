/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.FlowLayout;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;

/**
 * @author atanii
 * 
 *
 */
public class Logout extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Logout(AuthManager am, Refreshable desktopPane) {
		super("Logout", false, false, false, true);
		initUI(am, desktopPane);
	}
	
	private void initUI(AuthManager am, Refreshable desktopPane) {
		setLayout(new FlowLayout());
		
		var bttLogout = new JButton("Logout");
		bttLogout.addActionListener(e -> {
			am.logout();
			desktopPane.refresh();
		});
		add(bttLogout);
		
		setSize(100, 70);
		setVisible(true);
		try {
			setIcon(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
	}

}
