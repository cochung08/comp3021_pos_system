package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import core.Controller;
import core.entities.UserList;

/**
 * this class is to show the login GUI
 * 
 * @author John Chung
 * 
 */

public class LoginFrame extends JFrame
{

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton signinButton;
	public JButton connectionButton;
	public JButton failButton;

	Controller controller;

	public LoginFrame(Controller controller)
	{
		super();

		this.controller = controller;

		connectionButton = new JButton();
		connectionButton.setForeground(Color.BLUE);
		failButton = new JButton();
		failButton.setForeground(Color.RED);
		signinButton = new JButton("sign in");

		usernameField = new JTextField();
		JLabel usernameLabel = new JLabel("Username:");
		JPanel usernamePanel = new JPanel(new GridLayout(1, 2, 5, 5));
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameField);

		passwordField = new JPasswordField();
		JLabel passwordLabel = new JLabel("Password:");
		JPanel passwordPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);

		JPanel signinPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		signinPanel.add(signinButton);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(3, 1, 10, 10));
		centerPanel.add(usernamePanel);
		centerPanel.add(passwordPanel);
		centerPanel.add(signinPanel);


		setLayout(new BorderLayout(10, 10));
		add(centerPanel, BorderLayout.CENTER);
		add(failButton, BorderLayout.SOUTH);
		add(connectionButton, BorderLayout.NORTH);

		if (UserList.getInstance().isInitialized())
		{
			connectionButton.setText("Connection succeeded, please sign in!");
		} else
		{
			failButton.setText("Connection failed");
		}

		signinButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				verify();
			}
		});
	}

	private void verify()
	{
		String username = usernameField.getText();
		String password = passwordField.getText();

		if (controller.varify(username, password) == true)
		{
			signin(username);

		} else
		{
			failButton.setText("Invalid username or password");
		}
	}

	private void signin(String username)
	{
		controller.setUsername(username);
		POSFrame posFrame = new POSFrame(controller);
		posFrame.setSize(600, 600); 
		posFrame.setVisible(true);
		posFrame.setLocationRelativeTo(null);
		this.dispose();
	}
}
