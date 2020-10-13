/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import hu.kadarjeremiemanuel.jpicview.db.DatabaseHandler;
import hu.kadarjeremiemanuel.jpicview.db.model.UserRoleModel;
import hu.kadarjeremiemanuel.jpicview.db.model.UserRoleTableModel;

/**
 * @author atanii
 *
 */
public final class AdminControl extends JInternalFrame implements Refreshable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable tUserRoleMatrix;
	private JpicDesktopPane dp;
	private ArrayList<UserRoleModel> data;
	
	public AdminControl(JpicDesktopPane dp) {
		super("Admin Controlpanel", true, false, true, true);
		this.dp = dp;
		initUI();
	}
	
	private void initUI() {var table = getUserRoleMatrixTable();
        
        var bttNew = new JButton("New User");
		bttNew.addActionListener(e -> {
			newUser();
		});
        var bttEdit = new JButton("Edit User");
		bttEdit.addActionListener(e -> {
			editUser();
		});
		var bttDelete = new JButton("Delete User");
		bttDelete.addActionListener(e -> {
			deleteUser();
		});
		var bttRefresh = new JButton("Refresh");
		bttRefresh.addActionListener(e -> {
			refresh();
		});
		
		var pane = getContentPane();
        var gl = new GroupLayout(pane);
        pane.setLayout(gl);
		
		gl.setVerticalGroup(
				gl.createParallelGroup()
				.addGroup(
						gl.createSequentialGroup()
						.addComponent(table, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(
								gl.createParallelGroup()
								.addComponent(bttNew)
								.addComponent(bttEdit)
								.addComponent(bttDelete)
								.addComponent(bttRefresh)
						)
				)
		);
		
		gl.setHorizontalGroup(
				gl.createSequentialGroup()
				.addGroup(
						gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(table)
						.addGroup(
								gl.createSequentialGroup()
								.addComponent(bttNew)
								.addComponent(bttEdit)
								.addComponent(bttDelete)
								.addComponent(bttRefresh)
						)
				)
		);
		
        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);
        
        var headerSize = getInsets().top;
        setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height + headerSize * 3));
        
        pack();
        
        setVisible(true);
    }
	
	private UserRoleTableModel getModel() {
        data = DatabaseHandler.getUserRoleMatrix();
        return new UserRoleTableModel(data);
	}
	
	private JComponent getUserRoleMatrixTable() {
        tUserRoleMatrix = new JTable(getModel());
        
        tUserRoleMatrix.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tUserRoleMatrix.setPreferredScrollableViewportSize(tUserRoleMatrix.getPreferredSize());
        
        var scrollPane = new JScrollPane(tUserRoleMatrix);
        return scrollPane;
	}
	
	private void newUser() {
		dp.showUserAddScreen(this);
	}
	
	private void editUser() {
		int row = tUserRoleMatrix.getSelectedRow();
		if (row != -1) {
			String username = String.valueOf(data.get(row).username);
			dp.showUserEditorScreen(username, this);
		}
	}
	
	private void deleteUser() {
		int row = tUserRoleMatrix.getSelectedRow();
		if (row != -1 && JOptionPane.showConfirmDialog(null, "Are you sure?") == 0) {
			String username = String.valueOf(data.get(row).username);
			var result = DatabaseHandler.deleteUser(username);
			if (result) {
				JOptionPane.showMessageDialog(
						null,
						"User deleted successfully!",
						"Administration",
						JOptionPane.PLAIN_MESSAGE
				);
				refresh();
			} else {
				JOptionPane.showMessageDialog(
						null,
						"Error occured while updating user!",
						"Administration",
						JOptionPane.ERROR_MESSAGE
				);
			}
		}
	}
	
	@Override
	public void refresh() {
		this.tUserRoleMatrix.setModel(getModel());
	}
}
