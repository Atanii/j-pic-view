/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.Dimension;
import java.beans.PropertyVetoException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.db.DatabaseHandler;

/**
 * @author atanii
 *
 */
public final class AdminControlInternalFrame extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable tUserRoleMatrix;
	private AuthManager am;
	private JpicDesktopPane dp;
	private Object[][] data;
	
	public AdminControlInternalFrame(AuthManager am, JpicDesktopPane dp) {
		super("Admin Controlpanel (under implementation)", true, false, true, true);
		this.am = am;
		this.dp = dp;
		initUI();
	}
	
	private JComponent getUserRoleMatrixTable() {
		Object[] columnNames = {"Username", "Role", "Description"};
        data = DatabaseHandler.getUserRoleMatrix();
        var model = new DefaultTableModel(data, columnNames);
        tUserRoleMatrix = new JTable(model) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public Class getColumnClass(int column) {
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
        tUserRoleMatrix.setPreferredScrollableViewportSize(tUserRoleMatrix.getPreferredSize());
        var scrollPane = new JScrollPane(tUserRoleMatrix);
        return scrollPane;
	}
	
	private void editUser() {
		int row = tUserRoleMatrix.getSelectedRow();
		if (row != -1) {
			String username = String.valueOf(data[row][0]);
			dp.showUserEditorScreen(username);
			try {
				setIcon(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initUI() {
        var pane = getContentPane();
        var gl = new GroupLayout(pane);
        pane.setLayout(gl);
        
        var table = getUserRoleMatrixTable();
        var bttNew = new JButton("New User");
		bttNew.addActionListener(e -> {
			editUser();
		});
        var bttEdit = new JButton("Edit User");
		bttEdit.addActionListener(e -> {
			editUser();
		});
		var bttDelete = new JButton("Delete User");
		bttDelete.addActionListener(e -> {
			editUser();
		});
		
		gl.setVerticalGroup(
				gl.createParallelGroup()
				.addGroup(
						gl.createSequentialGroup()
						.addComponent(table, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(bttEdit)
				)
		);
		
		gl.setHorizontalGroup(
				gl.createSequentialGroup()
				.addGroup(
						gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(table)
						.addComponent(bttEdit)
				)
		);
		
        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);
        
        var headerSize = getInsets().top;
        setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height + headerSize * 3));
        
        pack();
    }
}
