package org.sd.battlesheep.view.main;



import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.sd.battlesheep.view.AFrame;



/**
 * Classe per il frame principale.
 * 
 * Questo frame mostra dei bottoni che permettono di scegliere in che modalità
 * avviare il programma, se come server (lobby) o come client (gioco).
 * 
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class MainFrame extends AFrame
{
	/**
	 * label per la stringa che richiede in quale modalità avviare il programma
	 */
	private JLabel labelMode;
	
	
	
	/**
	 * radiobutton per scegliere di avviare la lobby (server)
	 */
	private JRadioButton radioServer;
	
	/**
	 * radiobutton per scegliere di avviare il gioco (client)
	 */
	private JRadioButton radioClient;
	
	
	
	/**
	 * bottone per uscire dal programma
	 */
	private JButton buttonExit;
	
	/**
	 * bottone per avviare il programma nella modalità scelta
	 */
	private JButton buttonOk;
	
	
	
	/**
	 * l'osservatore delle azioni sul frame
	 */
	private MainFrameObserver observer;
	
	
	
	/**
	 * crea un frame per la scelta della modalità in cui avviare il programma,
	 * se come server (lobby) o come client (gioco)
	 * 
	 * @param observer - l'osservatore delle azioni sul frame
	 */
	public MainFrame(MainFrameObserver observer) {
		super(new GridBagLayout());
		
		this.observer = observer;
		
		/*
		 * label che richiede di selezionare una modalità
		 */
		
		labelMode = new JLabel("Start the program as:");
		
		/*
		 * radiobuttons per le varie modalità
		 */
		
		radioServer = new JRadioButton("server", false);
		radioServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionServer();
			}
		});
		
		radioClient = new JRadioButton("client", true);
		radioClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionClient();
			}
		});
		
		/*
		 * bottoni per l'uscita o la conferma
		 */
		
		buttonExit = new JButton("Exit");
		buttonExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionExit();
			}
		});
		
		buttonOk = new JButton("OK");
		buttonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionOk();
			}
		});
		
		/*
		 * aggiunta degli elementi al frame
		 */
		
		add(labelMode, new GridBagConstraints(0, 0, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 5, 10), 0, 0));
		add(radioServer, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 5, 5), 0, 0));
		add(radioClient, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 10), 0, 0));
		add(buttonExit, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 10, 10, 5), 0, 0));
		add(buttonOk, new GridBagConstraints(1, 2, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 10, 10), 0, 0));
		
		pack();
		
		setVisible(true);
	}
	
	
	
	private void actionServer() {
		radioServer.setSelected(true);
		radioClient.setSelected(false);
	}
	
	private void actionClient() {
		radioClient.setSelected(true);
		radioServer.setSelected(false);
	}
	
	private void actionExit() {
		observer.onMainFrameExitClick();
	}
	
	private void actionOk() {
		observer.onMainFrameOkClick(radioServer.isSelected());
	}
}