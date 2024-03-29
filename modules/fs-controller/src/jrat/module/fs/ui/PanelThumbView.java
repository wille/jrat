package jrat.module.fs.ui;

import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.controller.utils.Utils;
import jrat.module.fs.packets.PacketRequestImageThumbnail;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("serial")
public class PanelThumbView extends ClientPanel {

	private Map<String, ImageIcon> thumbs = new HashMap<>();

	private String[] files;
	private JList list;
	private JLabel lblCount;
	private JProgressBar progressBar;

    public JProgressBar getBar() {
		return progressBar;
	}

	public JLabel getCount() {
		return lblCount;
	}

	public DefaultListModel getModel() {
		return (DefaultListModel) list.getModel();
	}

	public PanelThumbView(Slave s) {
		super(s, "Thumbnails", Resources.getIcon("images-stack"));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		setLayout(new BorderLayout(0, 0));
		
		progressBar = new JProgressBar();
		lblCount = new JLabel("0/0");

		list = new JList();
		list.setCellRenderer(new ThumbsListRenderer(this));
		list.setModel(new DefaultListModel());
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		scrollPane.setViewportView(list);
		
		add(scrollPane, BorderLayout.CENTER);
		
		JToolBar bar = new JToolBar();
		bar.setFloatable(false);
		add(bar, BorderLayout.SOUTH);
		bar.add(lblCount);
		bar.add(progressBar);

        JPopupMenu popupMenu = new JPopupMenu();
		Utils.addPopup(list, popupMenu);

        JMenuItem mntmReloadAll = new JMenuItem("Reload all");
		mntmReloadAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reload(files);
			}
		});
		mntmReloadAll.setIcon(Resources.getIcon("update"));
		popupMenu.add(mntmReloadAll);

        JMenuItem mntmClear = new JMenuItem("Clear");
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
		mntmClear.setIcon(Resources.getIcon("clear"));
		popupMenu.add(mntmClear);

		popupMenu.addSeparator();

        JMenuItem mntmSaveSelectedx = new JMenuItem("Save selected (150x100)");
		mntmSaveSelectedx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> en = (List<String>)list.getSelectedValuesList();

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
		mntmSaveSelectedx.setIcon(Resources.getIcon("images-stack"));
		popupMenu.add(mntmSaveSelectedx);
	}
	
	public void reload(String[] files) {
		thumbs.clear();
		getBar().setVisible(true);
		getCount().setVisible(true);
		getModel().clear();
		
		this.files = files;
		
		lblCount.setText("0/" + files.length);
		progressBar.setMaximum(files.length);
		
		setProgress(0);
		for (String str : files) {
			slave.addToSendQueue(new PacketRequestImageThumbnail(str));
		}	
	}

	public void setProgress(int i) {
		getCount().setText(i + "/" + files.length);
		getBar().setValue(i);
	}

	public void addImage(String path, BufferedImage image) {
		thumbs.put(path, new ImageIcon(image));
		getModel().addElement(path);
	}

	public int getProgress() {
		return getBar().getValue();
	}

	public Map<String, ImageIcon> getThumbMap() {
		return thumbs;
	}

	public Slave getSlave() {
		return slave;
	}
}
