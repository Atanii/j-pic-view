package hu.kadarjeremiemanuel.jpicview;

import java.awt.EventQueue;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.gui.MainWindow;
import hu.kadarjeremiemanuel.jpicview.utils.JpicConstants;

/**
 * 
 * @author atanii
 *
 */
public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			var mw = new MainWindow(AuthManager.getInstance(), JpicConstants.TITLE);
			mw.setVisible(true);
		});
	}
}
