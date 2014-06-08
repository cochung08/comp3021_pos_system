package gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import conf.GlobalConfiguration;

/**
 * this class is to show the start GUI
 * 
 * @author John Chung
 * 
 */

public class Main
{
	public static void main(String[] args)
	{
		boolean finished;

		GlobalConfiguration conf = GlobalConfiguration.getInstance();
		finished = conf.load(null, null, null, null);
		if (!finished)
		{
			Logger.getAnonymousLogger().log(Level.SEVERE,
					"Loading application configuration failed!");
			// return;
		}

		StartFrame start = new StartFrame();
		start.setSize(150, 100); // set frame size
		start.setVisible(true);
		start.setLocationRelativeTo(null);

	}
}
