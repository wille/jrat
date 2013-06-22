package pro.jrat.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class FrameExecutableInfo extends BaseFrame {

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
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameExecutableInfo.class.getResource("/icons/exe.png")));
		setTitle("Assembly Information");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 342, 344);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		txtTitle = new JTextField();
		txtTitle.setBounds(93, 16, 228, 20);
		txtTitle.setText("Title");
		txtTitle.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(15, 19, 24, 14);
		
		txtDescription = new JTextField();
		txtDescription.setBounds(93, 42, 228, 20);
		txtDescription.setText("Description");
		txtDescription.setColumns(10);
		
		txtCompany = new JTextField();
		txtCompany.setBounds(93, 68, 228, 20);
		txtCompany.setText("Company");
		txtCompany.setColumns(10);
		
		txtProduct = new JTextField();
		txtProduct.setBounds(93, 94, 228, 20);
		txtProduct.setText("Product");
		txtProduct.setColumns(10);
		
		txtCopyright = new JTextField();
		txtCopyright.setBounds(93, 120, 228, 20);
		txtCopyright.setText("Copyright");
		txtCopyright.setColumns(10);
		
		txtTrademark = new JTextField();
		txtTrademark.setBounds(93, 146, 228, 20);
		txtTrademark.setText("Trademark");
		txtTrademark.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(15, 45, 57, 14);
		
		JLabel lblCompany = new JLabel("Company:");
		lblCompany.setBounds(15, 71, 49, 14);
		
		JLabel lblProduct = new JLabel("Product:");
		lblProduct.setBounds(15, 97, 41, 14);
		
		JLabel lblCopyright = new JLabel("Copyright:");
		lblCopyright.setBounds(15, 123, 51, 14);
		
		JLabel lblTrademark = new JLabel("Trademark:");
		lblTrademark.setBounds(15, 149, 55, 14);
		
		v1 = new JTextField();
		v1.setBounds(93, 172, 35, 20);
		v1.setText("1");
		v1.setColumns(10);
		
		v2 = new JTextField();
		v2.setBounds(134, 172, 35, 20);
		v2.setText("0");
		v2.setColumns(10);
		
		v3 = new JTextField();
		v3.setBounds(175, 172, 35, 20);
		v3.setText("0");
		v3.setColumns(10);
		
		v4 = new JTextField();
		v4.setBounds(216, 172, 35, 20);
		v4.setText("0");
		v4.setColumns(10);
		
		a4 = new JTextField();
		a4.setBounds(216, 198, 35, 20);
		a4.setText("0");
		a4.setColumns(10);
		
		a1 = new JTextField();
		a1.setBounds(93, 198, 35, 20);
		a1.setText("1");
		a1.setColumns(10);
		
		a2 = new JTextField();
		a2.setBounds(134, 198, 35, 20);
		a2.setText("0");
		a2.setColumns(10);
		
		a3 = new JTextField();
		a3.setBounds(175, 198, 35, 20);
		a3.setText("0");
		a3.setColumns(10);
		
		JLabel lblVersion = new JLabel("Version:");
		lblVersion.setBounds(15, 175, 39, 14);
		
		JLabel lblAssemblyV = new JLabel("Assembly V:");
		lblAssemblyV.setBounds(15, 202, 58, 14);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(256, 277, 65, 23);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(203, 277, 47, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		JLabel lblIcon = new JLabel("Icon:");
		lblIcon.setBounds(15, 229, 25, 14);
		
		cbIcon = new JCheckBox("");
		cbIcon.setBounds(93, 229, 21, 21);
		
		txtIcon = new JTextField();
		txtIcon.setBounds(116, 229, 161, 20);
		txtIcon.setEditable(false);
		txtIcon.setColumns(10);
		contentPane.setLayout(null);
		contentPane.add(lblTitle);
		contentPane.add(txtTitle);
		contentPane.add(lblDescription);
		contentPane.add(txtDescription);
		contentPane.add(lblCopyright);
		contentPane.add(txtCopyright);
		contentPane.add(lblCompany);
		contentPane.add(txtCompany);
		contentPane.add(lblProduct);
		contentPane.add(txtProduct);
		contentPane.add(btnOk);
		contentPane.add(btnCancel);
		contentPane.add(lblTrademark);
		contentPane.add(lblVersion);
		contentPane.add(lblAssemblyV);
		contentPane.add(lblIcon);
		contentPane.add(cbIcon);
		contentPane.add(txtIcon);
		contentPane.add(v1);
		contentPane.add(v2);
		contentPane.add(v3);
		contentPane.add(v4);
		contentPane.add(txtTrademark);
		contentPane.add(a1);
		contentPane.add(a2);
		contentPane.add(a3);
		contentPane.add(a4);
		
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
		btnBrowse.setBounds(286, 229, 35, 21);
		contentPane.add(btnBrowse);
	}
}
