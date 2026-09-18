package pe.reciclaya.app.ui.util;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

public class EventObserver<T> implements Observer<Event<T>> {
    private final OnEventChanged<T> onEventChanged;

    public EventObserver(OnEventChanged<T> onEventChanged) {
        this.onEventChanged = onEventChanged;
    }

    @Override
    public void onChanged(@Nullable Event<T> tEvent) {
        if(tEvent != null && tEvent.getContentIfNotHandled() != null && onEventChanged != null) {
            onEventChanged.onUnhandledContent(tEvent.getContentIfNotHandled());
        }
    }

    public interface OnEventChanged<T>{
        void onUnhandledContent(T data);
    }
}
