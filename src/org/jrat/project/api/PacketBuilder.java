package org.jrat.project.api;

import java.io.DataOutputStream;


public abstract class PacketBuilder {
	
	public abstract void write(DataOutputStream out) throws Exception;
	
}
