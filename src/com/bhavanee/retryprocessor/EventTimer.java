package com.bhavanee.retryprocessor;

import java.util.Timer;
import java.util.TimerTask;

public abstract class EventTimer 
{
	Timer timer;
	boolean running=false;
	public int interval = 60000;


	public void Enabled(boolean value)
	{
		if (value == true)
		{
			if (running == true) 
				return;
			timer = new Timer();
			timer.schedule(new RetryTask(), interval,interval);
			running = true;
		}
		else
		{
			if (running == false)
				return;
			timer.cancel();
			timer.purge();
			running = false;
		}
	}

	protected abstract void OnFailureRetry();

	class RetryTask extends TimerTask 
	{
		public void run() 
		{ 
			OnFailureRetry();
		}
	}
}
