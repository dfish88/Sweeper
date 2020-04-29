import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.lang.*;

import Model.GameTimer;

public class TimerTest
{
	private GameTimer test;
	private int delay;

	@Before
	public void setUp()
	{
		// Should allow for 2 calls to run()
		// in game timer. One call at 0ms and
		// another at 1000ms
		delay = 1500;
		test = new GameTimer();
	}

	@After
	public void tearDown()
	{
		test = null;
	}

	@Test
	public void getTimeBeforeStart()
	{
		String t = test.getTime();
		String expectedTime = "00 : 00";
		assertEquals(expectedTime, t);
	}

	@Test 
	public void getTimeAfter2Seconds()
	{
		test.start();
		
		try
		{
			Thread.sleep(delay);
		}
		catch (InterruptedException e)
		{
			System.out.println(e);
		}

		test.stop();
		String t = test.getTime();
		String expectedTime = "00 : 02";
		assertEquals(expectedTime, t);
	}
}
