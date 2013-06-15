package org.jrat.project.api;

public abstract interface Queue {

	public abstract void addToSendQueue(PacketBuilder packet);
	
}
