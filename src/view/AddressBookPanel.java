package view;

import javax.swing.*;
import java.awt.*;

public class AddressBookPanel extends JPanel {
    Button button = new Button("전화번호부");
    public AddressBookPanel() {
        add(button);
        setVisible(true);
    }
}
