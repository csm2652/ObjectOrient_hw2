package view;

import presenter.AddModifyPersonPresenter;
import presenter.MainPresenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class AddModifyPersonPanel implements AddModifyPersonPresenter.View{
    private JPanel jAddModifyPanel;
    // Tool Panel Bar
    private JPanel jBarPanel;
    private JPanel jInfoPanel;

    private JTextArea jTextName;
    private JTextArea jTextNumber;
    private JTextArea jTextGroup;
    private JTextArea jTextEmail;

    private AddModifyPersonPresenter addModifyPersonPresenter;

    public AddModifyPersonPanel() {
        addModifyPersonPresenter = new AddModifyPersonPresenter(this);
        jAddModifyPanel = new JPanel();
        jAddModifyPanel.setBorder(new EmptyBorder(-5, 0, 0, 0));
        setBarUI();
        setInfoUI();
    }

    private void setBarUI() {
        jBarPanel = new JPanel();
        jBarPanel.setBackground(new Color(23,169,146));
        jBarPanel.setPreferredSize(new Dimension(420, 64));

        JButton btnBack = new JButton("");
        btnBack.addActionListener(new AddModifyPersonPanel.backBtnListener());
        ImageIcon iConBack = new ImageIcon("src\\resource\\images\\img_back.png");
        btnBack.setIcon(iConBack);
        btnBack.setBorder(new EmptyBorder(0, 0, 0, 0));

        JButton btnSavePerson = new JButton("");
        btnSavePerson.addActionListener(new AddModifyPersonPanel.saveBtnListener());
        ImageIcon iConSave = new ImageIcon("src\\resource\\images\\img_save.png");
        btnSavePerson.setIcon(iConSave);
        btnSavePerson.setBorder(new EmptyBorder(0, 0, 0, 0));

        jBarPanel.add(btnBack);
        jBarPanel.add(btnSavePerson);

        jAddModifyPanel.add(jBarPanel, "North");
        jAddModifyPanel.setVisible(true);
    }

    private void setInfoUI() {
        jInfoPanel = new JPanel();
        jInfoPanel.setLayout(new FlowLayout());
        jInfoPanel.setPreferredSize(new Dimension(400, 600));

        JLabel jName = new JLabel("이름", SwingConstants.LEFT);
        jName.setPreferredSize(new Dimension(400, 40));
        jName.setBorder(new EmptyBorder(20, 0, 0, 0));

        jTextName = new JTextArea();
        jTextName.setPreferredSize(new Dimension(400, 40));
        jTextName.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.gray));
        jTextName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jTextName.setBorder(BorderFactory.createMatteBorder(0,0,2,0, new Color(23,169,146)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                jTextName.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.gray));
            }
        });
        jTextName.setColumns(20);

        JLabel jNumber = new JLabel("번호", SwingConstants.LEFT);
        jNumber.setPreferredSize(new Dimension(400, 40));
        jNumber.setBorder(new EmptyBorder(20, 0, 0, 0));

        jTextNumber = new JTextArea();
        jTextNumber.setPreferredSize(new Dimension(400, 40));
        jTextNumber.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jTextNumber.setBorder(BorderFactory.createMatteBorder(0,0,2,0, new Color(23,169,146)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                jTextNumber.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.gray));
            }
        });
        jTextNumber.setColumns(20);

        JLabel jGroup = new JLabel("그룹", SwingConstants.LEFT);
        jGroup.setPreferredSize(new Dimension(400, 40));
        jGroup.setBorder(new EmptyBorder(20, 0, 0, 0));

        jTextGroup = new JTextArea();
        jTextGroup.setPreferredSize(new Dimension(400, 40));
        jTextGroup.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jTextGroup.setBorder(BorderFactory.createMatteBorder(0,0,2,0, new Color(23,169,146)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                jTextGroup.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.gray));
            }
        });
        jTextGroup.setColumns(20);

        JLabel jEmail = new JLabel("Email", SwingConstants.LEFT);
        jEmail.setPreferredSize(new Dimension(400, 40));
        jEmail.setBorder(new EmptyBorder(20, 0, 0, 0));

        jTextEmail = new JTextArea();
        jTextEmail.setPreferredSize(new Dimension(400, 40));
        jTextEmail.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jTextEmail.setBorder(BorderFactory.createMatteBorder(0,0,2,0, new Color(23,169,146)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                jTextEmail.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.gray));
            }
        });
        jTextEmail.setColumns(20);

        changeBorderGrayText();

        jInfoPanel.add(jName);
        jInfoPanel.add(jTextName);
        jInfoPanel.add(jNumber);
        jInfoPanel.add(jTextNumber);
        jInfoPanel.add(jGroup);
        jInfoPanel.add(jTextGroup);
        jInfoPanel.add(jEmail);
        jInfoPanel.add(jTextEmail);

        jAddModifyPanel.add(jInfoPanel);
        jAddModifyPanel.setVisible(true);
    }

    private class backBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MainPresenter.switchScreen(false);
        }
    }
    private class saveBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addModifyPersonPresenter.savePerson(jTextName.getText(), jTextNumber.getText(),
                    jTextGroup.getText(), jTextEmail.getText());
        }
    }

    private void changeBorderGrayText() {
        jTextNumber.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.gray));
        jTextGroup.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.gray));
        jTextEmail.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.gray));
    }

    public JPanel getJAddModifyPanel() {
        return jAddModifyPanel;
    }

    @Override
    public void setTextNoArgument() {
        jTextName.setText("");
        jTextNumber.setText("");
        jTextGroup.setText("");
        jTextEmail.setText("");
    }

    @Override
    public void setTextArgument(String name, String number, String group, String email) {
        jTextName.setText(name);
        jTextNumber.setText(number);
        jTextGroup.setText(group);
        jTextEmail.setText(email);
    }
}
