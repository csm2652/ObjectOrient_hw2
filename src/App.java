import presenter.MainPresenter;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame implements MainPresenter.View {
    private JPanel activity_main;
    // define Presenter
    public MainPresenter mainPresenter;

    private App() {
        // access Presenter
        mainPresenter = new MainPresenter(this);

        // jTab UI setting (remove border)
        UIManager.put("TabbedPane.contentOpaque", false);
        Color tabBorderColor = new Color(23,169,146);
        UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(0,0,0,0));
        UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", false);
        UIManager.put("TabbedPane.borderHightlightColor", tabBorderColor);
        UIManager.put("TabbedPane.darkShadow", tabBorderColor);
        UIManager.put("TabbedPane.shadow", tabBorderColor);
        UIManager.put("TabbedPane.light", tabBorderColor);
        UIManager.put("TabbedPane.highlight", tabBorderColor);
        UIManager.put("TabbedPane.focus", tabBorderColor);
        UIManager.put("TabbedPane.selectHighlight", tabBorderColor);

        // set Title
        setTitle("Address Book");

        // init Tab
        JTabbedPane jTab = new JTabbedPane();
        jTab.setBackground(new Color(23,169,146));
        ImageIcon icon = new ImageIcon("src\\resource\\images\\img_recent_call.jpg");
        jTab.addTab("", icon, mainPresenter.getRecentCallPanel());
        icon = new ImageIcon("src\\resource\\images\\img_recent_sms.jpg");
        jTab.addTab("", icon, mainPresenter.getRecentSMSPanel());
        icon = new ImageIcon("src\\resource\\images\\img_addressbook.jpg");
        jTab.addTab("", icon, mainPresenter.getAddressBookPanel());

        // accept Tab
        add(jTab);
        jTab.addChangeListener((ChangeEvent) -> {
            mainPresenter.touchMenu(jTab.getSelectedIndex());
        });

        // set Device
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(23,169,146));
        setSize(432, 739);
        setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}