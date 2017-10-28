package jrat.controller.ui.frames;

import jrat.api.Resources;
import jrat.controller.Constants;
import jrat.controller.Globals;
import jrat.controller.Help;
import jrat.controller.ui.renderers.HelpTreeRenderer;
import jrat.controller.utils.NetUtils;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class FrameHelp extends BaseFrame {

    private JTree tree;
	private JEditorPane com;
    private String currentFile;

	public DefaultTreeModel getModel() {
		return (DefaultTreeModel) tree.getModel();
	}

	public FrameHelp() {
		setTitle(Constants.NAME + " help");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameHelp.class.getResource("/help.png")));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 672, 384);
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE).addContainerGap(499, Short.MAX_VALUE)).addComponent(splitPane));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(splitPane)));

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		com = new JEditorPane();
		com.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						NetUtils.openUrl(e.getURL().toURI().toString());
					} catch (Exception localException) {
						localException.printStackTrace();
					}
				}
			}
		});
		com.setEditable(false);
		com.setContentType("text/html");
		scrollPane_1.setViewportView(com);

        JPopupMenu popupMenu = new JPopupMenu();
		addPopup(com, popupMenu);

        JMenuItem mntmOpenLatestOn = new JMenuItem("Open latest on internet");
		mntmOpenLatestOn.setIcon(Resources.getIcon("url"));
		mntmOpenLatestOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentFile == null) {
					return;
				}
				String site;
				if (currentFile.startsWith("/") || currentFile.startsWith("\\")) {
					site = Constants.HOST + "/help" + currentFile.trim().replace("\\", "/").replaceAll("/+", "/").replace(" ", "%20");
				} else {
					site = Constants.HOST + "/misc/help/" + currentFile.trim().replace("\\", "/").replaceAll("/+", "/").replace(" ", "%20");
				}
				try {
					Desktop.getDesktop().browse(new URI(site));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		popupMenu.add(mntmOpenLatestOn);

		tree = new JTree();
		splitPane.setLeftComponent(tree);
		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);
		tree.setCellRenderer(new HelpTreeRenderer());
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				try {
					String path = "";
					for (Object obj : arg0.getPath().getPath()) {
						path += File.separator + obj.toString();
					}
					path = path.replace("help", "").replace("Help", "") + ".txt";
					String str = Help.getHelp(path);

					if (str == null) {
						return;
					}

					currentFile = path;

					String p = new File("help/" + path).getAbsolutePath();
					String dir = p.substring(0, p.lastIndexOf(File.separator)) + File.separator;

					String text = "<font face=\"arial\"><h1>" + arg0.getPath().getPath()[arg0.getPath().getPath().length - 1] + "</h1></br><p>" + str.replace("\n", "<br>") + "</p></font>";

					com.setText(text.replace("PARENTDIR", dir));
				} catch (Exception ex) {
					// ex.printStackTrace();
				}
			}
		});
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Help") {
			{
				addNodes(this);
			}
		}));
		contentPane.setLayout(gl_contentPane);

		splitPane.setDividerLocation(getSize().width / 4);
	}

	public void addNodes(DefaultMutableTreeNode node) {
		addFolder(node, Globals.getHelpDocDirectory());
	}

	public void addFolder(DefaultMutableTreeNode curTop, File dir) {
		String curPath = dir.getPath();
		DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(new File(curPath).getName());

		if (curTop != null) {
			curTop.add(curDir);
			getModel().insertNodeInto(curDir, curTop, 0);
		}

		List<String> ol = new ArrayList<>();

		String[] tmp = dir.list();

        for (String aTmp : tmp) {
            ol.add(aTmp);
        }

		Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
		File f;

		List<String> files = new ArrayList<>();

        for (String thisObject : ol) {
            String newPath;
            if (curPath.equals(".")) {
                newPath = thisObject;
            } else {
                newPath = curPath + File.separator + thisObject;
            }

            if ((f = new File(newPath)).isDirectory()) {
                addFolder(curDir, f);
            } else if (f.getName().endsWith(".txt")) {
                files.add(thisObject);
            }
        }

        for (String file : files) {
            getModel().insertNodeInto(new DefaultMutableTreeNode(file.substring(0, file.lastIndexOf("."))), curDir, curDir.getChildCount());
        }
	}

	public DefaultMutableTreeNode getNode(String str) {
		return new DefaultMutableTreeNode(str);
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
