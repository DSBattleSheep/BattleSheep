package view.game;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



@SuppressWarnings("serial")
public class GameFrame extends JFrame
{
	private static final int FIELD_WIDTH = 10;
	
	private static final int FIELD_HEIGHT = 10;
	
	private static final Icon BANNER = new ImageIcon("./imgs/banner.jpg");
	
	private static final Icon GRASS = new ImageIcon("./imgs/grass.png");
	
	
	
	private JPanel northPanel;
	
	private JLabel banner;
	
	private JPanel leftPanel;
	
	private JPanel middlePanel;
	
	private JPanel middleLeftPanel;
	
	private JLabel myField[][];
	
	private JPanel middleRightPanel;
	
	private JLabel opponentField[][];
	
	private JPanel rightPanel;
	
	private JPanel southPanel;
	
	private JTextArea logArea;
	
	private JScrollPane scrollableLogArea;
	
	
	
	public GameFrame() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(0, 0, 800, 600);
		setResizable(false);
		setLayout(new BorderLayout());
		
		/*
		 * north panel
		 */
		
		northPanel = new JPanel();
		
		banner = new JLabel(BANNER);
		//banner.setPreferredSize(new Dimension(0, 200));
		
		northPanel.add(banner);
		
		/*
		 * left panel
		 */
		
		/*
		 * middle panel
		 */
		
		middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(1, 2));
		
		middleLeftPanel = new JPanel();
		middleLeftPanel.setLayout(new GridLayout(FIELD_HEIGHT, FIELD_WIDTH));
		
		myField = new JLabel[FIELD_WIDTH][FIELD_HEIGHT];
		for (int x = 0; x < FIELD_WIDTH; x++) {
			for (int y = 0; y < FIELD_HEIGHT; y++) {
				myField[x][y] = new JLabel(GRASS);
				myField[x][y].setBorder(BorderFactory.createLineBorder(Color.GREEN));
				middleLeftPanel.add(myField[x][y]);
			}
		}
		
		middleRightPanel = new JPanel();
		middleRightPanel.setLayout(new GridLayout(FIELD_HEIGHT, FIELD_WIDTH));
		
		opponentField = new JLabel[FIELD_WIDTH][FIELD_HEIGHT];
		for (int x = 0; x < FIELD_WIDTH; x++) {
			for (int y = 0; y < FIELD_HEIGHT; y++) {
				opponentField[x][y] = new JLabel(GRASS);
				opponentField[x][y].setBorder(BorderFactory.createLineBorder(Color.GREEN));
				middleRightPanel.add(opponentField[x][y]);
			}
		}
		
		middlePanel.add(middleLeftPanel);
		middlePanel.add(middleRightPanel);
		
		/*
		 * right panel
		 */
		
		/*
		 * south panel
		 */
		
		southPanel = new JPanel();
		
		logArea = new JTextArea("ciao a tutti,\nbelli e brutti!");
		logArea.setEditable(false);
		logArea.setBackground(Color.BLACK);
		logArea.setForeground(Color.WHITE);
		logArea.setCaretColor(Color.BLACK);
		logArea.setSelectionColor(Color.WHITE);
		logArea.setSelectedTextColor(Color.BLACK);
		
		scrollableLogArea = new JScrollPane(logArea);
		scrollableLogArea.setPreferredSize(new Dimension(0, 80));
		
		southPanel.add(scrollableLogArea);
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(scrollableLogArea, BorderLayout.SOUTH);
	}
}