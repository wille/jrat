package pro.jrat.ui.frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class FrameOnlinePlugins extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JPopupMenu popupMenu;
	private JMenuItem mntmReload;

	public FrameOnlinePlugins() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameOnlinePlugins.class.getResource("/icons/application_icon_large.png")));
		setTitle("Browse Online Plugins");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 725, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		model = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Author", "Description", "Version", "Built for" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(144);
		table.getColumnModel().getColumn(1).setPreferredWidth(93);
		table.getColumnModel().getColumn(2).setPreferredWidth(124);
		
		popupMenu = new JPopupMenu();
		addPopup(scrollPane, popupMenu);
		addPopup(table, popupMenu);
		
		mntmReload = new JMenuItem("Reload");
		popupMenu.add(mntmReload);
		scrollPane.setViewportView(table);
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
}
