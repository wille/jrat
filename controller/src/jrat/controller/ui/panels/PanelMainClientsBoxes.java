package jrat.controller.ui.panels;

import jrat.api.Resources;
import jrat.controller.AbstractSlave;
import jrat.controller.OfflineSlave;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet40Thumbnail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PanelMainClientsBoxes extends PanelMainClients {

	private JDesktopPane pane;
	
	public PanelMainClientsBoxes() {
		pane = new JDesktopPane();

		pane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				align();
			}
		});

		setViewportView(pane);
	}
	
	@Override
	public String getViewName() {
		return "Boxes";
	}
	
	@Override
	public Icon getIcon() {
		return Resources.getIcon("application-tile");
	}
	
	@Override
	public void addSlave(AbstractSlave slave) {
		SlaveBox box = new SlaveBox(slave);
		box.setVisible(true);
		
		pane.add(box);

		// build a new popup menu for this client
        box.addMouseListener(new MouseAdapter() {
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
                JPopupMenu popup = getPopupMenu();
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
		
		align();
		
		if (slave.getThumbnail() == null) {
			if (slave instanceof Slave) {
				((Slave) slave).addToSendQueue(new Packet40Thumbnail(box.getWidth(), box.getHeight()));
			}
		}
	}
	
	@Override
	public void removeSlave(AbstractSlave slave) {
		for (JInternalFrame c : pane.getAllFrames()) {
			assert c instanceof SlaveBox;
			
			SlaveBox box = (SlaveBox) c;
			
			if (box.getSlave().equals(slave)) {
				pane.remove(c);
			}
		}
		
		repaint();
	}

	@Override
	public AbstractSlave getSlave(int row) {
		return null;
	}
	
	@Override
	public List<AbstractSlave> getSelectedSlaves() {
		List<AbstractSlave> list = new ArrayList<>();

		AbstractSlave selected = getSelectedSlave();
		
		if (selected != null) {
			list.add(selected);
		}
		
		return list;
	}
	
	@Override
	public AbstractSlave getSelectedSlave() {
		AbstractSlave selected = null;
		
		for (JInternalFrame c : pane.getAllFrames()) {
			assert c instanceof SlaveBox;
			
			SlaveBox box = (SlaveBox) c;
			
			if (box.isSelected()) {
				selected = box.getSlave();
				break; // only one frame can be active
			}
		}
		
		return selected;
	}
	
	@Override
	public void clear() {
		for (JInternalFrame c : pane.getAllFrames()) {
			pane.remove(c);
		}
	}

	@Override
	public List<JMenuItem> getConfigMenu() {
		List<JMenuItem> menu = new ArrayList<>();
		
		JMenuItem mntmAlign = new JMenuItem("Align");
		mntmAlign.setIcon(Resources.getIcon("application-tiles"));
		
		mntmAlign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				restore();
				align();
			}
		});
		
		menu.add(mntmAlign);
		
		return menu;
	}

	public void align() {
		JInternalFrame[] allframes = pane.getAllFrames();
		int count = allframes.length;
		
		if (count == 0) {
			return;
		}

		int sqrt = (int) Math.sqrt(count);
		int rows = sqrt;
		int columns = sqrt;
		if (rows * columns < count) {
			columns++;
			if (rows * columns < count) {
				rows++;
			}
		}

		Dimension size = pane.getSize();

		int w = size.width / columns;
		int h = size.height / rows;
		int x = 0;
		int y = 0;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns && ((i * columns) + j < count); j++) {
				JInternalFrame f = allframes[(i * columns) + j];

				pane.getDesktopManager().resizeFrame(f, x, y, w, h);
				x += w;
			}
			
			y += h;
			x = 0;
		}
	}
	
	public void restore() {
		JInternalFrame[] allframes = pane.getAllFrames();

		for (JInternalFrame frame : allframes) {
			try {
				frame.setIcon(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns the current internal frame size (if not moved around by user)
	 * @return
	 */
	public Dimension getCurrentDimension() {
		JInternalFrame[] allframes = pane.getAllFrames();
		int count = allframes.length;
		
		if (count == 0) {
			return pane.getSize();
		}

		int sqrt = (int) Math.sqrt(count);
		int rows = sqrt;
		int columns = sqrt;
		if (rows * columns < count) {
			columns++;
			if (rows * columns < count) {
				rows++;
			}
		}

		Dimension size = pane.getSize();

		int w = size.width / columns;
		int h = size.height / rows;
		
		return new Dimension(w, h);
	}
	
	private class SlaveBox extends JInternalFrame {
		
		private AbstractSlave slave;
		
		public SlaveBox(AbstractSlave s) {
			super(s.getDisplayName(), true, true, true, true);
			this.slave = s;
			
			JPanel panel = new JPanel() {
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					
					if (slave instanceof OfflineSlave) {
						OfflineSlave os = (OfflineSlave) slave;

						g.drawString("Last seen: " + os.getLastSeenDate(), 5, g.getFontMetrics().getHeight() + 5);
					} else if (slave.getThumbnail() != null) {
						Image image = slave.getThumbnail().getImage();

						g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
					} else {
						g.drawString("Loading...", 5, 5);
					}
				}
			};
			
			super.setFrameIcon(s.getFlag());
			
			add(panel, BorderLayout.CENTER);
		}	
		
		public AbstractSlave getSlave() {
			return this.slave;
		}
	}

}
