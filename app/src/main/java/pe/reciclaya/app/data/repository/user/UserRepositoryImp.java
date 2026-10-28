package pe.reciclaya.app.data.repository.user;

import android.content.Context;

import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.user.UserRepository;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.data.model.user.response.UserScoreResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepositoryImp implements UserRepository {
    private final AppPreferencesManager appPreferencesManager;
    private final User user;
    private Call<UserScoreResponse> scoreRequest;

    public UserRepositoryImp(Context context) {
        appPreferencesManager = AppPreferencesManager.getInstance(context);
        user = User.getInstance();
    }

    @Override public int getID() {return user.getID(); }
    @Override public String getFullName() { return user.getFullName(); }
    @Override public String getEmail() { return user.getEmail(); }
    @Override public UserRole getRole() { return user.getRole(); }
    @Override public String getProfilePhotoURL() { return user.getProfilePhotoURL(); }
    @Override public float getScore() { return user.getScore(); }
    @Override public void refreshScore(RepositoryCallback<Float> callback) {
        cancelScoreRefresh();
        final int userId = user.getID();
        if (userId <= 0) return;
        Call<UserScoreResponse> request = BackendClient.getUserService().getScore(userId);
        scoreRequest = request;
        request.enqueue(new Callback<UserScoreResponse>() {
            private boolean isCurrent() {
                return scoreRequest == request && !request.isCanceled() && User.getInstance() == user;
            }
            @Override public void onResponse(Call<UserScoreResponse> call, Response<UserScoreResponse> response) {
                if (!isCurrent()) return;
                scoreRequest = null;
                Float value = response.body() == null ? null : response.body().getScore();
                if (!response.isSuccessful() || value == null || !Float.isFinite(value) || value < 0 || value > 5) {
                    callback.onError("No se pudo actualizar la puntuación.");
                    return;
                }
                user.setScore(value);
                appPreferencesManager.putFloat("score", value);
                callback.onSuccess(value);
            }
            @Override public void onFailure(Call<UserScoreResponse> call, Throwable error) {
                if (!isCurrent()) return;
                scoreRequest = null;
                callback.onError("No se pudo actualizar la puntuación.");
            }
        });
    }
    @Override public void cancelScoreRefresh() {
        if (scoreRequest != null) scoreRequest.cancel();
        scoreRequest = null;
    }
    @Override public void logout() {
        cancelScoreRefresh();
        User.clearInstance();
        appPreferencesManager.cleanAll();
    }
}

