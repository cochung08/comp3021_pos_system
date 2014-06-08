package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Controller;

/**
 * this class is to show the start GUI
 * 
 * @author John Chung
 * 
 */

public class StartFrame extends JFrame
{
	/**
	 * 
	 */
	public StartFrame()
	{
		JButton startButton = new JButton("Start");
		JLabel startLabel = new JLabel("Start a new POS");
		JPanel panel = new JPanel(new GridLayout(2, 1));

		panel.add(startLabel);
		panel.add(startButton);
		this.add(panel);

		/**
		 * this class is to create a new thread for each Gui and create a new
		 * thread for logic controller
		 * 
		 */
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Thread guiThread = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						Controller controller = new Controller();
						controller.setVailid(true);
						LoginFrame loginFrame = new LoginFrame(controller);
						loginFrame.setSize(300, 300); // set frame size
						loginFrame.setVisible(true);
						loginFrame.setLocationRelativeTo(null);

						Thread controllerThread = new Thread(controller);
						controllerThread.start();

					};
				});

				guiThread.start();
			}
		});
	}
}
