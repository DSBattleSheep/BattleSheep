package org.sd.battlesheep.view.main;



/**
 * Interfaccia per la comunicazione dal frame principale verso i propri
 * osservatori delle azioni di:
 * - click sul bottone per l'uscita;
 * - click sul bottone per l'avvio del programma.
 * 
 * @author Giulio Biagini
 */
public interface MainFrameObserver
{
	public void onMainFrameExitClick();
	
	public void onMainFrameStartClick(boolean serverMode);
}