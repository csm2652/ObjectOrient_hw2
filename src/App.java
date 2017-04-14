import presenter.MainPresenter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class App extends JFrame implements MainPresenter.View {
    private JPanel activity_main;
    // define Presenter
    public MainPresenter mainPresenter;

    private JTabbedPane jTab;

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

        setJTab();

        add(jTab);
        //MainPresenter.switchScreen(false);
        // set Device
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(23,169,146));
        setSize(432, 739);
        setVisible(true);
    }

    private void setJTab() {
        // init Tab (최근기록, 연락처를 볼수 있는 Pane)
        jTab = new JTabbedPane();
        jTab.setBackground(new Color(23,169,146));
        ImageIcon icon = new ImageIcon("src\\resource\\images\\img_recent_call.jpg");
        jTab.addTab("", icon, mainPresenter.getRecentCallPanel());
        icon = new ImageIcon("src\\resource\\images\\img_recent_sms.jpg");
        jTab.addTab("", icon, mainPresenter.getRecentSMSPanel());
        icon = new ImageIcon("src\\resource\\images\\img_addressbook.jpg");
        jTab.addTab("", icon, mainPresenter.getAddressBookPanel());

        // accept Tab
        jTab.addChangeListener((ChangeEvent) -> {
            mainPresenter.touchMenu(jTab.getSelectedIndex());
        });
    }

    public static void main(String[] args) {
        new App();
    }

    @Override
    public void switchScreenMain() {
        getContentPane().removeAll();
        getContentPane().add(jTab);
        revalidate();
        repaint();
    }

    @Override
    public void switchScreenAddAndModify() {
        getContentPane().removeAll();
        getContentPane().add(mainPresenter.getAddModifyPersonPanel());
        revalidate();
        repaint();
    }
}