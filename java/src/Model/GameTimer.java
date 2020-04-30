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

package Model;

import java.util.Timer; 
import java.util.TimerTask;

/**
* This class keeps track of the game time and is polled by the Presenter module
* and the results are displayed via the View module.
*
* @author Daniel Fisher
*/
public class GameTimer
{
	private int seconds;	// Seconds elapsed since started
	private Timer timer;	
	private TimerTask task;

	/**
	* Constructor that creates a timer, a timer task that will
	* execute every second once started and initilizes seconds to 0.
	*/
	public GameTimer()
	{
		this.seconds = 0;
		this.timer = new Timer();
		this.task = new TimerTask()
		{
			public void run()
			{
				GameTimer.this.incrementTimer();
			}
		};
	}

	/**
	* Starts the timer which will increment seconds every seconds.
	*/
	public void start()
	{
		this.timer.scheduleAtFixedRate(task, 0, 1000);
	}

	/**
	* Stops the timer.
	*/
	public void stop()
	{
		this.timer.cancel();
	}

	/**
	* Returns the current time as a string in the format "xx : yy" where
	* xx is the mintues elapsed in two decimal places and yy is the seconds
	* elapsed for the current minute in two decimal places.
	*
	* @return	the string representation of the current time.
	*/	
	public String getTime()
	{
		int min = (int)(this.seconds / 60);
		int sec = this.seconds - (min * 60);
		return String.format("%02d : %02d", min, sec);
	}

	/**
	* Increments the seconds elapsed since started. This is called by the
	* timer task every seconds when the timer is running.
	*/
	private void incrementTimer()
	{
		this.seconds++;
	}
}
