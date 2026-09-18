package pe.reciclaya.app.ui.util;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

public class EventObserver<T> implements Observer<Event<T>> {
    private final OnEventChanged<T> onEventChanged;

    public EventObserver(OnEventChanged<T> onEventChanged) {
        this.onEventChanged = onEventChanged;
    }

    @Override
    public void onChanged(@Nullable Event<T> event) {
        if (event == null || onEventChanged == null) return;

        T content = event.getContentIfNotHandled();
        if (content != null) onEventChanged.onUnhandledContent(content);
    }

    public interface OnEventChanged<T>{
        void onUnhandledContent(T data);
    }
}
