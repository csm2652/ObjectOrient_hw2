package view;

import presenter.AddressBookPresenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class AddressBookPanel extends JPanel implements AddressBookPresenter.View {
    // Define Presenter
    public AddressBookPresenter addressBookPresenter;

    // Tool Panel Bar
    private JPanel jAddressBookBarPanel;

    // Scroll Panel
    public JScrollPane jPersonListPane;

    // Construct
    public AddressBookPanel() {
        // Accept Presenter
        addressBookPresenter = new AddressBookPresenter(this);

        setLayout(new BorderLayout());
        // Bar Init (UI)
        showBarPanel();
        // List Init (UI)
        showPersonList();
    }

    // Make Bar Panel (two button: add, del recent Call)
    private void showBarPanel() {
        jAddressBookBarPanel = new JPanel();
        jAddressBookBarPanel.setBackground(new Color(23,169,146));
        jAddressBookBarPanel.setPreferredSize(new Dimension(420, 64));
        jAddressBookBarPanel.setBorder(new EmptyBorder(16, 0, 16, 0));

        JTextArea jTextArea = new JTextArea();
        jTextArea.setColumns(20);
        jTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println(jTextArea.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println(jTextArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        JButton btnAddPerson = new JButton();
        btnAddPerson.addActionListener(new AddressBookPanel.AddPersonBtnListener());
        ImageIcon iconAddPerson = new ImageIcon("src\\resource\\images\\img_add_person.png");
        btnAddPerson.setIcon(iconAddPerson);
        btnAddPerson.setBorder(new EmptyBorder(0, 0, 0, 0));
        btnAddPerson.setPreferredSize(new Dimension(28, 28));

        JButton btnDelPerson = new JButton();
        btnDelPerson.addActionListener(new AddressBookPanel.DelPersonBtnListener());
        ImageIcon iconDelPerson = new ImageIcon("src\\resource\\images\\img_del_person.png");
        btnDelPerson.setIcon(iconDelPerson);
        btnDelPerson.setBorder(new EmptyBorder(0, 0, 0, 0));
        btnDelPerson.setPreferredSize(new Dimension(28, 28));

        JButton btnModPerson = new JButton();
        btnModPerson.addActionListener(new AddressBookPanel.ModPersonBtnListener());
        ImageIcon iconModPerson = new ImageIcon("src\\resource\\images\\img_mod_person.png");
        btnModPerson.setIcon(iconModPerson);
        btnModPerson.setBorder(new EmptyBorder(0, 0, 0, 0));
        btnModPerson.setPreferredSize(new Dimension(28, 28));

        jAddressBookBarPanel.add(jTextArea);
        jAddressBookBarPanel.add(btnAddPerson);
        jAddressBookBarPanel.add(btnDelPerson);
        jAddressBookBarPanel.add(btnModPerson);

        add(jAddressBookBarPanel, "North");
        setVisible(true);
    }

    private class AddPersonBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addressBookPresenter.touchAddPerson();
        }
    }

    private class DelPersonBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addressBookPresenter.touchDelPerson();
        }
    }

    private class ModPersonBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addressBookPresenter.touchModPerson();
        }
    }

    // Show List (Array to JList)
    // Accept Renderer, Add Pane
    private void showPersonList() {
        addressBookPresenter.refreshPersonList();

        jPersonListPane = new JScrollPane(addressBookPresenter.getList());
        jPersonListPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        jPersonListPane.setSize(418, 599);

        addressBookPresenter.refreshAcceptInView();
        add(jPersonListPane, "Center");
        setVisible(true);
    }


    // Render in Scroll List
    class CallRenderer extends JLabel implements ListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {

            setText(addressBookPresenter.getStringNameNumber(value));

            ImageIcon iconRecv = new ImageIcon("src\\resource\\images\\img_person.png");
            setIcon(iconRecv);

            if (isSelected) {
                addressBookPresenter.setiClickedList(index);
                setForeground(Color.RED);
            } else {
                setForeground(Color.black);
            }
            return this;
        }
    }


    @Override
    public void deleteList() {
        remove(jPersonListPane);
    }

    @Override
    public void acceptRenderer() {
        jPersonListPane = new JScrollPane(addressBookPresenter.getList());
        jPersonListPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        jPersonListPane.setSize(418, 600);

        addressBookPresenter.getList().setCellRenderer(new AddressBookPanel.CallRenderer());
        add(jPersonListPane);
        revalidate();
        setVisible(true);
    }
}

