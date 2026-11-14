package pe.reciclaya.app.data.repository.user;

import android.content.Context;
import org.json.JSONObject;
import java.util.Collections;
import pe.reciclaya.app.data.local.AppPreferencesManager;
import pe.reciclaya.app.data.model.user.request.profile.UpdateProfileRequest;
import pe.reciclaya.app.data.model.user.response.ProfileResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.domain.model.user.Profile;
import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.model.user.UserRole;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.ProfileRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class ProfileRepositoryImp implements ProfileRepository {
    private final User user = User.getInstance();
    private final AppPreferencesManager preferences;
    private Call<ProfileResponse> request;

    public ProfileRepositoryImp(Context context) {
        preferences = AppPreferencesManager.getInstance(context);
    }

    @Override public Profile local() {
        return new Profile(user.getID(), user.getFullName(), user.getEmail(), user.getRole(),
                user.getProfilePhotoURL(), user.getScore(), "Validado", Collections.emptyList());
    }

    @Override public void load(RepositoryCallback<Profile> callback) {
        execute(BackendClient.getUserService().getProfile(user.getID()), callback);
    }

    @Override public void update(String fullName, String email, String profilePhotoUrl,
                                 RepositoryCallback<Profile> callback) {
        execute(BackendClient.getUserService().updateProfile(user.getID(),
                new UpdateProfileRequest(fullName, email, profilePhotoUrl)), callback);
    }

    private void execute(Call<ProfileResponse> call, RepositoryCallback<Profile> callback) {
        close();
        request = call;
        call.enqueue(new Callback<ProfileResponse>() {
            @Override public void onResponse(Call<ProfileResponse> current, Response<ProfileResponse> response) {
                if (current.isCanceled() || request != current) return;
                request = null;
                if (!response.isSuccessful() || response.body() == null) {
                    callback.onError(errorMessage(response));
                    return;
                }
                Profile profile = map(response.body());
                save(profile);
                callback.onSuccess(profile);
            }

            @Override public void onFailure(Call<ProfileResponse> current, Throwable error) {
                if (current.isCanceled() || request != current) return;
                request = null;
                callback.onError("Sin conexión. Vuelve a intentarlo.");
            }
        });
    }

    private Profile map(ProfileResponse response) {
        return new Profile(response.id, response.fullName, response.email, UserRole.fromString(response.role),
                response.profilePhotoUrl, response.score, response.validationStatus, response.sensitiveFields);
    }

    private void save(Profile profile) {
        user.setID(profile.id);
        user.setFullName(profile.fullName);
        user.setEmail(profile.email);
        user.setRole(profile.role);
        user.setProfilePhotoURL(profile.profilePhotoUrl);
        user.setScore(profile.score);
        preferences.putInt("id", profile.id);
        preferences.putString("fullName", profile.fullName);
        preferences.putString("email", profile.email);
        preferences.putString("role", profile.role.getRole());
        preferences.putString("profilePhotoURL", profile.profilePhotoUrl);
        preferences.putFloat("score", profile.score);
    }

    private String errorMessage(Response<?> response) {
        try {
            if (response.errorBody() != null)
                return new JSONObject(response.errorBody().string()).optString("message", fallback(response.code()));
        } catch (Exception ignored) { }
        return fallback(response.code());
    }

    private String fallback(int code) {
        return code == 409 ? "El correo ingresado ya está registrado."
                : "No se pudo actualizar la información personal.";
    }

    @Override public void close() {
        if (request != null) request.cancel();
        request = null;
    }
}
