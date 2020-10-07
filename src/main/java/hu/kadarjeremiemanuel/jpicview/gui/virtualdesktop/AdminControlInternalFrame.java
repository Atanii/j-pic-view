/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.bean.UserRoleModel;
import hu.kadarjeremiemanuel.jpicview.db.DatabaseHandler;

/**
 * @author atanii
 *
 */
public final class AdminControlInternalFrame extends JInternalFrame {
	private JTable table;
	private static AuthManager am;
	private static JpicDesktopPane dp;
	
	public AdminControlInternalFrame(AuthManager am, JpicDesktopPane dp) {
		super("Admin Controlpanel (under implementation)", true, false, true, true);
		this.am = am;
		this.dp = dp;
		initUI();
	}
	
	private void setTable() {
		Object[] columnNames = {"ID", "Username", "Rolename"};
        Object[][] data = DatabaseHandler.getUserRoles();
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model) {
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
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
	}
	
	private void initUI() {
		setTable();
		setSize(800, 600);
	}
}
