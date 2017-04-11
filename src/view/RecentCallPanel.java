package view;

import presenter.CallPresenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecentCallPanel extends JPanel implements CallPresenter.View {
    // Define Presenter
    public CallPresenter callPresenter;

    // Construct
    public RecentCallPanel() {
        // Accept Presenter
        callPresenter = new CallPresenter(this);

        setLayout(new BorderLayout());
        // Bar Init (UI)
        showCallBarPanel();
        showRecentCall();
    }

    // Make Bar Panel (two button: add, del recent Call)
    private void showCallBarPanel() {
        callPresenter.setjCallBarPanel();

        JButton btnAddCall = new JButton("");
        btnAddCall.addActionListener(new RecentCallPanel.AddCallBtnListener());
        ImageIcon iconAddCall = new ImageIcon("src\\resource\\images\\img_add_call.png");
        btnAddCall.setIcon(iconAddCall);
        btnAddCall.setBorder(new EmptyBorder(0, 0, 0, 0));

        JButton btnDelCall = new JButton("");
        btnDelCall.addActionListener(new RecentCallPanel.DelCallBtnListener());
        ImageIcon iconDelCall = new ImageIcon("src\\resource\\images\\img_del.png");
        btnDelCall.setIcon(iconDelCall);
        btnDelCall.setBorder(new EmptyBorder(0, 0, 0, 0));

        callPresenter.getjCallBarPanel().add(btnAddCall);
        callPresenter.getjCallBarPanel().add(btnDelCall);

        add(callPresenter.getjCallBarPanel(), "North");
        setVisible(true);
    }

    // Show List (Array to JList)
    // Accept Renderer, Add Pane
    private void showRecentCall() {
        callPresenter.refreshRecentCall();

        add(callPresenter.getjRecentCallPane(), "Center");
        setVisible(true);
    }

    private class AddCallBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            callPresenter.touchAddCall();
        }
    }

    private class DelCallBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            callPresenter.touchDelCall();
        }
    }

    // Render in Scroll List
    class CallRenderer extends JLabel implements ListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {

            setText(callPresenter.getStringValue(value));

            ImageIcon iconRecv = new ImageIcon("src\\resource\\images\\img_person_recv.png");
            ImageIcon iconSend = new ImageIcon("src\\resource\\images\\img_person_send.png");

            if (callPresenter.getTypeValue(value).equals("recv")) {
                setIcon(iconRecv);
            } else if (callPresenter.getTypeValue(value).equals("send")){
                setIcon(iconSend);
            }

            if (isSelected) {
                callPresenter.setiClickedList(index);
                setForeground(Color.RED);
            } else {
                setForeground(Color.black);
            }
            return this;
        }
    }


    @Override
    public void deleteList() {
        remove(callPresenter.getjRecentCallPane());
    }

    @Override
    public void acceptRenderer() {
        callPresenter.getList().setCellRenderer(new RecentCallPanel.CallRenderer());
        add(callPresenter.jRecentCallPane);
        revalidate();
        setVisible(true);
    }
}

