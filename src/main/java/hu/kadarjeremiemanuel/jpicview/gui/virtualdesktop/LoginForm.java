/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;


import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.auth.RolesEnum;
import hu.kadarjeremiemanuel.jpicview.db.DatabaseHandler;

/**
 * @author atanii
 *
 */
public final class LoginForm extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AuthManager am;
	private JpicDesktopPane dp;
	
	private JTextField txtName;
	private JPasswordField txtPswd;
	
	private String path;
	
	public LoginForm(AuthManager am, JpicDesktopPane dp) {
		super("Login", false, false, false, true);
		this.am = am;
		this.dp = dp;
		initUI();
	}
	
	private void askForFolder(JLabel label) {
		var jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setDialogTitle("Choose a directory with images (the program will exit if you cancel)");
		
		int res = jfc.showSaveDialog(null); 
        if (res == JFileChooser.APPROVE_OPTION) { 
            path = jfc.getSelectedFile().getAbsolutePath();
            label.setText(path);
        }
	}
	
	private void initUI() {
		var lName = new JLabel("Username");
		txtName = new JTextField();
		
		var lPswd = new JLabel("Password");
		txtPswd = new JPasswordField();
		
		var bttChooseFolder = new JButton("Choose Folder");
		var txtPath = new JLabel("FOLDER TO BROWSE");
		
		var bttGuest = new JButton("Continue As Guest");
		var bttOk = new JButton("Confirm");
		var bttSignup = new JButton("SignUp");
		
		bttGuest.addActionListener(e -> {
			Login(true);
		});
		bttOk.addActionListener(e -> {
			Login(false);
		});
		bttSignup.addActionListener(e -> {
			SignUp();
		});
		bttChooseFolder.addActionListener(e -> {
			askForFolder(txtPath);
		});
		
		var pane = getContentPane();
        var gl = new GroupLayout(pane);
        pane.setLayout(gl);
		
		gl.setHorizontalGroup(
				gl.createParallelGroup()
				.addGroup(
						gl.createSequentialGroup()
						.addComponent(lName)
						.addComponent(txtName)
				)
				.addGroup(
						gl.createSequentialGroup()
						.addComponent(lPswd)
						.addComponent(txtPswd)
				)
				.addGroup(
						gl.createSequentialGroup()
						.addComponent(bttChooseFolder)
						.addComponent(txtPath)
				)
				.addGroup(
						gl.createSequentialGroup()
						.addComponent(bttGuest)
						.addComponent(bttOk)
						.addComponent(bttSignup)
				)
		);
		
		gl.setVerticalGroup(
				gl.createSequentialGroup()
				.addGroup(
						gl.createParallelGroup()
						.addComponent(lName)
						.addComponent(txtName)
				)
				.addGroup(
						gl.createParallelGroup()
						.addComponent(lPswd)
						.addComponent(txtPswd)
				)
				.addGroup(
						gl.createParallelGroup()
						.addComponent(bttChooseFolder)
						.addComponent(txtPath)
				)
				.addGroup(
						gl.createParallelGroup()
						.addComponent(bttGuest)
						.addComponent(bttOk)
						.addComponent(bttSignup)
				)
		);
		
		gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);
        
        var headerSize = getInsets().top;
        setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height + headerSize * 3));
		
		pack();
		
		setVisible(true);
	}
	
	private void Login(boolean asGuest) {
		if (path == null) {
			JOptionPane.showMessageDialog(
					null,
					"You have to choose a folder first!",
					"Login Error",
					JOptionPane.ERROR_MESSAGE
			);
			return;
		}
		
		var name = "";
		var pswd = "";
		
		if (asGuest) {
			name = "guest";
			pswd = "guest";
		} else if (txtName.getText() != null && txtPswd.getPassword() != null) {
			name = txtName.getText();
			pswd = String.valueOf(txtPswd.getPassword());
		} else {
			JOptionPane.showMessageDialog(
					null,
					"Username and password are all required!",
					"Login Error",
					JOptionPane.ERROR_MESSAGE
			);
		}
		
		var authRes = am.login(name, pswd);
		if (authRes.toBoolean()) {
			dp.setPath(path);
			dp.updateUIOnCredentials();
			dp.setTitlePostFix("(LOGGED IN AS '" + name + "')");
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, authRes.getCaseMessage(), "Authentication Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void SignUp() {
		if (txtName.getText() != null && txtPswd.getPassword() != null) {
			var result = DatabaseHandler.addUser(
					this.txtName.getText(),
					String.valueOf(txtPswd.getPassword()),
					RolesEnum.REGULAR.getRoleName()
			);
			if (result) {
				JOptionPane.showMessageDialog(
						null,
						"Registration completed successfully, you can now login!",
						"Registration",
						JOptionPane.PLAIN_MESSAGE
				);
			} else {
				JOptionPane.showMessageDialog(
						null,
						"An error occured while completing registration!",
						"Registration",
						JOptionPane.ERROR_MESSAGE
				);
			}
		} else {
			JOptionPane.showMessageDialog(
					null,
					"Both username and password is required!",
					"Login Error",
					JOptionPane.ERROR_MESSAGE
			);
		}
	}

}
