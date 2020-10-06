/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.utils.SharedValues;

/**
 * @author atanii
 *
 */
public final class ImageBrowserInternalFrame extends JInternalFrame {
	private static AuthManager am;
	private static JpicDesktopPane dp;
	
	private static File dir;
	private static String[] files;
	
	private JList fileList;
	private JButton bttOpen;
	
	public ImageBrowserInternalFrame(AuthManager am, JpicDesktopPane dp) {
		super("Available Images", true, false, true, true);
		this.am = am;
		this.dp = dp;
		readFiles();
		initUI();
	}
	
	private void readFiles() {
		try {
			URI imagesRes = Thread.currentThread().getContextClassLoader().getResource(SharedValues.IMAGE_DIR_CLASSPATH).toURI();
			if (imagesRes != null) {
				dir = new File(imagesRes);
				files = dir.list();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private void openImage() {
		String selectedFile = (String) this.fileList.getSelectedValue();
		System.out.println(dir.getAbsoluteFile() + "\\" + selectedFile);
		dp.showImage(dir.getAbsoluteFile() + "\\" + selectedFile);
	}
	
	private void initUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		fileList = new JList<String>(files);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = 0;
		c.gridy = 0;
		add(new JScrollPane(fileList), c);
		
		bttOpen = new JButton("Open Image");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridx = 0;
		c.gridy = 1;
		add(bttOpen, c);
		
		bttOpen.addActionListener(e -> {
			openImage();
		});
		
		setSize(150, 300);
	}
}
