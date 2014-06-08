package testPackage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.Logger;

public class LoggerTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testGetInstance()
	{
		assertNotNull(Logger.getInstance());
	}

	@Test
	public void testLog()
	{
		Logger.getInstance().log("\n");
	}

}
