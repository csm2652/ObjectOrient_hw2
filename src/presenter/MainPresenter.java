package presenter;

import view.AddressBookPanel;
import view.RecentCallPanel;
import view.RecentSMSPanel;

public class MainPresenter {
    private static View view;
    // declare Panel
    private RecentCallPanel recentCallPanel = null;
    private RecentSMSPanel recentSMSPanel = null;
    private AddressBookPanel addressBookPanel = null;

    public MainPresenter(View view) {
        this.view = view;

        // init Panel
        recentCallPanel = new RecentCallPanel();
        recentSMSPanel = new RecentSMSPanel();
        addressBookPanel = new AddressBookPanel();

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

    public void touchMenu(int index) {
        System.out.println(index + "clicked");
    }

    public static void switchScreen(boolean isSwitched) {
        if (!isSwitched) {
            view.switchScreenMain();
        } else {
            view.switchScreenAddAndModify();
        }
    }

    public interface View {
        void switchScreenMain();
        void switchScreenAddAndModify();
    }
}
