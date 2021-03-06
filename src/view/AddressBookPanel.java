package view;

import presenter.AddressBookPresenter;
import presenter.CallPresenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class AddressBookPanel extends JPanel implements AddressBookPresenter.View {
    // Define Presenter
    public AddressBookPresenter addressBookPresenter;

    // Tool Panel Bar
    private JPanel jAddressBookBarPanel;

    // Scroll Panel
    public JScrollPane jPersonListPane;
    private JTextArea jTextArea = new JTextArea();
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

        jTextArea = new JTextArea();
        jTextArea.setColumns(20);
        jTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                addressBookPresenter.changedTxtSearch(jTextArea.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                addressBookPresenter.changedTxtSearch(jTextArea.getText());
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

        acceptRenderer();
        add(jPersonListPane, "Center");
        setVisible(true);
    }

    public void refresh() {
        addressBookPresenter.refreshPresent();
        addressBookPresenter.changedTxtSearch(jTextArea.getText());
    }


    // Render in Scroll List
    class CallRenderer extends JLabel implements ListCellRenderer {
        private JButton jBtn;
        private JLayeredPane panel = new JLayeredPane();



        public CallRenderer() {
            panel.setLayout(new BorderLayout());
            jBtn = new JButton();
            ImageIcon iconCall = new ImageIcon("src\\resource\\images\\img_call.png");
            jBtn.setIcon(iconCall);
            jBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
            jBtn.setPreferredSize(new Dimension(40, 40));
        }

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

            panel.add(this, BorderLayout.WEST, 1);
            panel.add(jBtn, BorderLayout.EAST, 0);
            return panel;
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
        addressBookPresenter.getList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getX() > 350) {
                    clickButtonAt(e.getPoint());
                }
            }
        });
        add(jPersonListPane);
        revalidate();
        setVisible(true);
    }
    private void clickButtonAt(Point point)
    {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Calling",
                "AddressBook",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            int index = addressBookPresenter.getList().locationToIndex(point);
            addressBookPresenter.call(index);
        }


    }


    @Override
    public void acceptSearched() {
        jPersonListPane = new JScrollPane(addressBookPresenter.getListSearched());
        jPersonListPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        jPersonListPane.setSize(418, 600);

        addressBookPresenter.getListSearched().setCellRenderer(new AddressBookPanel.CallRenderer());
        addressBookPresenter.getListSearched().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getX() > 350) {
                    clickSearchedButtonAt(e.getPoint());
                }
            }
        });
        add(jPersonListPane);
        revalidate();
        setVisible(true);
    }

    private void clickSearchedButtonAt(Point point)
    {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Calling",
                "AddressBook",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            int index = addressBookPresenter.getListSearched().locationToIndex(point);
            addressBookPresenter.callSearched(index);
        }
    }

}

