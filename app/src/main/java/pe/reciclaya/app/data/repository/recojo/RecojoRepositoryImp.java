package pe.reciclaya.app.data.repository.recojo;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import pe.reciclaya.app.data.mapper.RecojoMapper;
import pe.reciclaya.app.data.remote.SolicitudService;
import pe.reciclaya.app.domain.model.recojo.*;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.recojo.*;
import retrofit2.*;

/** Owns HTTP requests, JSON payloads, transport errors and DTO mapping. */
public final class RecojoRepositoryImp implements RecojoRepository {
    private final SolicitudService service;
    private final Set<Call<?>> requests = new HashSet<>();
    public RecojoRepositoryImp(SolicitudService service) { this.service=service; }
    private <T,R> RequestHandle execute(Call<T> call, Function<T,R> map, RepositoryCallback<R> callback) {
        requests.add(call);
        call.enqueue(new Callback<T>() {
            @Override public void onResponse(Call<T> c, Response<T> response) {
                requests.remove(c);
                if (c.isCanceled()) return;
                if (!response.isSuccessful()) { callback.onError(errorMessage(response)); return; }
                if (response.body() == null) { callback.onError("El servidor devolvió una respuesta vacía."); return; }
                R value;
                try { value=map.apply(response.body()); }
                catch (RuntimeException error) { callback.onError("No se pudo interpretar la respuesta del servidor."); return; }
                callback.onSuccess(value);
            }
            @Override public void onFailure(Call<T> c, Throwable failure) {
                requests.remove(c);
                if (!c.isCanceled()) callback.onError("Sin conexión. Vuelve a intentarlo.");
            }
        });
        return call::cancel;
    }
    private String errorMessage(Response<?> response) {
        String fallback = response.code() == 404 ? "El servicio no está disponible. Comprueba que el backend esté actualizado." : "No se pudo completar la operación (HTTP " + response.code() + ").";
        try {
            if (response.errorBody() != null) return new org.json.JSONObject(response.errorBody().string()).optString("message",fallback);
        } catch (Exception ignored) { }
        return fallback;
    }
    @Override public RequestHandle getActive(int userId, RepositoryCallback<Recojo> cb) {
        return execute(service.getActiva(userId),RecojoMapper::toDomain,cb);
    }
    @Override public RequestHandle shareLocation(int id, int userId, LocationFix fix, RepositoryCallback<Void> cb) {
        JsonObject body=new JsonObject(); body.addProperty("latitude",fix.point.latitude); body.addProperty("longitude",fix.point.longitude); body.addProperty("accuracy",fix.accuracyMeters);
        return execute(service.ubicacion(id,userId,body),ignored -> null,cb);
    }
    @Override public RequestHandle confirm(int id, int userId, RepositoryCallback<Void> cb) { return execute(service.confirmar(id,userId),ignored -> null,cb); }
    @Override public RequestHandle cancel(int id, int userId, RepositoryCallback<Void> cb) { return execute(service.cancelarRecojo(id,userId),ignored -> null,cb); }
    @Override public RequestHandle rate(int id, int userId, int score, String comment, RepositoryCallback<Void> cb) {
        JsonObject body=new JsonObject(); body.addProperty("evaluador_id",userId); body.addProperty("puntuacion",score); body.addProperty("comentario",comment);
        return execute(service.calificar(id,body),ignored -> null,cb);
    }
    @Override public RequestHandle getRoute(int id, int userId, RepositoryCallback<RutaRecojo> cb) {
        return execute(service.ruta(id,userId),dto -> new RutaRecojo(dto.polyline,dto.message),cb);
    }
    @Override public void close() { for (Call<?> call:new ArrayList<>(requests)) call.cancel(); requests.clear(); }
}
