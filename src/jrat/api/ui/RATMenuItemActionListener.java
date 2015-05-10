package jrat.api.ui;

import java.util.List;

import jrat.api.RATObject;

public abstract interface RATMenuItemActionListener {

	/**
	 * 
	 * @param servers
	 *            Event fired when servers are selected and custom menu item
	 *            clicked on
	 */
	public abstract void onClick(List<RATObject> servers);
}
