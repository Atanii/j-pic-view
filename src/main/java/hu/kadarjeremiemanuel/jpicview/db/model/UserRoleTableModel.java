/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.db.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * @author atanii
 *
 */
public class UserRoleTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "Username", "Role", "Description" };
	private ArrayList<UserRoleModel> data;

	public UserRoleTableModel(ArrayList<UserRoleModel> data) {
		this.data = data;
	}

	@Override
	public int getRowCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return data.get(rowIndex).username;
		case 1:
			return data.get(rowIndex).rolename;
		case 2:
			return data.get(rowIndex).description;
		default:
			return null;
		}
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Class<?> getColumnClass(int col) {
		return String.class;
	}

}
