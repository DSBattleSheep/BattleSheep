package org.sd.battlesheep.view.lobby;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.sd.battlesheep.communication.server.LobbyStartObserver;


/**
 * 
 * 
 * @author DSBattleSheep
 */
@SuppressWarnings("serial")
public class LobbyFrame extends JFrame implements LobbyJoinFrameObserver 
{

	private final static String LOBBY_FRAME_TITLE = "Lobby @ ";
	
	private final static int LOBBY_FRAME_WIDTH = 400;
	
	private final static int LOBBY_FRAME_HEIGHT = 300;
	
	private final static String[] TABLE_COLUMN_NAMES = {"Username", "host", "port"};
	
	
	
	private JPanel loadingPanel;
	
	private JPanel tablePanel;
	
	private DefaultTableModel model;

	private LobbyStartObserver onStartObserver;
	
	
	
	public LobbyFrame(String host, int port) {
		super(LOBBY_FRAME_TITLE + host + ":" + port);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(LOBBY_FRAME_WIDTH, LOBBY_FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(false);

		// creo il pannello per l'attesa dei giocatori
		loadingPanel = new JPanel();
		loadingPanel.setLayout(new BorderLayout());
		loadingPanel.setBackground(Color.WHITE);
		loadingPanel.setOpaque(true);
		
		// carico la gif animata, creo la label e le metto al centro del pannello 		
		ImageIcon loading = new ImageIcon("imgs/ajax-loader.gif");
		loadingPanel.add(new JLabel("Waiting for clients...", loading, JLabel.CENTER));
		
		
		// creo il pannello per la tabella dei dati dei giocatori
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.setBackground(Color.WHITE);
		tablePanel.setOpaque(true);
		
		// creo il modello con cui devono essere aggiunte le righe nella tabella, la tabella e lo scrollPane. 
		model = new DefaultTableModel(TABLE_COLUMN_NAMES, 0);
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		
		// aggiungo il pulsante per far partire il gioco ed aggiungo il listener 
		JButton addButton = new JButton("Start game");
		addButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {			
					@Override
					public void run() {
						onStartObserver.onLobbyStartClick();				
					}
				}).start();		
			}
		});

		// do una dimensione alla tabella affinchè non vada a coprire il pulsante
		Dimension tableSize = table.getPreferredSize();
		Dimension addButtonSize = addButton.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(tableSize.width, 
				LOBBY_FRAME_HEIGHT - addButtonSize.height - 25)); // FIXME: 25 pixel 
		
		// imposto una larghezza migliore per le singole colonne
		table.getColumnModel().getColumn(0).setPreferredWidth(Math.round(tableSize.width*0.50f));
		table.getColumnModel().getColumn(1).setPreferredWidth(Math.round(tableSize.width*0.40f));
		table.getColumnModel().getColumn(2).setPreferredWidth(Math.round(tableSize.width*0.10f));
		
		// metto l'allineamento centrale alle celle della tabella
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		
		// aggiungo la tabella ed il pulsante al pannello 
		tablePanel.add(scrollPane, BorderLayout.NORTH);
		tablePanel.add(addButton, BorderLayout.SOUTH);
		
		// aggiungo al frame della lobby solo il pannello di attesa
		add(loadingPanel);
		//add(tablePanel);
		
		setVisible(true);
	}
		
	
	public void setOnStartObserver(LobbyStartObserver onStartObserver){
		this.onStartObserver = onStartObserver;
	}
	
	
	@Override
	public void onClientJoin(String username, String host, int port) {
		System.out.println(model.getRowCount());
		if (model.getRowCount() == 0) {
			remove(loadingPanel);
			add(tablePanel);
			SwingUtilities.updateComponentTreeUI(this);
		}
		model.addRow(new Object[] { username, host, new Integer(port) });
	}
}
