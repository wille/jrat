package com.redpois0n.ui.frames;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.redpois0n.Slave;
import com.redpois0n.packets.in.PacketBuilder;
import com.redpois0n.packets.out.Header;
import com.redpois0n.ui.renderers.ThumbsListRenderer;


@SuppressWarnings("serial")
public class FrameRemoteThumbView extends BaseFrame {
	
	public static HashMap<Slave, FrameRemoteThumbView> instances = new HashMap<Slave, FrameRemoteThumbView>();
	public HashMap<String, ImageIcon> thumbs = new HashMap<String, ImageIcon>();

	private JPanel contentPane;
	private String[] paths;
	private JList<String> list;
	private Slave slave;
	private JLabel lblCount;
	private JProgressBar progressBar;
	private JPopupMenu popupMenu;
	private JMenuItem mntmReloadAll;
	private JMenuItem mntmSaveSelectedx;
	private JMenuItem mntmClear;
	
	public Slave getSlave() {
		return slave;
	}
	
	public JProgressBar getBar() {
		return progressBar;
	}
	
	public JLabel getCount() {
		return lblCount;
	}
	
	public DefaultListModel<String> getModel() {
		return (DefaultListModel<String>)list.getModel();
	}

	public FrameRemoteThumbView(Slave sl, String[] p) {
		super();
		setTitle("Thumbnail view - " + sl.getIP() + " - " + sl.getComputerName());
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteThumbView.class.getResource("/icons/images-stack.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		this.paths = p;
		this.slave = sl;
		instances.put(sl, this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 731, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		progressBar = new JProgressBar();
		
		lblCount = new JLabel("0/0");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblCount)
							.addGap(36)))
					.addGap(0))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCount))
					.addGap(8))
		);
		
		list = new JList<String>();
		list.setCellRenderer(new ThumbsListRenderer(this));
		list.setModel(new DefaultListModel<String>());
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		scrollPane.setViewportView(list);
		
		popupMenu = new JPopupMenu();
		addPopup(list, popupMenu);
		
		mntmReloadAll = new JMenuItem("Reload all");
		mntmReloadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thumbs.clear();
				getBar().setVisible(true);
				getCount().setVisible(true);
				getModel().clear();
				process();
			}
		});
		mntmReloadAll.setIcon(new ImageIcon(FrameRemoteThumbView.class.getResource("/icons/update.png")));
		popupMenu.add(mntmReloadAll);
		
		mntmClear = new JMenuItem("Clear");
		mntmClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thumbs.clear();
				getBar().setVisible(true);
				getCount().setVisible(true);
				getModel().clear();
				getBar().setValue(0);
				getCount().setText("0/0");
			}
		});
		mntmClear.setIcon(new ImageIcon(FrameRemoteThumbView.class.getResource("/icons/clear.png")));
		popupMenu.add(mntmClear);
		
		popupMenu.addSeparator();
		
		mntmSaveSelectedx = new JMenuItem("Save selected (150x100)");
		mntmSaveSelectedx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> en = list.getSelectedValuesList();			
				
				for (String str : en) {					
					JFileChooser c = new JFileChooser();
					c.showSaveDialog(null);
					File file = c.getSelectedFile();
					
					if (file == null) {
						return;
					}
					BufferedImage image = new BufferedImage(150, 100, BufferedImage.TYPE_INT_RGB);
					Graphics g = image.createGraphics();
					g.drawImage(thumbs.get(str).getImage(), 0, 0, null);
					try {
						ImageIO.write(image, "png", file);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					g.dispose();
				}
			}
		});
		mntmSaveSelectedx.setIcon(new ImageIcon(FrameRemoteThumbView.class.getResource("/icons/images-stack.png")));
		popupMenu.add(mntmSaveSelectedx);
		contentPane.setLayout(gl_contentPane);
		
		lblCount.setText("0/" + paths.length);
		progressBar.setMaximum(paths.length);
		
		process();
	}
	
	public void setProgress(int i) {
		getCount().setText(i + "/" + paths.length);
		getBar().setValue(i);
	}
	
	public void addImage(String path, BufferedImage image) {
		//path = new File(path).getName();
		thumbs.put(path, new ImageIcon(image));
		getModel().addElement(path);
	}
	
	public void process() {
		setProgress(0);
		for (String str : paths) {
			PacketBuilder packet = new PacketBuilder(Header.LIST_THUMBNAIL, str);
			slave.addToSendQueue(packet);
		}		
	}
	
	public void exit() {
		instances.remove(slave);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public int getProgress() {
		return getBar().getValue();
	}
}
