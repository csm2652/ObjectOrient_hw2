package view;

import presenter.SMSPresenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class RecentSMSPanel extends JPanel implements SMSPresenter.View{
    // Define Presenter
    public SMSPresenter smsPresenter;

    // Tool Panel Bar
    private JPanel jSMSBarPanel;

    // Scroll Panel
    public JScrollPane jRecentSMSPane;

    // Construct
    public RecentSMSPanel() {
        // Accept Presenter
        smsPresenter = new SMSPresenter(this);

        setLayout(new BorderLayout());
        // Bar Init (UI)
        showSMSBarPanel();
        showRecentSMS();
    }

    // Make Bar Panel (two button: add, del recent SMS)
    private void showSMSBarPanel() {
        // init Panel
        jSMSBarPanel = new JPanel();
        jSMSBarPanel.setBackground(new Color(23,169,146));
        jSMSBarPanel.setPreferredSize(new Dimension(420, 64));

        JButton btnAddCall = new JButton("");
        btnAddCall.addActionListener(new RecentSMSPanel.AddCallBtnListener());
        ImageIcon iconAddCall = new ImageIcon("src\\resource\\images\\img_add_sms.png");
        btnAddCall.setIcon(iconAddCall);
        btnAddCall.setBorder(new EmptyBorder(0, 0, 0, 0));

        JButton btnDelCall = new JButton("");
        btnDelCall.addActionListener(new RecentSMSPanel.DelCallBtnListener());
        ImageIcon iconDelCall = new ImageIcon("src\\resource\\images\\img_del.png");
        btnDelCall.setIcon(iconDelCall);
        btnDelCall.setBorder(new EmptyBorder(0, 0, 0, 0));

        jSMSBarPanel.add(btnAddCall);
        jSMSBarPanel.add(btnDelCall);

        add(jSMSBarPanel, "North");
        setVisible(true);
    }

    // Show List (Array to JList)
    // Accept Renderer, Add Pane
    private void showRecentSMS() {
        smsPresenter.refreshRecentSMS();

        jRecentSMSPane = new JScrollPane(smsPresenter.getList());
        jRecentSMSPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        jRecentSMSPane.setSize(418, 600);

        smsPresenter.refreshAcceptInView();
        add(jRecentSMSPane, "Center");
        setVisible(true);
    }

    private class AddCallBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            smsPresenter.touchAddSMS();
        }
    }

    private class DelCallBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            smsPresenter.touchDelSMS();
        }
    }

    // Render in Scroll List
    class SMSRenderer extends JLabel implements ListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {

            setText(smsPresenter.getStringValue(value));

            ImageIcon iconRecv = new ImageIcon("src\\resource\\images\\img_person_recv_sms.png");
            ImageIcon iconSend = new ImageIcon("src\\resource\\images\\img_person_send_sms.png");

            if (smsPresenter.getTypeValue(value).equals("recv")) {
                setIcon(iconRecv);
            } else if (smsPresenter.getTypeValue(value).equals("send")){
                setIcon(iconSend);
            }

            if (isSelected) {
                smsPresenter.setiClickedList(index);
                setForeground(Color.RED);
            } else {
                setForeground(Color.black);
            }
            return this;
        }
    }

    @Override
    public void deleteList() {
        remove(jRecentSMSPane);
    }

    @Override
    public void acceptRenderer() {
        jRecentSMSPane = new JScrollPane(smsPresenter.getList());
        jRecentSMSPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        jRecentSMSPane.setSize(420, 600);

        smsPresenter.getList().setCellRenderer(new RecentSMSPanel.SMSRenderer());
        add(jRecentSMSPane);
        revalidate();
        setVisible(true);
    }
}
