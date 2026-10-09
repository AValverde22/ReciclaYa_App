package pe.reciclaya.app.ui.main.tabs;

import androidx.fragment.app.Fragment;

import java.util.function.Supplier;

public class TabData {
    private final Supplier<Fragment> fragmentCreator;
    private final String tabName;
    private final int tabIcon;

    public TabData(Supplier<Fragment> fragmentCreator, String tabName, int tabIcon) {
        this.fragmentCreator = fragmentCreator;
        this.tabName = tabName;
        this.tabIcon = tabIcon;
    }

    public Supplier<Fragment> getFragmentCreator() { return fragmentCreator; }
    public String getTabName() { return tabName; }
    public int getTabIcon() { return tabIcon; }
}
