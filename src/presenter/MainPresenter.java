package presenter;

import view.AddModifyPersonPanel;
import view.AddressBookPanel;
import view.RecentCallPanel;
import view.RecentSMSPanel;

import javax.swing.*;

public class MainPresenter {
    private static View view;
    // declare Panel
    private RecentCallPanel recentCallPanel = null;
    private RecentSMSPanel recentSMSPanel = null;
    private static AddressBookPanel addressBookPanel = null;
    private AddModifyPersonPanel addModifyPersonPanel = null;

    public MainPresenter(View view) {
        this.view = view;

        // init Panel
        recentCallPanel = new RecentCallPanel();
        recentSMSPanel = new RecentSMSPanel();
        addressBookPanel = new AddressBookPanel();
        addModifyPersonPanel = new AddModifyPersonPanel();

    }

    public RecentCallPanel getRecentCallPanel() {
        return recentCallPanel;
    }
    public RecentSMSPanel getRecentSMSPanel() {
        return recentSMSPanel;
    }
    public AddressBookPanel getAddressBookPanel() {
        return addressBookPanel;
    }
    public JPanel getAddModifyPersonPanel() { return addModifyPersonPanel.getJAddModifyPanel(); }

    public void touchMenu(int index) {
        System.out.println(index + "clicked");
    }

    public static void switchScreen(boolean isSwitched) {
        if (!isSwitched) {
            view.switchScreenMain();
            addressBookPanel.refresh();
        } else {
            view.switchScreenAddAndModify();
        }
    }

    public interface View {
        void switchScreenMain();
        void switchScreenAddAndModify();
    }
}
