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
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import hu.kadarjeremiemanuel.jpicview.db.DatabaseHandler;
import hu.kadarjeremiemanuel.jpicview.db.model.RoleModel;
import hu.kadarjeremiemanuel.jpicview.db.model.UserModel;

/**
 * @author atanii
 *
 */
public final class UserEditor extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField txtName;
	private JPasswordField txtPswd;
	private JList<RoleModel> liRoles;
	
	private RoleModel[] roles;
	private UserModel userToEdit;

	public UserEditor(Refreshable ac) {
		super("Registrate User", false, true, false, true);
		roles = DatabaseHandler.getRoles();
		initUIForNew(ac);
	}
	
	public UserEditor(String username, Refreshable ac) {
		super("Edit User", false, true, false, true);
		roles = DatabaseHandler.getRoles();
		userToEdit = DatabaseHandler.getUserForEdit(username);
		initUIForEdit(username, ac);
	}
	
	private void initUIForNew(Refreshable ac) {
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
			var result = DatabaseHandler.addUser(
					this.txtName.getText(),
					String.valueOf(txtPswd.getPassword()),
					liRoles.getSelectedValue().rolename
			);
			if (result) {
				JOptionPane.showMessageDialog(
						null,
						"User added successfully!",
						"Administration",
						JOptionPane.PLAIN_MESSAGE
				);
				ac.refresh();
			} else {
				JOptionPane.showMessageDialog(
						null,
						"An error occured while creating new user!",
						"Administration",
						JOptionPane.ERROR_MESSAGE
				);
			}
			dispose();
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
						.addComponent(
								scrollRoleList,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE
						)
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
	
	private void initUIForEdit(String username, Refreshable ac) {
		var lName = new JLabel("Username");
		var lRole = new JLabel("Role");
		
		txtName = new JTextField();
		liRoles = new JList<RoleModel>();
		liRoles.setListData(roles);
		var scrollRoleList = new JScrollPane(liRoles);
		
		var bttSave = new JButton("Save");
		var bttCancel = new JButton("Cancel");
		
		bttSave.addActionListener(e -> {
			var result = DatabaseHandler.updateUser(
					username, this.txtName.getText(),
					userToEdit.rolename, liRoles.getSelectedValue().rolename
			);
			if (result) {
				JOptionPane.showMessageDialog(
						null,
						"User updated successfully!",
						"Administration",
						JOptionPane.PLAIN_MESSAGE
				);
				ac.refresh();
			} else {
				JOptionPane.showMessageDialog(
						null,
						"Error occured while updating user!",
						"Administration",
						JOptionPane.ERROR_MESSAGE
				);
			}
			dispose();
		});
		bttCancel.addActionListener(e -> {
			dispose();
		});
		
		RoleModel r = roles[0];
		for (var role : roles) {
			if (role.rolename == userToEdit.rolename) {
				r = role;
			}
		}
		liRoles.setSelectedValue(r, true);
		txtName.setText(username);
		
		
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
						.addComponent(lRole)
						.addComponent(scrollRoleList,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE
						)
				)
				.addGroup(
						gl.createSequentialGroup()
						.addComponent(bttSave)
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
						.addComponent(lRole)
						.addComponent(scrollRoleList)
				)
				.addGroup(
						gl.createParallelGroup()
						.addComponent(bttSave)
						.addComponent(bttCancel)
				)
		);
		
		gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);
        
        var headerSize = getInsets().top;
        setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height + headerSize * 3));
		
		pack();
	}

}
