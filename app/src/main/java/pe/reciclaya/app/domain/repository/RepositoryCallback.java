package pe.reciclaya.app.domain.repository;

public interface RepositoryCallback<T> {
    void onSuccess(T data);
    void onError(String errorMessage);
}
