package pe.reciclaya.app.domain.repository.user;

public interface RepositoryCallback<T> {
    void onSuccess(T data);
    void onError(String errorMessage);
}
