/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;

/**
 * @author atanii
 *
 */
public final class LoginInternalFrame extends JInternalFrame {
	private static AuthManager am;
	private static JpicDesktopPane dp;
	
	private JLabel lName;
	private JLabel lPswd;
	
	private JTextField tfName;
	private JTextField tfPswd;
	
	private JButton bttGuest;
	private JButton bttOk;
	
	public LoginInternalFrame(AuthManager am, JpicDesktopPane dp) {
		super("Login", false, false, false, true);
		this.am = am;
		this.dp = dp;
		setUI();
	}
	
	private void setUI() {
		GridLayout gl = new GridLayout(3, 2);
		setLayout(gl);
		
		lName = new JLabel("Username");
		add(lName);
		tfName = new JTextField();
		add(tfName);
		
		lPswd = new JLabel("Password");
		add(lPswd);
		tfPswd = new JTextField();
		add(tfPswd);
		
		bttGuest = new JButton("Continue As Guest");
		add(bttGuest);
		bttOk = new JButton("Confirm");
		add(bttOk);
		
		bttGuest.addActionListener(e -> {
			Login(true);
		});
		
		bttOk.addActionListener(e -> {
			Login(false);
		});
		
		setSize(350, 100);
	}
	
	private void Login(boolean asGuest) {
		if (asGuest) {
			if (am.login("guest", "guest")) {
				dp.updateUIOnCredentials();
				dp.setTitlePostFix("(LOGGED IN AS 'guest')");
				this.setVisible(false);
			}
		}
		else if (tfName.getText() != null && tfPswd.getText() != null) {
			String name = tfName.getText();
			String pswd = tfPswd.getText();
			if (am.login(name, pswd)) {
				dp.updateUIOnCredentials();
				dp.setTitlePostFix("(LOGGED IN AS '" + name + "')");
				this.setVisible(false);
			}
		}
		
	}

}
