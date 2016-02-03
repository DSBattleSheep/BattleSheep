package org.sd.battlesheep.view.main;



/**
 * @author Giulio Biagini
 */
public interface MainFrameObserver
{
	public void onMainFrameExitClick();
	
	public void onMainFrameStartClick(boolean serverMode);
}