/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.bean.RoleModel;
import hu.kadarjeremiemanuel.jpicview.db.DatabaseHandler;

/**
 * @author atanii
 *
 */
public final class UserEditorInternalFrame extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField txtName;
	private JPasswordField txtPswd;
	private JList<RoleModel> liRoles;
	
	private AuthManager am;
	
	private RoleModel[] roles;

	public UserEditorInternalFrame(AuthManager am) {
		super("Add User", false, true, false, true);
		this.am = am;
		roles = DatabaseHandler.getRoles(am);
		initUIForNew();
	}
	
	public UserEditorInternalFrame(AuthManager am, String userName) {
		super("Edit User", false, true, false, true);
		this.am = am;
		roles = DatabaseHandler.getRoles(am);
		initUIForEdit(userName);
	}
	
	private void initUIForNew() {
		var lName = new JLabel("Username");
		var lPswd = new JLabel("Password");
		var lRole = new JLabel("Role");
		
		txtName = new JTextField();
		txtPswd = new JPasswordField();
		
		liRoles = new JList<RoleModel>();
		liRoles.setListData(roles);
		var scrollRoleList = new JScrollPane(liRoles);
		
		var bttAdd = new JButton("Add");
		var bttCancel = new JButton("Cancel");
		
		bttAdd.addActionListener(e -> {
			DatabaseHandler.addUser(
					this.txtName.getText(),
					String.valueOf(txtPswd.getPassword()),
					liRoles.getSelectedValue().rolename,
					am
			);
		});
		bttCancel.addActionListener(e -> {
			dispose();
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
						.addComponent(lRole)
						.addComponent(scrollRoleList, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(
						gl.createSequentialGroup()
						.addComponent(bttAdd)
						.addComponent(bttCancel)
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
						.addComponent(lRole)
						.addComponent(scrollRoleList)
				)
				.addGroup(
						gl.createParallelGroup()
						.addComponent(bttAdd)
						.addComponent(bttCancel)
				)
		);
		
		gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);
        
        var headerSize = getInsets().top;
        setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height + headerSize * 3));
		
		pack();
	}
	
	private void initUIForEdit(String userName) {
		
	}

}
