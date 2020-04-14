import java.util.Timer; 
import java.util.TimerTask;

public class GameTimer
{
	private int seconds;
	private Timer timer;
	private TimerTask task;

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

	public void start()
	{
		this.timer.scheduleAtFixedRate(task, 0, 1000);
	}

	public void stop()
	{
		this.timer.cancel();
	}
	
	public String getTime()
	{
		int min = (int)(this.seconds / 60);
		int sec = this.seconds - (min * 60);
		return String.format("%02d : %02d", min, sec);
	}

	private void incrementTimer()
	{
		this.seconds++;
	}
}
