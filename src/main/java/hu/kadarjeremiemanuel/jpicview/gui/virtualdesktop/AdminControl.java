/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.db.DatabaseHandler;

/**
 * @author atanii
 *
 */
public final class AdminControl extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable tUserRoleMatrix;
	private AuthManager am;
	private JpicDesktopPane dp;
	private Object[][] data;
	
	public AdminControl(AuthManager am, JpicDesktopPane dp) {
		super("Admin Controlpanel (under implementation)", true, false, true, true);
		this.am = am;
		this.dp = dp;
		initUI();
	}
	
	private DefaultTableModel getModel() {
		Object[] columnNames = {"Username", "Role", "Description"};
        data = DatabaseHandler.getUserRoleMatrix();
        return new DefaultTableModel(data, columnNames);
	}
	
	private JComponent getUserRoleMatrixTable() {
        tUserRoleMatrix = new JTable(getModel()) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                    case 2:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        
        tUserRoleMatrix.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tUserRoleMatrix.setPreferredScrollableViewportSize(tUserRoleMatrix.getPreferredSize());
        
        var scrollPane = new JScrollPane(tUserRoleMatrix);
        return scrollPane;
	}
	
	private void newUser() {
		dp.showUserAddScreen();
	}
	
	private void editUser() {
		int row = tUserRoleMatrix.getSelectedRow();
		if (row != -1) {
			String username = String.valueOf(data[row][0]);
			dp.showUserEditorScreen(username);
		}
	}
	
	private void deleteUser() {
		int row = tUserRoleMatrix.getSelectedRow();
		if (row != -1 && JOptionPane.showConfirmDialog(null, "Are you sure?") == 0) {
			String username = String.valueOf(data[row][0]);
			DatabaseHandler.deleteUser(username);
		}
	}
	
	private void refresh() {
		this.tUserRoleMatrix.setModel(getModel());
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
    }
}
