package view;

import presenter.CallPresenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class RecentCallPanel extends JPanel implements CallPresenter.View {
    // Define Presenter
    public CallPresenter callPresenter;

    // Tool Panel Bar
    private JPanel jCallBarPanel;

    // Scroll Panel
    public JScrollPane jRecentCallPane;

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
        jCallBarPanel = new JPanel();
        jCallBarPanel.setBackground(new Color(23,169,146));
        jCallBarPanel.setPreferredSize(new Dimension(420, 64));

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

        jCallBarPanel.add(btnAddCall);
        jCallBarPanel.add(btnDelCall);

        add(jCallBarPanel, "North");
        setVisible(true);
    }

    // Show List (Array to JList)
    // Accept Renderer, Add Pane
    private void showRecentCall() {
        callPresenter.refreshRecentCall();

        jRecentCallPane = new JScrollPane(callPresenter.getList());
        jRecentCallPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        jRecentCallPane.setSize(418, 600);

        callPresenter.refreshAcceptInView();
        add(jRecentCallPane, "Center");
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
        remove(jRecentCallPane);
    }

    @Override
    public void acceptRenderer() {
        jRecentCallPane = new JScrollPane(callPresenter.getList());
        jRecentCallPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        jRecentCallPane.setSize(418, 600);

        callPresenter.getList().setCellRenderer(new RecentCallPanel.CallRenderer());
        add(jRecentCallPane);
        revalidate();
        setVisible(true);
    }
}

