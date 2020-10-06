package hu.kadarjeremiemanuel.jpicview;

import hu.kadarjeremiemanuel.jpicview.auth.AuthManager;
import hu.kadarjeremiemanuel.jpicview.gui.MainWindow;

public class App 
{
	private static MainWindow mw;
	private static AuthManager am;
	
    public static void main( String[] args )
    {
    	am = AuthManager.getInstance();
        mw = new MainWindow(am, "JpicView");
        mw.setVisible(true);
    }
}
