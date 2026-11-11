package pe.reciclaya.app.ui.main.reputation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import pe.reciclaya.app.data.repository.user.ReputationRepositoryImp;
import pe.reciclaya.app.domain.model.reputation.Reputation;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.ReputationRepository;
import pe.reciclaya.app.ui.common.event.Event;

public final class ReputationViewModel extends ViewModel {
    private final ReputationRepository repository = new ReputationRepositoryImp();
    private final MutableLiveData<Reputation> reputation = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<Event<String>> message = new MutableLiveData<>();
    public LiveData<Reputation> getReputation() { return reputation; }
    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<Event<String>> getMessage() { return message; }
    public void load(int userId) {
        loading.setValue(true);
        repository.get(userId, new RepositoryCallback<Reputation>() {
            @Override public void onSuccess(Reputation value) {
                loading.postValue(false);
                reputation.postValue(value);
            }
            @Override public void onError(String errorMessage) {
                loading.postValue(false);
                message.postValue(new Event<>(errorMessage));
            }
        });
    }
    @Override protected void onCleared() { repository.close(); super.onCleared(); }
}
