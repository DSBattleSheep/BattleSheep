package org.sd.battlesheep.view.game;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import org.sd.battlesheep.view.APanel;
import org.sd.battlesheep.view.utils.Field;



/**
 * @author Giulio Biagini
 */
@SuppressWarnings("serial")
public class GamePanel extends APanel
{
	private static final Icon BANNER = new ImageIcon(IMGS_PATH + "banner.png");
	
	
	
	private JPanel northPanel;
	
	private JLabel bannerLabel;
	
	
	
	private JPanel middlePanel;
	
	private Field myField;
	
	private Field opponentField;
	
	
	
	private JPanel leftPanel;
	
	
	
	private JPanel southPanel;
	
	
	
	private int rows;
	
	private int cols;
	
	
	
	public GamePanel(int rows, int cols) {
		super(Color.GREEN, new BorderLayout());
		
		/* model */
		
		this.rows = rows;
		this.cols = cols;
		
		/* north panel */
		
		northPanel = new JPanel(new GridBagLayout());
		northPanel.setBackground(new Color(0, 0, 0, 0));
		
		bannerLabel = new JLabel(BANNER);
		bannerLabel.setOpaque(true);
		bannerLabel.setBackground(new Color(0, 0, 0, 0));
		
		northPanel.add(
			bannerLabel,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(10, 10, 5, 10),
				0, 0
			)
		);
		
		/* middle panel */
		
		middlePanel = new JPanel(new GridBagLayout());
		middlePanel.setBackground(new Color(0, 0, 0, 0));
		
		myField = new Field(rows, cols, null);
		
		opponentField = new Field(rows, cols, null);
		
		middlePanel.add(
			myField,
			new GridBagConstraints(
				0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 10, 5, 5),
				0, 0
			)
		);
		
		middlePanel.add(
			opponentField,
			new GridBagConstraints(
				1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(5, 5, 5, 10),
				0, 0
			)
		);
		
		/* left panel */
		
		/* south panel */
		
		/* this panel */
		
		add(northPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
	}
	
	
	
	@Override
	public void lock() {
		
	}
	
	@Override
	public void unlock() {
		
	}
}