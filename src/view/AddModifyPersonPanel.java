package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddModifyPersonPanel {
    private JPanel jAddModifyPanel;

    public AddModifyPersonPanel() {
        jAddModifyPanel = new JPanel();
        jAddModifyPanel.setBackground(new Color(23,169,146));
        jAddModifyPanel.setPreferredSize(new Dimension(420, 64));
        jAddModifyPanel.setBorder(new EmptyBorder(16, 0, 16, 0));
    }

    public JPanel getJAddModifyPanel() {
        return jAddModifyPanel;
    }
}
