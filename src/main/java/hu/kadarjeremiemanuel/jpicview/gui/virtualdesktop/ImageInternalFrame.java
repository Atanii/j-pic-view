/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.Container;
import java.awt.FlowLayout;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

/**
 * @author atanii
 *
 */
public final class ImageInternalFrame extends JInternalFrame {
	private ImageIcon ii;
	private Path p;

	public ImageInternalFrame(String path) {
		super("Loading...", true, true, true, true);
		p = Paths.get(path); 
		initUI();
	}
	
	private void initUI() {
		setTitle(p.getFileName().toString());
		ii = new ImageIcon(p.toString());
		Container pane = getContentPane();
		JLabel pic = new JLabel(ii);
		pane.setLayout(new FlowLayout());
		add(pic);
		setSize(ii.getIconWidth(), ii.getIconHeight());
        setVisible(true);
	}
}
