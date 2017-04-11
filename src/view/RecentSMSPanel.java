package view;

import model.SMS;
import presenter.SMSPresenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecentSMSPanel extends JPanel implements SMSPresenter.View{
    // Define Presenter
    public SMSPresenter smsPresenter;

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
        smsPresenter.setjSMSBarPanel();

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

        smsPresenter.getjSMSBarPanel().add(btnAddCall);
        smsPresenter.getjSMSBarPanel().add(btnDelCall);

        add(smsPresenter.getjSMSBarPanel(), "North");
        setVisible(true);
    }

    // Show List (Array to JList)
    // Accept Renderer, Add Pane
    private void showRecentSMS() {
        smsPresenter.refreshRecentSMS();

        add(smsPresenter.getjRecentSMSPane(), "Center");
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

            SMS entry = (SMS) value;
            setText("  " + entry.toStringNumber() + "  "  + entry.toStringContent() + " "+ entry.getTime());

            ImageIcon iconRecv = new ImageIcon("src\\resource\\images\\img_person_recv_sms.png");
            ImageIcon iconSend = new ImageIcon("src\\resource\\images\\img_person_send_sms.png");

            if (entry.getType().equals("recv")) {
                setIcon(iconRecv);
            } else if (entry.getType().equals("send")){
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
        remove(smsPresenter.getjRecentSMSPane());
    }

    @Override
    public void acceptRenderer() {
        smsPresenter.getList().setCellRenderer(new RecentSMSPanel.SMSRenderer());
        add(smsPresenter.jRecentSMSPane);
        revalidate();
        setVisible(true);
    }
}
