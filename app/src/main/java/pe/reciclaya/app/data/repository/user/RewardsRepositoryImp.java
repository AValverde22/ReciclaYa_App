package pe.reciclaya.app.data.repository.user;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pe.reciclaya.app.data.model.user.response.BenefitResponse;
import pe.reciclaya.app.data.model.user.response.BenefitsResponse;
import pe.reciclaya.app.data.model.user.response.PointMovementResponse;
import pe.reciclaya.app.data.model.user.response.PointsAccountResponse;
import pe.reciclaya.app.data.model.user.response.RedemptionResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.domain.model.rewards.Benefit;
import pe.reciclaya.app.domain.model.rewards.PointMovement;
import pe.reciclaya.app.domain.model.rewards.RewardsAccount;
import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.RewardsRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class RewardsRepositoryImp implements RewardsRepository {
    private final int userId = User.getInstance().getID();
    private final Set<Call<?>> requests = new HashSet<>();

    @Override public void account(RepositoryCallback<RewardsAccount> callback) {
        Call<PointsAccountResponse> call = BackendClient.getUserService().getPoints(userId);
        requests.add(call);
        call.enqueue(new Callback<PointsAccountResponse>() {
            @Override public void onResponse(Call<PointsAccountResponse> current, Response<PointsAccountResponse> response) {
                requests.remove(current);
                if (current.isCanceled()) return;
                if (!response.isSuccessful() || response.body() == null) {
                    callback.onError(error(response, "No se pudo consultar el saldo de puntos."));
                    return;
                }
                List<PointMovement> history = new ArrayList<>();
                if (response.body().history != null) for (PointMovementResponse item : response.body().history)
                    history.add(new PointMovement(item.id, item.type, item.points, item.description, item.createdAt));
                callback.onSuccess(new RewardsAccount(response.body().balance, history));
            }
            @Override public void onFailure(Call<PointsAccountResponse> current, Throwable failure) {
                requests.remove(current);
                if (!current.isCanceled()) callback.onError("Sin conexión. Vuelve a intentarlo.");
            }
        });
    }

    @Override public void benefits(RepositoryCallback<List<Benefit>> callback) {
        Call<BenefitsResponse> call = BackendClient.getUserService().getBenefits(userId);
        requests.add(call);
        call.enqueue(new Callback<BenefitsResponse>() {
            @Override public void onResponse(Call<BenefitsResponse> current, Response<BenefitsResponse> response) {
                requests.remove(current);
                if (current.isCanceled()) return;
                if (!response.isSuccessful() || response.body() == null) {
                    callback.onError(error(response, "No se pudieron consultar los beneficios."));
                    return;
                }
                List<Benefit> result = new ArrayList<>();
                if (response.body().benefits != null) for (BenefitResponse item : response.body().benefits)
                    result.add(new Benefit(item.id, item.name, item.description, item.provider,
                            item.pointsRequired, item.conditions, item.stock));
                callback.onSuccess(result);
            }
            @Override public void onFailure(Call<BenefitsResponse> current, Throwable failure) {
                requests.remove(current);
                if (!current.isCanceled()) callback.onError("Sin conexión. Vuelve a intentarlo.");
            }
        });
    }

    @Override public void redeem(int benefitId, RepositoryCallback<Integer> callback) {
        Call<RedemptionResponse> call = BackendClient.getUserService().redeemBenefit(userId, benefitId);
        requests.add(call);
        call.enqueue(new Callback<RedemptionResponse>() {
            @Override public void onResponse(Call<RedemptionResponse> current, Response<RedemptionResponse> response) {
                requests.remove(current);
                if (current.isCanceled()) return;
                if (!response.isSuccessful() || response.body() == null || response.body().redemption == null) {
                    callback.onError(error(response, "No se pudo realizar el canje."));
                    return;
                }
                callback.onSuccess(response.body().redemption.balance);
            }
            @Override public void onFailure(Call<RedemptionResponse> current, Throwable failure) {
                requests.remove(current);
                if (!current.isCanceled()) callback.onError("Sin conexión. Vuelve a intentarlo.");
            }
        });
    }

    private String error(Response<?> response, String fallback) {
        try {
            if (response.errorBody() != null)
                return new JSONObject(response.errorBody().string()).optString("message", fallback);
        } catch (Exception ignored) { }
        return fallback;
    }

    @Override public void close() {
        for (Call<?> request : new ArrayList<>(requests)) request.cancel();
        requests.clear();
    }
}
