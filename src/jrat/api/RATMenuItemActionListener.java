package jrat.api;

import java.util.List;

public abstract interface RATMenuItemActionListener {

	/**
	 * 
	 * @param servers
	 *            Event fired when servers are selected and custom menu item
	 *            clicked on
	 */
	public abstract void onClick(List<RATObject> servers);
}
