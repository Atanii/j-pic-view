/**
 * 
 */
package hu.kadarjeremiemanuel.jpicview.gui;

import java.awt.Container;

import javax.swing.JFrame;

import hu.kadarjeremiemanuel.jpicview.gui.virtualdesktop.JpicDesktopPane;

/**
 * @author atanii
 *
 */
public final class MainWindow extends JFrame {
	public MainWindow() {
		JpicDesktopPane dkPane = new JpicDesktopPane();
		Container contentPane = getContentPane();
	}
}