/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

/**
 * @author atanii
 *
 */
public final class SingleImageViewer extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SingleImageViewer(String path) {
		super("Loading image...", true, true, true, true); 
		initUI(Paths.get(path));
	}
	
	private void initUI(Path path) {
		setTitle(path.getFileName().toString());
		setLayout(new FlowLayout());
		
		int prefWidth = 800;
		int prefHeight = 600;
		var ii = new ImageIcon(new ImageIcon(path.toString()).getImage().getScaledInstance(prefWidth, -1, Image.SCALE_DEFAULT));
		var pic = new JLabel(ii);
		add(pic);
		pic.setPreferredSize(new Dimension(prefWidth, prefHeight));
		
		pack();
        setVisible(true);
	}
}
