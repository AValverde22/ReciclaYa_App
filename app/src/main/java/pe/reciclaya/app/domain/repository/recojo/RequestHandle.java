package pe.reciclaya.app.domain.repository.recojo;

public interface RequestHandle {
    void cancel();
    RequestHandle NONE = () -> {};
}
