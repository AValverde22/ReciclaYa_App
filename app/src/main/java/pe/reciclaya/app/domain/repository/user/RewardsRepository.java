package pe.reciclaya.app.domain.repository.user;

import java.util.List;
import pe.reciclaya.app.domain.model.rewards.Benefit;
import pe.reciclaya.app.domain.model.rewards.RewardsAccount;
import pe.reciclaya.app.domain.repository.RepositoryCallback;

public interface RewardsRepository {
    void account(RepositoryCallback<RewardsAccount> callback);
    void benefits(RepositoryCallback<List<Benefit>> callback);
    void redeem(int benefitId, RepositoryCallback<Integer> callback);
    void close();
}
