package pe.reciclaya.app.ui.main.tabs;

import androidx.fragment.app.Fragment;

public interface MainTabsAbstractFactory {
    Fragment crearFragment(int pos);
    String getTabName(int pos);
    int getTabIcon(int pos);
    int getTabCount();
}
