package pro.jrat.client.ui.frames;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;

import pro.jrat.client.Slave;
import pro.jrat.client.packets.outgoing.Packet12RemoteScreen;
import pro.jrat.client.packets.outgoing.Packet26StopRemoteScreen;
import pro.jrat.client.ui.components.JRemoteScreenPane;

@SuppressWarnings("serial")
public class FrameRemoteScreen extends BaseFrame {
	
	public static final Map<Slave, FrameRemoteScreen> instances = new HashMap<Slave, FrameRemoteScreen>();

	private int monitor;
	private int quality;
	private int interval;
	private int size;
	
	private JToolBar toolBarTop;
	private JToolBar toolBarBottom;
	private JRemoteScreenPane screenPane;
	private JProgressBar progressBar;
	private JCheckBox cbShowProgressBar;
	private JButton btnStart;
	private JButton btnStop;
	private Slave slave;

	public FrameRemoteScreen(Slave sl) {
		slave = sl;
		
		setTitle("Remote Screen - " + sl.getComputerName() + " - " + sl.getIP());
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteScreen.class.getResource("/icons/screen.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 673, 446);
		
		instances.put(sl, this);
		
		toolBarTop = new JToolBar();
		toolBarTop.setFloatable(false);
		
		toolBarBottom = new JToolBar();
		toolBarBottom.setFloatable(false);
		
		screenPane = new JRemoteScreenPane();
		
		getContentPane().add(toolBarTop, BorderLayout.NORTH);
		getContentPane().add(toolBarBottom, BorderLayout.SOUTH);
		
		cbShowProgressBar = new JCheckBox("");
		cbShowProgressBar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				progressBar.setVisible(cbShowProgressBar.isSelected());
			}
		});
		cbShowProgressBar.setSelected(true);
		cbShowProgressBar.setToolTipText("Show progressbar");
		toolBarBottom.add(cbShowProgressBar);
		
		progressBar = new JProgressBar();
		toolBarBottom.add(progressBar);
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		toolBarTop.add(btnStart);
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		btnStop.setEnabled(false);
		toolBarTop.add(btnStop);
		getContentPane().add(screenPane);
	}
	
	public void start() {
		btnStart.setEnabled(false);
		btnStop.setEnabled(true);
	
		slave.addToSendQueue(new Packet12RemoteScreen(size, quality, monitor));
	}
	
	public void stop() {
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
		
		slave.addToSendQueue(new Packet26StopRemoteScreen());
	}

	public static final void show(Slave sl) {
		FrameRemoteScreen frame = new FrameRemoteScreen(sl);
		frame.setVisible(true);
		FrameMonitors dialog = new FrameMonitors(frame, sl);
		dialog.setVisible(true);
	}

	public int getMonitor() {
		return monitor;
	}

	public void setMonitor(int monitor) {
		this.monitor = monitor;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getImageSize() {
		return size;
	}

	public void setImageSize(int size) {
		this.size = size;
	}

	public void update(BufferedImage image) {
		screenPane.update(image);
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

}
