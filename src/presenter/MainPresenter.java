package presenter;

import view.AddressBookPanel;
import view.RecentCallPanel;
import view.RecentSMSPanel;

public class MainPresenter {
    private View view;
    // declare Panel
    public RecentCallPanel recentCallPanel = null;
    public RecentSMSPanel recentSMSPanel = null;
    public AddressBookPanel addressBookPanel = null;

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

    public interface View {

    }
}
