package se.jrat.controller.ui.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;

import se.jrat.controller.AbstractSlave;

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
	public void addSlave(AbstractSlave slave) {
		SlaveBox box = new SlaveBox(slave);
		box.setVisible(true);
		
		pane.add(box);
		
		align();
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
	}

	@Override
	public AbstractSlave getSlave(int row) {
		return null;
	}
	
	@Override
	public List<AbstractSlave> getSelectedSlaves() {
		List<AbstractSlave> list = new ArrayList<AbstractSlave>();

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
	public JMenu getConfigMenu() {
		JMenu menu = new JMenu("Customize");
		
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
	
	private class SlaveBox extends JInternalFrame {
		
		private AbstractSlave slave;
		
		public SlaveBox(AbstractSlave slave) {
			super(slave.getDisplayName(), true, true, true, true);
			this.slave = slave;
		}
		
		public AbstractSlave getSlave() {
			return this.slave;
		}
	}

}
