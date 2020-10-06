package hu.kadarjeremiemanuel.jpicview;

import hu.kadarjeremiemanuel.jpicview.gui.MainWindow;

/**
 * Hello world!
 *
 */
public class App 
{
	private static MainWindow mw;
    public static void main( String[] args )
    {
        mw = new MainWindow();
        mw.setVisible(true);
    }
}
