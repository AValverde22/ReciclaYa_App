package pe.reciclaya.app.ui.main.rewards;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import pe.reciclaya.app.data.repository.user.RewardsRepositoryImp;
import pe.reciclaya.app.domain.model.rewards.Benefit;
import pe.reciclaya.app.domain.model.rewards.RewardsAccount;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.RewardsRepository;
import pe.reciclaya.app.ui.common.event.Event;

public final class RewardsViewModel extends ViewModel {
    private final RewardsRepository repository = new RewardsRepositoryImp();
    private final MutableLiveData<RewardsAccount> account = new MutableLiveData<>();
    private final MutableLiveData<List<Benefit>> benefits = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<Event<String>> message = new MutableLiveData<>();
    private int pending;

    public RewardsViewModel() { refresh(); }
    public LiveData<RewardsAccount> getAccount() { return account; }
    public LiveData<List<Benefit>> getBenefits() { return benefits; }
    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<Event<String>> getMessage() { return message; }

    public void refresh() {
        pending = 2;
        loading.setValue(true);
        repository.account(new RepositoryCallback<RewardsAccount>() {
            @Override public void onSuccess(RewardsAccount value) { account.postValue(value); done(); }
            @Override public void onError(String errorMessage) { message.postValue(new Event<>(errorMessage)); done(); }
        });
        repository.benefits(new RepositoryCallback<List<Benefit>>() {
            @Override public void onSuccess(List<Benefit> value) { benefits.postValue(value); done(); }
            @Override public void onError(String errorMessage) { message.postValue(new Event<>(errorMessage)); done(); }
        });
    }

    public void redeem(Benefit benefit) {
        RewardsAccount current = account.getValue();
        if (current == null) {
            message.setValue(new Event<>("No se pudo consultar el saldo de puntos."));
            return;
        }
        if (current.balance < benefit.pointsRequired) {
            message.setValue(new Event<>("No cuenta con puntos suficientes para realizar este canje."));
            return;
        }
        loading.setValue(true);
        repository.redeem(benefit.id, new RepositoryCallback<Integer>() {
            @Override public void onSuccess(Integer value) {
                message.postValue(new Event<>("Beneficio canjeado correctamente."));
                refresh();
            }
            @Override public void onError(String errorMessage) {
                loading.postValue(false);
                message.postValue(new Event<>(errorMessage));
            }
        });
    }

    private synchronized void done() {
        pending--;
        if (pending <= 0) loading.postValue(false);
    }

    @Override protected void onCleared() {
        repository.close();
        super.onCleared();
    }
}
