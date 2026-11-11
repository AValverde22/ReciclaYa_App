package pe.reciclaya.app.data.repository.user;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import pe.reciclaya.app.data.model.user.response.ReputationResponse;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.domain.model.reputation.Reputation;
import pe.reciclaya.app.domain.model.reputation.Review;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.ReputationRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class ReputationRepositoryImp implements ReputationRepository {
    private Call<ReputationResponse> request;
    @Override public void get(int userId, RepositoryCallback<Reputation> callback) {
        close();
        Call<ReputationResponse> call = BackendClient.getUserService().getReputation(userId);
        request = call;
        call.enqueue(new Callback<ReputationResponse>() {
            @Override public void onResponse(Call<ReputationResponse> current, Response<ReputationResponse> response) {
                if (current.isCanceled() || request != current) return;
                request = null;
                if (!response.isSuccessful() || response.body() == null || response.body().user == null) {
                    callback.onError(error(response));
                    return;
                }
                ReputationResponse body = response.body();
                List<Review> reviews = new ArrayList<>();
                if (body.reviews != null) for (ReputationResponse.Review value : body.reviews) {
                    ReputationResponse.Reviewer reviewer = value.reviewer;
                    reviews.add(new Review(value.id, value.requestId, value.score, value.comment, value.createdAt,
                            reviewer == null ? -1 : reviewer.id,
                            reviewer == null ? "Usuario de ReciclaYa" : reviewer.fullName,
                            reviewer == null ? null : reviewer.profilePhotoUrl));
                }
                callback.onSuccess(new Reputation(body.user.id, body.user.fullName, body.user.profilePhotoUrl,
                        body.averageRating, body.totalRatings, reviews));
            }
            @Override public void onFailure(Call<ReputationResponse> current, Throwable failure) {
                if (current.isCanceled() || request != current) return;
                request = null;
                callback.onError("Sin conexión. Vuelve a intentarlo.");
            }
        });
    }

    private String error(Response<?> response) {
        try {
            if (response.errorBody() != null)
                return new JSONObject(response.errorBody().string()).optString("message", "No se pudo consultar la reputación.");
        } catch (Exception ignored) { }
        return "No se pudo consultar la reputación.";
    }

    @Override public void close() {
        if (request != null) request.cancel();
        request = null;
    }
}
