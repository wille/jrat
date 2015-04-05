package se.jrat.controller.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import se.jrat.controller.utils.Utils;

@SuppressWarnings("serial")
public class FrameExecutableInfo extends JFrame {

	private JPanel contentPane;
	private JTextField txtTitle;
	private JTextField txtDescription;
	private JTextField txtCompany;
	private JTextField txtProduct;
	private JTextField txtCopyright;
	private JTextField txtTrademark;
	private JTextField v1;
	private JTextField v2;
	private JTextField v3;
	private JTextField v4;
	private JTextField a4;
	private JTextField a1;
	private JTextField a2;
	private JTextField a3;
	private JCheckBox cbIcon;
	private JTextField txtIcon;
	private JButton btnBrowse;

	public boolean useIcon() {
		return cbIcon.isSelected();
	}

	public String getIcon() {
		return txtIcon.getText();
	}

	public String getAssemblyTitle() {
		return txtTitle.getText();
	}

	public String getDescription() {
		return txtDescription.getText();
	}

	public String getCompany() {
		return txtCompany.getText();
	}

	public String getProduct() {
		return txtProduct.getText();
	}

	public String getCopyright() {
		return txtCopyright.getText();
	}

	public String getTrademark() {
		return txtTrademark.getText();
	}

	public String getVersion() {
		return v1.getText().trim() + "." + v2.getText().trim() + "." + v3.getText().trim() + "." + v4.getText().trim();
	}

	public String getAssemblyVersion() {
		return a1.getText().trim() + "." + a2.getText().trim() + "." + a3.getText().trim() + "." + a4.getText().trim();
	}

	public boolean check() {
		try {
			Integer.parseInt(v1.getText());
			Integer.parseInt(v2.getText());
			Integer.parseInt(v3.getText());
			Integer.parseInt(v4.getText());

			Integer.parseInt(a1.getText());
			Integer.parseInt(a2.getText());
			Integer.parseInt(a3.getText());
			Integer.parseInt(a4.getText());
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	public FrameExecutableInfo() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameExecutableInfo.class.getResource("/icons/exe.png")));
		setTitle("Assembly Information");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 342, 344);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		txtTitle = new JTextField();
		txtTitle.setText("Title");
		txtTitle.setColumns(10);

		JLabel lblTitle = new JLabel("Title:");

		txtDescription = new JTextField();
		txtDescription.setText("Description");
		txtDescription.setColumns(10);

		txtCompany = new JTextField();
		txtCompany.setText("Company");
		txtCompany.setColumns(10);

		txtProduct = new JTextField();
		txtProduct.setText("Product");
		txtProduct.setColumns(10);

		txtCopyright = new JTextField();
		txtCopyright.setText("Copyright");
		txtCopyright.setColumns(10);

		txtTrademark = new JTextField();
		txtTrademark.setText("Trademark");
		txtTrademark.setColumns(10);

		JLabel lblDescription = new JLabel("Description:");

		JLabel lblCompany = new JLabel("Company:");

		JLabel lblProduct = new JLabel("Product:");

		JLabel lblCopyright = new JLabel("Copyright:");

		JLabel lblTrademark = new JLabel("Trademark:");

		v1 = new JTextField();
		v1.setText("1");
		v1.setColumns(10);

		v2 = new JTextField();
		v2.setText("0");
		v2.setColumns(10);

		v3 = new JTextField();
		v3.setText("0");
		v3.setColumns(10);

		v4 = new JTextField();
		v4.setText("0");
		v4.setColumns(10);

		a4 = new JTextField();
		a4.setText("0");
		a4.setColumns(10);

		a1 = new JTextField();
		a1.setText("1");
		a1.setColumns(10);

		a2 = new JTextField();
		a2.setText("0");
		a2.setColumns(10);

		a3 = new JTextField();
		a3.setText("0");
		a3.setColumns(10);

		JLabel lblVersion = new JLabel("Version:");

		JLabel lblAssemblyV = new JLabel("Assembly V:");

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});

		JLabel lblIcon = new JLabel("Icon:");

		cbIcon = new JCheckBox("");

		txtIcon = new JTextField();
		txtIcon.setEditable(false);
		txtIcon.setColumns(10);

		btnBrowse = new JButton("...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();
				c.showOpenDialog(null);
				File file = c.getSelectedFile();

				if (file != null) {
					cbIcon.setSelected(true);
					txtIcon.setText(file.getAbsolutePath());
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblTitle)
					.addGap(54)
					.addComponent(txtTitle, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblDescription)
					.addGap(21)
					.addComponent(txtDescription, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblCompany)
					.addGap(29)
					.addComponent(txtCompany, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblProduct)
					.addGap(37)
					.addComponent(txtProduct, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblCopyright)
					.addGap(27)
					.addComponent(txtCopyright, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblTrademark)
					.addGap(23)
					.addComponent(txtTrademark, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblVersion)
					.addGap(39)
					.addComponent(v1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(v2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(v3, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(v4, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblAssemblyV)
					.addGap(20)
					.addComponent(a1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(a2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(a3, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(a4, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblIcon)
					.addGap(53)
					.addComponent(cbIcon)
					.addGap(2)
					.addComponent(txtIcon, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addComponent(btnBrowse, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(198)
					.addComponent(btnOk)
					.addGap(6)
					.addComponent(btnCancel))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblTitle))
						.addComponent(txtTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblDescription))
						.addComponent(txtDescription, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblCompany))
						.addComponent(txtCompany, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblProduct))
						.addComponent(txtProduct, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblCopyright))
						.addComponent(txtCopyright, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblTrademark))
						.addComponent(txtTrademark, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblVersion))
						.addComponent(v1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(v2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(v3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(v4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(4)
							.addComponent(lblAssemblyV))
						.addComponent(a1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(a2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(a3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(a4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblIcon)
						.addComponent(cbIcon)
						.addComponent(txtIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnOk)
						.addComponent(btnCancel)))
		);
		contentPane.setLayout(gl_contentPane);
		
		Utils.center(this);
	}
}
