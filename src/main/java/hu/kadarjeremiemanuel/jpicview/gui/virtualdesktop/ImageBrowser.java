/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop;

import java.awt.Dimension;
import java.io.File;
import java.io.FileFilter;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.auth.PermissionsEnum;

/**
 * @author atanii
 *
 */
public final class ImageBrowser extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AuthManager am;
	private JpicDesktopPane dp;
	
	private File dir;
	private File[] files;
	
	private JList<File> fileList;
	
	private String path;
	
	public ImageBrowser(AuthManager am, JpicDesktopPane dp, String path) {
		super("Available Images", true, false, true, true);
		this.am = am;
		this.dp = dp;
		this.path = path;
		readFiles();
		initUI();
	}
	
	private void initUI() {
		fileList = new JList<File>(files);
		var fileListWithScrollpane = new JScrollPane(fileList);
		
		var bttOpen = new JButton("Open Image");
		bttOpen.addActionListener(e -> {
			openImage();
		});
		
		var pane = getContentPane();
        var gl = new GroupLayout(pane);
        pane.setLayout(gl);
		
		gl.setHorizontalGroup(
				gl.createParallelGroup()
				.addGroup(
						gl.createSequentialGroup()
						.addComponent(fileListWithScrollpane)
				)
				.addGroup(
						gl.createSequentialGroup()
						.addComponent(bttOpen)
				)
		);
		
		gl.setVerticalGroup(
				gl.createSequentialGroup()
				.addGroup(
						gl.createParallelGroup()
						.addComponent(fileListWithScrollpane)
				)
				.addGroup(
						gl.createParallelGroup()
						.addComponent(bttOpen)
				)
		);
		
		gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);
        
        var headerSize = getInsets().top;
        setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height + headerSize * 3));
		
		pack();
		
		setVisible(true);
	}
	
	private void readFiles() {
		dir = new File(path);
		if (dir.exists() && dir.isDirectory()) {
			files = dir.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					String name = pathname.getName();
					int index = name.lastIndexOf('.');
					if(index > 0) {
						String extension = name.substring(index + 1);
						if ( (extension.equals("png") && am.checkPermission(PermissionsEnum.PNG.getPermission()))
						 ||  (extension.equals("jpg") && am.checkPermission(PermissionsEnum.JPG.getPermission()))
						 ||  (extension.equals("gif") && am.checkPermission(PermissionsEnum.GIF.getPermission()))) {
							return true;
						}
					}
					return false;
				}
			});
		}
	}
	
	private void openImage() {
		var selectedFile = (File) this.fileList.getSelectedValue();
		dp.showImageInternalFrame(selectedFile.getAbsolutePath());
	}
}
