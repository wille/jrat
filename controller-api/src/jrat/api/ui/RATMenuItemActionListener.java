package jrat.api.ui;

import java.util.List;

import jrat.api.Client;

public interface RATMenuItemActionListener {

	/**
	 * 
	 * @param servers
	 *            Event fired when servers are selected and custom menu item
	 *            clicked on
	 */
    void onClick(List<Client> servers);
}
