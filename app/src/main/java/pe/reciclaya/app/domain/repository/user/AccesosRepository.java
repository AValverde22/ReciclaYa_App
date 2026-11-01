package pe.reciclaya.app.domain.repository.user;

import java.util.List;

import pe.reciclaya.app.data.model.user.request.reportes.ReporteActividadRequest;
import pe.reciclaya.app.data.model.user.response.LoginAccessResponse;
import pe.reciclaya.app.data.model.user.response.reportes.ReporteActividadResponse;
import pe.reciclaya.app.domain.repository.RepositoryCallback;

public interface AccesosRepository {

    void obtenerHistorial(
            RepositoryCallback<List<LoginAccessResponse>> callback
    );

    void obtenerDetalle(
            int accesoId,
            RepositoryCallback<LoginAccessResponse> callback
    );

    void registrarReporte(
            int accesoId,
            String descripcion,
            RepositoryCallback<Void> callback
    );

    void obtenerMisReportes(
            RepositoryCallback<List<ReporteActividadResponse>> callback
    );

    void obtenerDetalleReporte(
            int reporteId,
            RepositoryCallback<ReporteActividadResponse> callback
    );
}