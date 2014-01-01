package pro.jrat.api;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

public class RATMenuItem {

	private String text;
	private ImageIcon icon;
	private RATMenuItemActionListener listener;

	public RATMenuItem(RATMenuItemActionListener listener, String text, ImageIcon icon) {
		this.listener = listener;
		this.text = text;
		this.icon = icon;
	}

	public RATMenuItem(RATMenuItemActionListener listener, String text) {
		this.listener = listener;
		this.text = text;
	}

	/**
	 * 
	 * @return Text of menu item
	 */

	public String getText() {
		return this.text;
	}

	/**
	 * 
	 * @param text
	 *            Set text of menu item
	 */

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 
	 * @return ImageIcon of this menu item
	 */

	public ImageIcon getIcon() {
		return this.icon;
	}

	/**
	 * 
	 * @param icon
	 *            Sets the menu item icon
	 */

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	/**
	 * 
	 * @return A new instance of the item
	 */

	public JMenuItem getItem() {
		JMenuItem item = new JMenuItem();
		item.setIcon(this.icon);
		item.setText(text);
		return item;
	}

	/**
	 * 
	 * @return The action listener
	 */

	public RATMenuItemActionListener getListener() {
		return this.listener;
	}

}
