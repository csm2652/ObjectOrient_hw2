package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class AddressBookPanel extends JPanel {
    // Tool Panel Bar
    private JPanel jAddressBookBarPanel;

    // Scroll Panel
    public JScrollPane jAddressBookPane;

    public AddressBookPanel() {
        showAddressBookBarPanel();
        showAddressBookPane();
        setVisible(true);
    }

    private void showAddressBookBarPanel() {
        // init Panel
        jAddressBookBarPanel = new JPanel();
        jAddressBookBarPanel.setBackground(new Color(23,169,146));
        jAddressBookBarPanel.setPreferredSize(new Dimension(420, 64));

        JButton btnAddCall = new JButton("");
        //btnAddCall.addActionListener(new RecentCallPanel.AddCallBtnListener());
        ImageIcon iconAddCall = new ImageIcon("src\\resource\\images\\img_add_call.png");
        btnAddCall.setIcon(iconAddCall);
        btnAddCall.setBorder(new EmptyBorder(0, 0, 0, 0));

        JButton btnDelCall = new JButton("");
        //btnDelCall.addActionListener(new RecentCallPanel.DelCallBtnListener());
        ImageIcon iconDelCall = new ImageIcon("src\\resource\\images\\img_del.png");
        btnDelCall.setIcon(iconDelCall);
        btnDelCall.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(jAddressBookBarPanel, "North");
        setVisible(true);
    }

    private void showAddressBookPane() {
        jAddressBookPane = new JScrollPane();
        jAddressBookPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        jAddressBookPane.setSize(418, 600);
    }

}
