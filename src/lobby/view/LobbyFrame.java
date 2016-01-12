package lobby.view;

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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


/**
 * 
 * 
 * @author DSBattleSheep
 */
@SuppressWarnings("serial")
public class LobbyFrame extends JFrame implements LobbyFrameObserver {

	private final static String LOBBY_FRAME_TITLE = "Lobby @ ";
	
	private final static int LOBBY_FRAME_WIDTH = 400;
	
	private final static int LOBBY_FRAME_HEIGHT = 300;
	
	private final static String[] TABLE_COLUMN_NAMES = {"Username", "host", "port"};
	
	
	
	private JPanel loadingPanel;
	
	private JPanel tablePanel;
	
	private DefaultTableModel model;
	
	public LobbyFrame(String host, int port) {
		super(LOBBY_FRAME_TITLE + host + ":" + port);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(LOBBY_FRAME_WIDTH, LOBBY_FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setResizable(false);

		loadingPanel = new JPanel();
		loadingPanel.setLayout(new BorderLayout());
		loadingPanel.setBackground(Color.WHITE);
		loadingPanel.setOpaque(true);
		
		ImageIcon loading = new ImageIcon("imgs/ajax-loader.gif");
		loadingPanel.add(new JLabel("loading... ", loading, JLabel.CENTER));
		
		add(loadingPanel);
		
	    
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.setBackground(Color.WHITE);
		tablePanel.setOpaque(true);
		
		model = new DefaultTableModel(TABLE_COLUMN_NAMES, 0);		
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		
		JButton addButton = new JButton("Start game");
		addButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.addRow(new Object[] { "Kathy", "127.0.0.1", new Integer(5) });
			}
		});

		Dimension tableSize = table.getPreferredSize();
		Dimension addButtonSize = addButton.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(tableSize.width, 
				LOBBY_FRAME_HEIGHT - addButtonSize.height - 25)); // TODO: 25 pixel 
		
		table.getColumnModel().getColumn(0).setPreferredWidth(Math.round(tableSize.width*0.50f));
		table.getColumnModel().getColumn(1).setPreferredWidth(Math.round(tableSize.width*0.40f));
		table.getColumnModel().getColumn(2).setPreferredWidth(Math.round(tableSize.width*0.10f));
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		
		tablePanel.add(scrollPane, BorderLayout.NORTH);
		tablePanel.add(addButton, BorderLayout.SOUTH);
		
		//add(tablePanel);
		
		setVisible(true);
	}
		
	
	
	
	
	
	@Override
	public void onClientJoin(String username, String host, String port) {
		if (model.getRowCount() == 0) {
			remove(loadingPanel);
			add(tablePanel);
		}
		model.addRow(new Object[] { username, host, new Integer(port) });
	}	
}
