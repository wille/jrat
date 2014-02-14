package io.jrat.api;

import io.jrat.api.events.OnConnectEvent;
import io.jrat.api.events.OnDisableEvent;
import io.jrat.api.events.OnDisconnectEvent;
import io.jrat.api.events.OnEnableEvent;
import io.jrat.api.events.OnPacketEvent;
import io.jrat.api.events.OnSendPacketEvent;

import java.awt.event.ActionListener;
import java.util.List;


public abstract class RATPlugin {

	/**
	 * 
	 * @param event
	 *            OnEnableEvent when plugin is enabled
	 */

	public abstract void onEnable(OnEnableEvent event) throws Exception;

	/**
	 * WILL NOT BE CALLED IF PACKET IS JRAT PACKET
	 * 
	 * @param event
	 *            OnPacketEvent when String packet header is received
	 */

	public abstract void onPacket(OnPacketEvent event) throws Exception;

	/**
	 * 
	 * @param event
	 *            OnConnectEvent when server connects
	 */

	public abstract void onConnect(OnConnectEvent event) throws Exception;

	/**
	 * 
	 * @param event
	 *            OnDisconnectEvent when server disconnects
	 */

	public abstract void onDisconnect(OnDisconnectEvent event) throws Exception;

	/**
	 * 
	 * @param event
	 *            OnDisableEvent when plugin is disabled (jRAT shut down)
	 */

	public abstract void onDisable(OnDisableEvent event) throws Exception;

	/**
	 * 
	 * @param event
	 *            OnSendPacketEvent when string packet header is sent
	 */

	public abstract void onSendPacket(OnSendPacketEvent event) throws Exception;

	/**
	 * Note that this is the display name, like "Test Plugin", but the working
	 * name in etc gallery is "TestPlugin"
	 * 
	 * @return Name of the plugin
	 */

	public abstract String getName();

	/**
	 * 
	 * @return Plugin version
	 */

	public abstract String getVersion();

	/**
	 * 
	 * @return Plugin description
	 */

	public abstract String getDescription();

	/**
	 * 
	 * @return Author of plugin
	 */

	public abstract String getAuthor();

	/**
	 * 
	 * @return List of RATMenuItems that will be on the right click menu of the
	 *         Main tab
	 */

	public abstract List<RATMenuItem> getMenuItems() throws Exception;

	/**
	 * 
	 * @return List of RATControlMenuEntries that will be on the Control Panel
	 *         tree list
	 * @throws Exception
	 */

	public abstract List<RATControlMenuEntry> getControlTreeItems() throws Exception;

	/**
	 * Display about dialog maybe?
	 * 
	 * @return ActionListener for button, null if none
	 */
	public abstract ActionListener getGlobalMenuItemListener();

}
