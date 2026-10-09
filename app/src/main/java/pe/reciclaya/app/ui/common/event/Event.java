package pe.reciclaya.app.ui.common.event;

public class Event<T> {
    private boolean hasBeenHandled = false;
    private final T content;

    public Event(T content) {
        this.content = content;
    }

    public T getContentIfNotHandled() {
        if(hasBeenHandled) return null;

        hasBeenHandled = true;
        return content;
    }
}
