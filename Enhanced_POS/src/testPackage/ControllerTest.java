package testPackage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import conf.GlobalConfiguration;

import core.Controller;

public class ControllerTest
{

	Controller controller = new Controller();
	GlobalConfiguration conf = GlobalConfiguration.getInstance();
	boolean finished = conf.load(null, null, null, null);

	@Before
	public void setUp() throws Exception
	{
		;
	}

	@Test
	public void testVarify()
	{
		assertEquals(true,controller.varify("chunlin", "chunlin"));
	}
	
	
	@Test
	public void testSetStatus()
	{
		controller.setStatus(null);
	}

	@Test
	public void testSetRomoveItemName()
	{
		controller.setRomoveItemName("shoe");
		controller.add("shoe", 6);
		controller.remove("shoe");
	}

	@Test
	public void testSetAddItemName()
	{
		controller.setAddItemName("shoe");
	}

	@Test
	public void testSetAddItemNum()
	{
		controller.setAddItemNum(6);
	}

	@Test
	public void testGetValid()
	{
		assertEquals("true", controller.getValid(), false);
	}

	@Test
	public void testSetVailid()
	{
		controller.setVailid(false);
	}

	@Test
	public void testSetPaid()
	{
		controller.setPaid(5);
	}

	@Test
	public void testGetPaid()
	{
		controller.setPaid(5);
		assertEquals(controller.getPaid(), 5, 0.00001);
	}

	@Test
	public void testGetShoppingCartListModel()
	{
		assertNotNull(controller.getShoppingCartListModel());
	}

	@Test
	public void testAdd()
	{

		controller.add("pencil", 6);
	}

	@Test
	public void testRemove()
	{
		controller.remove("pencil");
	}

	@Test
	public void testClear()
	{
		controller.clear();
	}

	@Test
	public void testSetVIP()
	{
		controller.setVIP(false);
	}

	@Test
	public void testGetVIP()
	{
		assertEquals(controller.getVIP(), false);
	}

	@Test
	public void testSetEventDiscount()
	{
		controller.setEventDiscount(false);
	}

	@Test
	public void testGetEventDiscount()
	{
		assertEquals(controller.getEventDiscount(), false);
	}

	@Test
	public void testGetOrderList()
	{
		assertNotNull(controller.getOrderList());
	}

	@Test
	public void testSetUsername()
	{
		controller.setUsername("john");
	}

	@Test
	public void testGetUsername()
	{
		controller.setUsername("john");
		assertEquals(controller.getUsername(), "john");
	}

	@Test
	public void testGetTotalSum()
	{
		controller.add("shoe", 6);
		controller.setVIP(true);
		controller.setEventDiscount(true);

		assertFalse(controller.getTotalSum() == 100);
	}

	@Test
	public void testGetTotalDiscountedSum()
	{
		controller.add("shoe", 6);
		controller.setVIP(true);
		controller.setEventDiscount(true);
		assertFalse(controller.getTotalDiscountedSum() == 100);
	}

	@Test
	public void testGetDiscounted()
	{
		assertFalse(controller.getDiscounted() == 100);
	}

	@Test
	public void testGetRefund()
	{
		assertFalse(controller.getRefund() == 100);
	}

	@Test
	public void testRun01()
	{
		controller.add("shoe", 6);
		controller.setVailid(true);

		Thread temp = new Thread(controller);
		temp.start();

		try
		{
			controller.setPaid(100000);
			controller.setUsername("testCase!!");
			controller.setStatus("log");
			Thread.sleep(500);
			synchronized (controller)
			{
				controller.notify();
			}

		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testRun02()
	{
		controller.add("shoe", 6);
		controller.setVailid(true);

		Thread temp = new Thread(controller);
		temp.start();

		try
		{

			controller.setAddItemName("shoe");
			controller.setAddItemNum(6);
			controller.setStatus("add");
			Thread.sleep(500);
			synchronized (controller)
			{
				controller.notify();
			}

		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testRun03()
	{
		controller.add("shoe", 6);
		controller.setVailid(true);

		Thread temp = new Thread(controller);
		temp.start();

		try
		{

			controller.setStatus("clear");
			Thread.sleep(500);
			synchronized (controller)
			{
				controller.notify();
			}

		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testRun04()
	{
		controller.add("shoe", 6);
		controller.setVailid(true);

		Thread temp = new Thread(controller);
		temp.start();

		try
		{

			controller.setStatus("add");
			Thread.sleep(500);
			synchronized (controller)
			{
				controller.notify();
			}

		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testRun05()
	{
		controller.add("shoe", 6);
		controller.setVailid(true);

		Thread temp = new Thread(controller);
		temp.start();

		try
		{

			controller.setStatus("remove");
			Thread.sleep(500);
			synchronized (controller)
			{
				controller.notify();
			}

		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testRun06()
	{
		controller.add("shoe", 6);
		controller.setVailid(true);

		Thread temp = new Thread(controller);
		temp.start();

		try
		{

			controller.setStatus("no");
			Thread.sleep(500);
			synchronized (controller)
			{
				controller.notify();
			}

		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
