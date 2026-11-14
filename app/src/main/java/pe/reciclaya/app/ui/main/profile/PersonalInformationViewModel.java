package pe.reciclaya.app.ui.main.profile;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import pe.reciclaya.app.data.repository.user.ProfileRepositoryImp;
import pe.reciclaya.app.domain.model.user.Profile;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.ProfileRepository;
import pe.reciclaya.app.domain.service.ProfileValidation;
import pe.reciclaya.app.ui.common.event.Event;

public final class PersonalInformationViewModel extends AndroidViewModel {
    private final ProfileRepository repository;
    private final MutableLiveData<Profile> profile = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> fullNameError = new MutableLiveData<>();
    private final MutableLiveData<String> emailError = new MutableLiveData<>();
    private final MutableLiveData<String> photoError = new MutableLiveData<>();
    private final MutableLiveData<Event<String>> message = new MutableLiveData<>();

    public PersonalInformationViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepositoryImp(application.getApplicationContext());
        profile.setValue(repository.local());
        load();
    }

    public LiveData<Profile> getProfile() { return profile; }
    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<String> getFullNameError() { return fullNameError; }
    public LiveData<String> getEmailError() { return emailError; }
    public LiveData<String> getPhotoError() { return photoError; }
    public LiveData<Event<String>> getMessage() { return message; }

    public void load() {
        loading.setValue(true);
        repository.load(new RepositoryCallback<Profile>() {
            @Override public void onSuccess(Profile value) {
                loading.postValue(false);
                profile.postValue(value);
            }
            @Override public void onError(String errorMessage) {
                loading.postValue(false);
                message.postValue(new Event<>(errorMessage));
            }
        });
    }

    public void save(String fullName, String email, String photoUrl) {
        String nameError = ProfileValidation.fullName(fullName);
        String mailError = ProfileValidation.email(email);
        String urlError = ProfileValidation.photoUrl(photoUrl);
        fullNameError.setValue(nameError);
        emailError.setValue(mailError);
        photoError.setValue(urlError);
        if (nameError != null || mailError != null || urlError != null) return;

        loading.setValue(true);
        repository.update(fullName.trim().replaceAll("\\s+", " "), email.trim().toLowerCase(),
                photoUrl == null ? "" : photoUrl.trim(), new RepositoryCallback<Profile>() {
            @Override public void onSuccess(Profile value) {
                loading.postValue(false);
                profile.postValue(value);
                message.postValue(new Event<>("Información personal actualizada correctamente."));
            }
            @Override public void onError(String errorMessage) {
                loading.postValue(false);
                if (errorMessage.toLowerCase().contains("correo")) emailError.postValue(errorMessage);
                message.postValue(new Event<>(errorMessage));
            }
        });
    }

    @Override protected void onCleared() {
        repository.close();
        super.onCleared();
    }
}
