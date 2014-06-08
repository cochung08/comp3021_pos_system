package core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;

import conf.GlobalConfiguration;
import conf.discount.Discount;

import core.entities.Item;
import core.entities.ItemList;
import core.entities.UserList;

/**
 * this class is running on its own thread to monitor the users,operation
 * 
 * @author John Chung
 * 
 */

public class Controller implements Runnable
{

	private String removeItemName;
	private String addItemName;
	private Integer addItemNum;

	private DefaultListModel shoppingCartListModel;
	private String status;
	private boolean valid;

	private boolean isVIP;
	private boolean isEventDiscount;

	private String username;
	private HashMap<String, Integer> orderList;

	public float paid = 0;

	public boolean varify(String username, String password)
	{
		return UserList.getInstance().verifyUser(username, password);
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public void setRomoveItemName(String removeItemName)
	{
		this.removeItemName = removeItemName;
	}

	public void setAddItemName(String addItemName)
	{
		this.addItemName = addItemName;
	}

	public void setAddItemNum(Integer addItemNum)
	{
		this.addItemNum = addItemNum;
	}

	public boolean getValid()
	{
		return this.valid;
	}

	public void setVailid(boolean valid)
	{
		this.valid = valid;

	}

	public void setPaid(float paid)
	{
		this.paid = paid;
	}

	public float getPaid()
	{
		return this.paid;
	}

	public Controller()
	{
		orderList = new HashMap<String, Integer>();
		shoppingCartListModel = new DefaultListModel();
	}

	public DefaultListModel getShoppingCartListModel()
	{
		return shoppingCartListModel;
	}

	public void add(String itemName, Integer itemNum)
	{
		orderList.put(itemName, itemNum);
		updateShoppingCart();
	}

	public void remove(String itemName)
	{
		orderList.remove(itemName);
		updateShoppingCart();
	}

	public void clear()
	{
		orderList.clear();
		updateShoppingCart();
	}

	/**
	 * update the content of shoppingCartList
	 * 
	 */
	public void updateShoppingCart()
	{

		Iterator<Entry<String, Integer>> iteratorOfOrderList = orderList
				.entrySet().iterator();

		shoppingCartListModel.removeAllElements();

		while (iteratorOfOrderList.hasNext())
		{

			Map.Entry<String, Integer> pairs = (Map.Entry<String, Integer>) iteratorOfOrderList
					.next();

			String itemName = pairs.getKey();
			int itemNum = pairs.getValue();
			float itemPrice = ItemList.getInstance().getItemByName(itemName)
					.getPrice();
			float total = itemPrice * itemNum;

			String info = itemName + " " + itemNum + "=" + total;
			shoppingCartListModel.addElement(info);

		}
	}

	public void setVIP(boolean isVIP)
	{
		this.isVIP = isVIP;
	}

	public boolean getVIP()
	{
		return isVIP;
	}

	public void setEventDiscount(boolean isEventDiscount)
	{
		this.isEventDiscount = isEventDiscount;
	}

	public boolean getEventDiscount()
	{
		return isEventDiscount;
	}

	public HashMap<String, Integer> getOrderList()
	{
		return this.orderList;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * calculate the total cost and return
	 * 
	 */
	public float getTotalSum()
	{
		float total = 0;

		Iterator<Entry<String, Integer>> iteratorOfOrderList = orderList
				.entrySet().iterator();

		while (iteratorOfOrderList.hasNext())
		{
			Map.Entry<String, Integer> pairs = (Map.Entry<String, Integer>) iteratorOfOrderList
					.next();

			String itemName = pairs.getKey();

			Item item = ItemList.getInstance().getItemByName(itemName);

			float subTotal = item.getPrice() * pairs.getValue();
			total = total + subTotal;

		}

		return total;
	}

	/**
	 * calculate the total cost after discounted and return
	 * 
	 */

	public float getTotalDiscountedSum()
	{
		float discountedTotal = 0;

		Iterator<Entry<String, Integer>> iteratorOfOrderList = orderList
				.entrySet().iterator();

		while (iteratorOfOrderList.hasNext())
		{
			Map.Entry<String, Integer> pairs = (Map.Entry<String, Integer>) iteratorOfOrderList
					.next();

			String itemName = pairs.getKey();

			Item item = ItemList.getInstance().getItemByName(itemName);
			Discount discount = item.getDiscount();
			float subTotal = item.getPrice() * pairs.getValue();
			if (discount != null)
			{
				subTotal = subTotal * (1 - discount.discount());
			}
			discountedTotal = discountedTotal + subTotal;
		}

		if (getTotalSum() > GlobalConfiguration.getInstance()
				.getSalesRequirement()
				|| getTotalSum() == GlobalConfiguration.getInstance()
						.getSalesRequirement())
		{
			discountedTotal = discountedTotal
					* (1 - GlobalConfiguration.getInstance().getSalesDiscount()
							.discount());
		}
		if (isVIP == true)
		{
			discountedTotal = discountedTotal
					* (1 - GlobalConfiguration.getInstance()
							.getCustomerDiscount().discount());
		}
		if (isEventDiscount == true)
		{
			discountedTotal = discountedTotal
					* (1 - GlobalConfiguration.getInstance()
							.getGlobalDiscount().discount());
		}
		return discountedTotal;
	}

	/**
	 * calculate the discounted number and return
	 * 
	 */

	public float getDiscounted()
	{
		return getTotalSum() - getTotalDiscountedSum();
	}

	/**
	 * calculate the refund and return
	 * 
	 */

	public float getRefund()
	{
		try
		{
			float totalDiscountedSum = getTotalDiscountedSum();
			float paidAmount = paid;
			float refundAmount = paidAmount - totalDiscountedSum;
			return refundAmount;
		}

		catch (Exception ex)
		{
			return -1;
		}

	}

	/**
	 * log the customers' list of purchased item
	 * 
	 */
	private void log()
	{
		String msg = "Purchase:\n" + username + " " + isVIP + " "
				+ getTotalSum() + " " + getDiscounted() + " " + paid + " "
				+ getRefund() + "\n";

		Iterator<Entry<String, Integer>> iteratorOfOrderList = orderList
				.entrySet().iterator();

		while (iteratorOfOrderList.hasNext())
		{
			Map.Entry<String, Integer> pairs = (Map.Entry<String, Integer>) iteratorOfOrderList
					.next();

			String itemName = pairs.getKey();
			int itemNum = pairs.getValue();

			Item temp = ItemList.getInstance().getItemByName(itemName);
			String itemID = temp.getItemID();

			msg = msg + itemID + " " + itemNum + ", ";
		}

		msg = msg + "\n\n";

		Logger.getInstance().log(msg);
	}

	/**
	 * use to monitor the customers' operation and respond interact with GUI
	 */
	@Override
	public void run()
	{
		while (valid == true)
		{
			// lock the object so that each time only one operation is allowed
			// to keep thread-safe
			synchronized (this)
			{
				try
				{
					this.wait();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				//monitor the customers' operation
				if (status.equals("log"))
				{
					log();
				}

				else if (status.equals("clear"))
				{
					clear();
				} else if (status.equals("remove"))
				{
					remove(removeItemName);
				} else if (status.equals("add"))
				{
					add(addItemName, addItemNum);
				}

			}
		}

	}
}
