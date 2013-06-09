package com.redpois0n.packets;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameRemoteChat;


public class PacketCHAT extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
		FrameRemoteChat frame = FrameRemoteChat.instances.get(slave);
		String message = slave.readLine();
		if (frame != null) {
			StyleContext sc = StyleContext.getDefaultStyleContext();
			AttributeSet set = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.red);
			frame.txtChat.getDocument().insertString(frame.txtChat.getDocument().getLength(), "Server: " + message + "\n", set);
			frame.txtChat.setSelectionEnd(frame.txtChat.getText().length());
			frame.txtChat.setSelectionStart(frame.txtChat.getText().length());
		}
	}

}
