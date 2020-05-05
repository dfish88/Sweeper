/*
*	Copyright (C) 2019-2020  Daniel Fisher
*
*	This program is free software: you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation, either version 3 of the License, or
*	(at your option) any later version.
*
*	This program is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU General Public License for more details.
*
*	You should have received a copy of the GNU General Public License
*	along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.lang.*;

import Model.GameTimer;

/**
* This class test the GameTimer class in the Model module
*
* @author Daniel Fisher
*/
public class TimerTest
{
	private GameTimer test; // The game timer to test
	private int delay; // How long to wait when testing timer

	/**
	* Sets up the test by creating a game timer and setting
	* the delay to 1.5 seconds (1500 ms)
	*/
	@Before
	public void setUp()
	{
		// Should allow for 2 calls to run()
		// in game timer. One call at 0ms and
		// another at 1000ms
		delay = 1500;
		test = new GameTimer();
	}

	/**
	* Tears down the test by setting the game timer to null
	*/
	@After
	public void tearDown()
	{
		test = null;
	}

	/**
	* Gets the time before starting the timer so the time 
	* should be 0
	*/
	@Test
	public void getTimeBeforeStart()
	{
		String t = test.getTime();
		String expectedTime = "00 : 00";
		assertEquals(expectedTime, t);
	}

	/**
	* Gets the time after starting and timer and wainting for
	* 2 seconds so the time should be 2 seconds
	*/
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
