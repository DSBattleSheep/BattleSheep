package org.sd.battlesheep.view;



import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;



@SuppressWarnings("serial")
public abstract class AFrame extends JFrame
{
	/**
	 * costante per il nome del programma
	 */
	public static final String PROGRAM_NAME = "Battlesheep v.0.1 - beta";
	
	
	
	/**
	 * costante per il path della cartella delle immagini
	 */
	private static final String IMGS_PATH = "./imgs/";
	
	/**
	 * costante per il path dell'immagine del logo
	 */
	private static final String LOGO_PATH = IMGS_PATH + "logo.png";
	
	/**
	 * costante per il path dell'immagine dell'erba
	 */
	private static final String GRASS_PATH = IMGS_PATH + "grass.png";
	
	/**
	 * costante per il path dell'immagine della pecora
	 */
	private static final String SHEEP_PATH = IMGS_PATH + "sheep.png";
	
	
	
	/**
	 * costante per l'icona del logo
	 */
	public static final Icon LOGO = new ImageIcon(LOGO_PATH);
	
	/**
	 * costante per l'icona dell'erba
	 */
	public static final Icon GRASS = new ImageIcon(GRASS_PATH);
	
	/**
	 * costante per l'icona della pecora
	 */
	public static final Icon SHEEP = new ImageIcon(SHEEP_PATH);
	
	
	
	public AFrame(int width, int height) {
		super(PROGRAM_NAME);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
	}
}