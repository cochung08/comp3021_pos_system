package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import conf.GlobalConfiguration;

import core.Controller;
import core.entities.Item;
import core.entities.ItemList;

/**
 * this class is to show the pay GUI
 * 
 * @author John Chung
 * 
 */

public class PayFrame extends JFrame
{
	DecimalFormat df;

	Controller controller;
	private HashMap<String, Integer> orderList;

	private JButton connectionButton;
	private JButton failButton;
	private JButton submitButton;

	private JTextField totalPriceField;
	private JTextField discountedField;
	private JTextField totalSumField;
	private JTextField paidField;
	private JTextField refundField;

	private JList shoppingCartList;

	public PayFrame(Controller controller)
	{
		// this.setLayout(new BorderLayout(50, 50));
		df = new DecimalFormat();
		df.setMaximumFractionDigits(1);

		this.controller = controller;
		orderList = controller.getOrderList();

		connectionButton = new JButton("Please pay the follwing:");
		connectionButton.setForeground(Color.BLUE);
		failButton = new JButton();
		failButton.setForeground(Color.RED);
		submitButton = new JButton("submit");

		totalPriceField = new JTextField();
		totalPriceField.setEnabled(false);
		totalPriceField.setSize(10, 5);
		discountedField = new JTextField();
		discountedField.setSize(10, 3);
		discountedField.setEnabled(false);
		totalSumField = new JTextField();
		totalSumField.setEnabled(false);

		paidField = new JTextField();
		paidField.setText("0.0");
		refundField = new JTextField("-");
		refundField.setEnabled(false);

		JPanel payPanel = new JPanel(new GridLayout(5, 2, 5, 5));
		String currency = GlobalConfiguration.getInstance().getCurrency()
				.show();

		JLabel totalPriceLabel = new JLabel("Total Price:" + currency);
		JLabel discountedLabel = new JLabel("discounted:" + currency);
		JLabel totalSumLabel = new JLabel("Total Sum:" + currency);
		JLabel paidLabel = new JLabel("Paid:" + currency);
		JLabel refundLabel = new JLabel("Refund:" + currency);

		payPanel.add(totalPriceLabel);
		payPanel.add(totalPriceField);
		payPanel.add(discountedLabel);
		payPanel.add(discountedField);
		payPanel.add(totalSumLabel);
		payPanel.add(totalSumField);
		payPanel.add(paidLabel);
		payPanel.add(paidField);
		payPanel.add(refundLabel);
		payPanel.add(refundField);

		JPanel paySubmitPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		paySubmitPanel.add(payPanel);
		paySubmitPanel.add(submitButton);

		// updateShoppingCart();
		DefaultListModel shoppingCartListModel = controller
				.getShoppingCartListModel();
		shoppingCartList = new JList(shoppingCartListModel);
		controller.updateShoppingCart();

		JPanel row2Panel = new JPanel();
		row2Panel.setLayout(new BorderLayout(20, 5));
		row2Panel.add(shoppingCartList, BorderLayout.WEST);
		row2Panel.add(paySubmitPanel, BorderLayout.EAST);

		this.setLayout(new BorderLayout(50, 50));
		this.add(connectionButton, BorderLayout.NORTH);
		this.add(row2Panel, BorderLayout.CENTER);
		this.add(failButton, BorderLayout.SOUTH);

		float totalSum = getTotalSum();
		float totalDiscountedSum = getTotalDiscountedSum();
		float discountedSum = totalSum - totalDiscountedSum;

		String totalSumStr = df.format(totalSum);
		String totalDiscountedSumStr = df.format(totalDiscountedSum);
		String discountedSumStr = df.format(discountedSum);

		// String totalSumStr = String.valueOf(totalSum);
		// String totalDiscountedSumStr = String.valueOf(totalDiscountedSum);
		// String discountedSumStr = String.valueOf(discountedSum);

		totalPriceField.setText(totalSumStr);
		totalSumField.setText(totalDiscountedSumStr);
		discountedField.setText(discountedSumStr);

		submitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				float refund = getRefund();
				if (refund < 0)
				{
					failButton.setText("not enough money!");
				} else
				{

					reCreatePosFrame();

				}

			}
		});

		paidField.addKeyListener(new KeyListener()
		{

			public void keyTyped(KeyEvent e)
			{

			}

			public void keyPressed(KeyEvent e)
			{

			}

			public void keyReleased(KeyEvent e)
			{
				try
				{
					setRefund();
					float refund = getRefund();
					if (refund >= 0)
					{
						refundField.setText(df.format(refund));
						failButton.setText("");
					} else
					{
						failButton.setText("Please enter correct amount");
					}
				} catch (Exception w)
				{
					failButton.setText("Please enter correct amount");
				}

			}

		});

	}

	private void reCreatePosFrame()
	{
		// controller.log();

		controller.setStatus("log");
		synchronized (controller)
		{
			controller.notify();
		}
		// try
		// {
		// Thread.sleep(1000);
		// } catch (InterruptedException e)
		// {
		// e.printStackTrace();
		// }
		//
		// controller.setEventDiscount(false);
		// controller.setVIP(false);
		// controller.clear();

		// after submit,create another thread for the control part
		// so that the log procedure and new transaction can run concurrently
		Controller xcontroller = new Controller();
		xcontroller.setUsername(controller.getUsername());
		xcontroller.setVailid(true);
		Thread controllerThread = new Thread(xcontroller);
		controllerThread.start();

		POSFrame posFrame = new POSFrame(xcontroller);
		posFrame.setSize(600, 600); // set frame size
		posFrame.setVisible(true);
		posFrame.setLocationRelativeTo(null);
		this.dispose();
	}

	private float getRefund()
	{

		return controller.getRefund();

	}

	private void setRefund()
	{
		float paidAmount = Float.valueOf(paidField.getText());
		controller.setPaid(paidAmount);
	}

	private float getTotalSum()
	{
		return controller.getTotalSum();
	}

	private float getTotalDiscountedSum()
	{
		return controller.getTotalDiscountedSum();
	}

	// private void updateShoppingCart()
	// {
	// DefaultListModel shoppingCartListModel = new DefaultListModel();
	//
	// Iterator<Entry<String, Integer>> iteratorOfOrderList = orderList
	// .entrySet().iterator();
	//
	// while (iteratorOfOrderList.hasNext())
	// {
	//
	// Map.Entry<String, Integer> pairs = (Map.Entry<String, Integer>)
	// iteratorOfOrderList
	// .next();
	//
	// String itemName = pairs.getKey();
	// int itemNum = pairs.getValue();
	// float itemPrice = ItemList.getInstance().getItemByName(itemName)
	// .getPrice();
	// float total = itemPrice * itemNum;
	//
	// String info = itemName + '*' + itemNum + '=' + total;
	// shoppingCartListModel.addElement(info);
	//
	// }
	//
	// shoppingCartList = new JList(shoppingCartListModel);
	// }

}
