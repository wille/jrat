package org.jrat.project.api;

import java.util.List;

import org.jrat.project.api.events.OnConnectEvent;
import org.jrat.project.api.events.OnDisableEvent;
import org.jrat.project.api.events.OnDisconnectEvent;
import org.jrat.project.api.events.OnEnableEvent;
import org.jrat.project.api.events.OnPacketEvent;
import org.jrat.project.api.events.OnSendPacketEvent;


public abstract class RATPlugin {
	
	/**
	 * 
	 * @param event OnEnableEvent when plugin is enabled
	 */

	public abstract void onEnable(OnEnableEvent event) throws Exception;
	
	/**
	 * 
	 * @param event OnPacketEvent when String packet header is received
	 */

	public abstract void onPacket(OnPacketEvent event) throws Exception;
	
	/**
	 * 
	 * @param event OnConnectEvent when server connects
	 */

	public abstract void onConnect(OnConnectEvent event) throws Exception;
	
	/**
	 * 
	 * @param event OnDisconnectEvent when server disconnects
	 */

	public abstract void onDisconnect(OnDisconnectEvent event) throws Exception;
	
	/**
	 * 
	 * @param event OnDisableEvent when plugin is disabled (jRAT shut down
	 */

	public abstract void onDisable(OnDisableEvent event) throws Exception;
	
	/**
	 * 
	 * @param event OnSendPacketEvent when string packet header is sent
	 */

	public abstract void onSendPacket(OnSendPacketEvent event) throws Exception;
	
	/**
	 * 
	 * @return Name of the plugin
	 */

	public abstract String getName() throws Exception;
	
	/**
	 * 
	 * @return Plugin version
	 */

	public abstract String getVersion() throws Exception;
	
	/**
	 * 
	 * @return Plugin description
	 */

	public abstract String getDescription() throws Exception;
	
	/**
	 * 
	 * @return Author of plugin
	 */

	public abstract String getAuthor() throws Exception;
	
	/**
	 * 
	 * @return List of RATMenuItems that will be on the right click menu of the Main tab
	 */

	public abstract List<RATMenuItem> getMenuItems() throws Exception;
	
	/**
	 * 
	 * @return List of RATControlMenuEntries that will be on the Control Panel tree list
	 * @throws Exception 
	 */
	
	public abstract List<RATControlMenuEntry> getControlTreeItems() throws Exception;

}
