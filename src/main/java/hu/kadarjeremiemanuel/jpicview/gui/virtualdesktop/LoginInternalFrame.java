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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;

/**
 * @author atanii
 *
 */
public final class LoginInternalFrame extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static AuthManager am;
	private static JpicDesktopPane dp;
	
	private JTextField tfName;
	private JPasswordField tfPswd;
	
	public LoginInternalFrame(AuthManager am, JpicDesktopPane dp) {
		super("Login", false, false, false, true);
		this.am = am;
		this.dp = dp;
		setUI();
	}
	
	private void setUI() {
		GridLayout gl = new GridLayout(3, 2);
		setLayout(gl);
		
		var lName = new JLabel("Username");
		add(lName);
		tfName = new JTextField();
		add(tfName);
		
		var lPswd = new JLabel("Password");
		add(lPswd);
		tfPswd = new JPasswordField();
		add(tfPswd);
		
		var bttGuest = new JButton("Continue As Guest");
		add(bttGuest);
		var bttOk = new JButton("Confirm");
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
		else if (tfName.getText() != null && tfPswd.getPassword() != null) {
			String name = tfName.getText();
			String pswd = String.valueOf(tfPswd.getPassword());
			if (am.login(name, pswd)) {
				dp.updateUIOnCredentials();
				dp.setTitlePostFix("(LOGGED IN AS '" + name + "')");
				this.setVisible(false);
			}
		}
		
	}

}
