package pe.reciclaya.app.data.repository.user;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import pe.reciclaya.app.data.model.user.request.reportes.ReporteActividadRequest;
import pe.reciclaya.app.data.model.user.response.LoginAccessResponse;
import pe.reciclaya.app.data.model.user.response.reportes.ReporteActividadResponse;
import pe.reciclaya.app.data.model.user.response.reportes.ReporteActividadResponseWrapper;
import pe.reciclaya.app.data.model.user.response.reportes.ReporteActividadDetalleWrapper;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.remote.UserService;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.user.AccesosRepository;
import pe.reciclaya.app.domain.model.user.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccesosRepositoryImp implements AccesosRepository {

    private static final String TAG = "AccesosRepository";

    private final UserService apiService;
    private final int userId;

    public AccesosRepositoryImp(Context context) {
        apiService = BackendClient.getUserService();
        userId = User.getInstance().getID();
    }

    private boolean usuarioValido(RepositoryCallback<?> callback) {
        if (userId <= 0) {
            callback.onError(
                    "No se pudo identificar al usuario. Inicie sesión nuevamente."
            );
            return false;
        }

        return true;
    }

    @Override
    public void obtenerHistorial(
            RepositoryCallback<List<LoginAccessResponse>> callback
    ) {
        if (!usuarioValido(callback)) return;

        apiService.obtenerHistorialAccesos(userId)
                .enqueue(new Callback<List<LoginAccessResponse>>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<List<LoginAccessResponse>> call,
                            @NonNull Response<List<LoginAccessResponse>> response
                    ) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError(
                                    "No se pudo obtener el historial de accesos."
                            );
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<List<LoginAccessResponse>> call,
                            @NonNull Throwable t
                    ) {
                        Log.e(TAG, "Error consultando historial", t);
                        callback.onError(
                                "Error de conexión al consultar los accesos."
                        );
                    }
                });
    }

    @Override
    public void obtenerDetalle(
            int accesoId,
            RepositoryCallback<LoginAccessResponse> callback
    ) {
        if (!usuarioValido(callback)) return;

        apiService.obtenerDetalleAcceso(userId, accesoId)
                .enqueue(new Callback<LoginAccessResponse>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<LoginAccessResponse> call,
                            @NonNull Response<LoginAccessResponse> response
                    ) {
                        if (response.isSuccessful() && response.body() != null) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onError(
                                    "No se pudo obtener el detalle del acceso."
                            );
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<LoginAccessResponse> call,
                            @NonNull Throwable t
                    ) {
                        Log.e(TAG, "Error consultando detalle", t);
                        callback.onError(
                                "Error de conexión al consultar el acceso."
                        );
                    }
                });
    }

    @Override
    public void registrarReporte(
            int accesoId,
            String descripcion,
            RepositoryCallback<Void> callback
    ) {
        if (!usuarioValido(callback)) return;

        ReporteActividadRequest request =
                new ReporteActividadRequest(accesoId, descripcion);

        apiService.registrarReporte(userId, accesoId, request)
                .enqueue(new Callback<ReporteActividadDetalleWrapper>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<ReporteActividadDetalleWrapper> call,
                            @NonNull Response<ReporteActividadDetalleWrapper> response
                    ) {
                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()) {

                            callback.onSuccess(null);

                        } else {
                            String mensaje = response.body() != null
                                    ? response.body().getMessage()
                                    : null;

                            if (mensaje == null || mensaje.isEmpty()) {
                                mensaje = response.code() == 409
                                        ? "Este acceso ya fue reportado."
                                        : "No se pudo registrar el reporte.";
                            }

                            callback.onError(mensaje);
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<ReporteActividadDetalleWrapper> call,
                            @NonNull Throwable t
                    ) {
                        Log.e(TAG, "Error registrando reporte", t);
                        callback.onError(
                                "Error de conexión al registrar el reporte."
                        );
                    }
                });
    }

    @Override
    public void obtenerMisReportes(
            RepositoryCallback<List<ReporteActividadResponse>> callback
    ) {
        if (!usuarioValido(callback)) return;

        apiService.obtenerMisReportes(userId)
                .enqueue(new Callback<ReporteActividadResponseWrapper>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<ReporteActividadResponseWrapper> call,
                            @NonNull Response<ReporteActividadResponseWrapper> response
                    ) {
                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()
                                && response.body().getData() != null) {

                            callback.onSuccess(response.body().getData());

                        } else {
                            callback.onError(
                                    "No se pudieron obtener tus reportes."
                            );
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<ReporteActividadResponseWrapper> call,
                            @NonNull Throwable t
                    ) {
                        Log.e(TAG, "Error consultando reportes", t);
                        callback.onError(
                                "Error de conexión al consultar tus reportes."
                        );
                    }
                });
    }

    @Override
    public void obtenerDetalleReporte(
            int reporteId,
            RepositoryCallback<ReporteActividadResponse> callback
    ) {
        if (!usuarioValido(callback)) return;

        apiService.obtenerDetalleReporte(userId, reporteId)
                .enqueue(new Callback<ReporteActividadDetalleWrapper>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<ReporteActividadDetalleWrapper> call,
                            @NonNull Response<ReporteActividadDetalleWrapper> response
                    ) {
                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isSuccess()
                                && response.body().getData() != null) {

                            callback.onSuccess(response.body().getData());

                        } else {
                            callback.onError(
                                    "No se pudo obtener el detalle del reporte."
                            );
                        }
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<ReporteActividadDetalleWrapper> call,
                            @NonNull Throwable t
                    ) {
                        Log.e(TAG, "Error consultando detalle del reporte", t);
                        callback.onError(
                                "Error de conexión al consultar el reporte."
                        );
                    }
                });
    }
}