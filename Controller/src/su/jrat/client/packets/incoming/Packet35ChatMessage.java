package su.jrat.client.packets.incoming;

import java.awt.Color;
import java.io.DataInputStream;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import su.jrat.client.Slave;
import su.jrat.client.ui.frames.FrameRemoteChat;


public class Packet35ChatMessage extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameRemoteChat frame = FrameRemoteChat.instances.get(slave);
		String message = slave.readLine();
		if (frame != null) {
			StyleContext sc = StyleContext.getDefaultStyleContext();
			AttributeSet set = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.red);
			frame.txtChat.getDocument().insertString(frame.txtChat.getDocument().getLength(), "Remote: " + message + "\n", set);
			frame.txtChat.setSelectionEnd(frame.txtChat.getText().length());
			frame.txtChat.setSelectionStart(frame.txtChat.getText().length());
		}
	}

}
