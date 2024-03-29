package jrat.controller.ui.frames;

import jrat.api.Resources;
import jrat.common.Version;
import jrat.controller.Constants;
import jrat.controller.ErrorDialog;
import jrat.controller.utils.NetUtils;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;


@SuppressWarnings("serial")
public class FrameChangelog extends BaseFrame {

    public FrameChangelog() {
        setResizable(false);
        setIconImage(Resources.getIcon("changelog").getImage());
        setTitle("Changelog");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 527, 295);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        JLabel lblYourVersion = new JLabel("Your version: " + Version.getVersion());

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
                dispose();
            }
        });
        btnClose.setIcon(Resources.getIcon("delete"));
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED, 304, Short.MAX_VALUE).addComponent(lblYourVersion).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnClose)).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE));
        gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnClose).addComponent(lblYourVersion)).addGap(30)));

        contentPane.setLayout(gl_contentPane);

        JEditorPane com;
        try {
            com = new JEditorPane(new URL(Constants.CHANGELOG_URL));
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorDialog.create(ex);
            return;
        }
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

        scrollPane.setViewportView(com);

        com.setSelectionStart(0);
        com.setSelectionEnd(0);

        setLocationRelativeTo(null);
    }
}
