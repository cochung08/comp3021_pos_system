package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import core.Controller;
import core.entities.Item;
import core.entities.ItemList;

/**
 * this class is to show the POS GUI
 * 
 * @author John Chung
 * 
 */

public class POSFrame extends JFrame
{

	private JLabel idLabel;
	private JTextField idInputField;

	private JLabel amountLabel;
	private JTextField amountInputField;
	private JButton addByIdButton;

	private JLabel amountLabel2;
	private JTextField amountInputField2;
	private JButton addButton;
	private JButton removeButton;
	private JButton clearButton;

	public JButton connectionButton;
	public JButton failButton;

	public JLabel vipLabel;
	public JCheckBox vipCheckBox;
	public JLabel eventDiscountLabel;
	private JCheckBox eventDiscountCheckBox;
	public JButton payButton;

	private JList productList;
	private JList shoppingCartList;

	private DefaultListModel productListModel;
	private DefaultListModel shoppingCartListModel;

	private HashMap<String, Integer> orderList;
	Controller controller;

	public POSFrame(Controller controller)
	{
		this.controller = controller;
		orderList = controller.getOrderList();

		connectionButton = new JButton();
		connectionButton.setForeground(Color.BLUE);
		failButton = new JButton();
		failButton.setForeground(Color.RED);

		idLabel = new JLabel("ID:");
		idInputField = new JTextField();
		amountLabel = new JLabel("Amount:");
		amountInputField = new JTextField();
		addByIdButton = new JButton("Add");

		JPanel idPanel = new JPanel();
		idPanel.setLayout(new GridLayout(1, 5, 5, 0));
		idPanel.setBorder(new EmptyBorder(0, 60, 0, 60));
		idPanel.add(idLabel);
		idPanel.add(idInputField);
		idPanel.add(amountLabel);
		idPanel.add(amountInputField);
		idPanel.add(addByIdButton);

		JPanel Row12Panel = new JPanel();
		Row12Panel.setLayout(new GridLayout(2, 1, 5, 5));
		Row12Panel.add(connectionButton);
		Row12Panel.add(idPanel);

		amountLabel2 = new JLabel("Amount:");
		amountInputField2 = new JTextField();
		addButton = new JButton("-->");
		removeButton = new JButton("<--");
		clearButton = new JButton("Clear");

		JPanel addPanel = new JPanel();
		addPanel.setLayout(new GridLayout(5, 1, 5, 5));
		addPanel.add(amountLabel2);
		addPanel.add(amountInputField2);
		addPanel.add(addButton);
		addPanel.add(removeButton);
		addPanel.add(clearButton);

		vipLabel = new JLabel("IS VIP?");
		vipCheckBox = new JCheckBox();
		eventDiscountLabel = new JLabel("IS EventDiscounted");
		eventDiscountCheckBox = new JCheckBox();
		payButton = new JButton("Pay");

		JPanel payPanel = new JPanel();
		payPanel.setLayout(new GridLayout(1, 5, 1, 1));
		payPanel.add(vipLabel);
		payPanel.add(vipCheckBox);
		payPanel.add(eventDiscountLabel);
		payPanel.add(eventDiscountCheckBox);
		payPanel.add(payButton);

		JPanel Row45Panel = new JPanel();
		Row45Panel.setLayout(new BorderLayout(10, 10));
		Row45Panel.add(payPanel, BorderLayout.CENTER);
		Row45Panel.add(failButton, BorderLayout.SOUTH);

		productListModel = new DefaultListModel();

		productList = new JList(productListModel)
		{
			@Override
			public Dimension getPreferredScrollableViewportSize()
			{
				return new Dimension(200, 400);
			}
		};
	

		JScrollPane selectionPane = new JScrollPane(productList);
		productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		productList.setVisibleRowCount(20);

		//

		JPanel selectionPanel = new JPanel();
		selectionPanel.add(selectionPane);
		selectionPanel.setBorder(BorderFactory
				.createTitledBorder("Select Products"));
		// selectionPanel.setSize(100, 300);

		shoppingCartListModel = controller.getShoppingCartListModel();
		shoppingCartList = new JList(shoppingCartListModel)
		{
			@Override
			public Dimension getPreferredScrollableViewportSize()
			{
				return new Dimension(200, 400);
			}
		};

		JScrollPane shoppingCartPane = new JScrollPane(shoppingCartList);
		shoppingCartList.setVisibleRowCount(20);

		JPanel shoppingCarPanel = new JPanel();
		shoppingCarPanel.add(shoppingCartPane);
		shoppingCarPanel.setBorder(BorderFactory
				.createTitledBorder("Shopping Cart:"));

		setLayout(new BorderLayout(10, 10));
		this.add(Row12Panel, BorderLayout.NORTH);
		this.add(selectionPanel, BorderLayout.WEST);
		this.add(addPanel, BorderLayout.CENTER);
		this.add(shoppingCarPanel, BorderLayout.EAST);
		this.add(Row45Panel, BorderLayout.SOUTH);

		connectionButton.setText("Recod sale information succeeded by user "
				+ controller.getUsername() + '!');

		loadProductList();

		addByIdButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String id = idInputField.getText();
				String amountStr = amountInputField.getText();
				Integer amountNum;

				String itemName = null;
				try
				{
					Item temp = ItemList.getInstance().getItemById(id);
					itemName = temp.getItemName();
					// System.out.println(temp);
				} catch (Exception e)
				{
					failButton.setText("Prodcut " + id + " does not exist");
					return;
				}

				try
				{
					amountNum = Integer.parseInt(amountStr);
					if (amountNum <= 0)
					{
						failButton.setText("Please input correct amount");
					} else
					{
						String selectedItemName = itemName;
						Item selectedItem = ItemList.getInstance()
								.getItemByName(selectedItemName);
						int num = amountNum;
						addToOrderList(selectedItemName, num);
						failButton.setText("succeed adding");
					}

				} catch (Exception e)
				{
					failButton.setText("Please input correct amount");
				}

			}

		});

		addButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{

				String amountStr = amountInputField2.getText();
				Integer amountNum;
				try
				{
					amountNum = Integer.parseInt(amountStr);
					if (amountNum <= 0)
					{
						failButton.setText("Please input correct amount");
					} else
					{
						String selectedItemName = (String) productList
								.getSelectedValue();
						if (selectedItemName != null)
						{

							Item selectedItem = ItemList.getInstance()
									.getItemByName(selectedItemName);

							int num = amountNum;
							addToOrderList(selectedItemName, num);

							failButton.setText("succeed adding");
						}
						else
						{
							failButton.setText("please select items");
						}

					}

				} catch (Exception e)
				{
					failButton.setText("Please input correct amount");
				}

			}

		});

		removeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String selectedItemName = (String) shoppingCartList
						.getSelectedValue();

				if (selectedItemName != null)
				{
					String[] temp = selectedItemName.split(" ");

					removeFromOrderList(temp[0]);
				}
			}
		});

		clearButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				removeAllFromOrderList();
			}
		});

		payButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (shoppingCartListModel.isEmpty() == true)
				{
					failButton.setText("Invalid sales information");
				} else
					jumpToPayFrame();
			}
		});

	}

	private void jumpToPayFrame()
	{
		controller.setVIP(vipCheckBox.isSelected());
		controller.setEventDiscount(eventDiscountCheckBox.isSelected());

		PayFrame payFrame = new PayFrame(controller);
		payFrame.setSize(500, 500); // set frame size
		payFrame.setVisible(true);
		payFrame.setLocationRelativeTo(null);
		this.dispose();

	}

	private void loadProductList()
	{

		HashMap<String, Item> products = ItemList.getInstance().getItems();
		Iterator<Entry<String, Item>> iteratorOfProducts = products.entrySet()
				.iterator();

		while (iteratorOfProducts.hasNext())
		{
			Map.Entry<String, Item> pairs = (Map.Entry<String, Item>) iteratorOfProducts
					.next();
			productListModel.addElement(pairs.getValue().getItemName());
		}

	}

	private void addToOrderList(String itemName, Integer itemNum)
	{
		// controller.add(itemName, itemNum);
		// updateShoppingCart();

		controller.setStatus("add");
		controller.setAddItemName(itemName);
		controller.setAddItemNum(itemNum);
		synchronized (controller)
		{
			controller.notify();
		}

	}

	private void removeFromOrderList(String itemName)
	{
		// controller.remove(itemName);
		// updateShoppingCart();

		controller.setStatus("remove");
		controller.setRomoveItemName(itemName);
		synchronized (controller)
		{
			controller.notify();
		}
	}

	private void removeAllFromOrderList()
	{
		// controller.clear();
		controller.setStatus("clear");
		synchronized (controller)
		{
			controller.notify();
		}

		// updateShoppingCart();
	}

}
