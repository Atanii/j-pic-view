/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.FlowLayout;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

/**
 * @author atanii
 * 
 *
 */
public class Logout extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Logout(JpicDesktopPane dp) {
		super("Logout", false, false, false, true);
		initUI(dp);
	}
	
	private void initUI(JpicDesktopPane dp) {
		setLayout(new FlowLayout());
		
		var bttLogout = new JButton("Logout");
		bttLogout.addActionListener(e -> {
			dp.reset();
		});
		add(bttLogout);
		
		setSize(100, 70);
		setVisible(true);
		try {
			setIcon(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
	}

}
