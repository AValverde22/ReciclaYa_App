package pe.reciclaya.app.ui.main.seguridad.historial;

import java.util.ArrayList;
import java.util.List;

import pe.reciclaya.app.data.model.user.response.LoginAccessResponse;

public class AccesoItemMapper {

    private AccesoItemMapper() {}

    public static AccesoItem toUI(LoginAccessResponse acceso) {
        return new AccesoItem(
                acceso.getId(),
                acceso.getFechaLogin(),
                acceso.getCiudad(),
                acceso.getPais(),
                acceso.getEstado(),
                acceso.getTipoActividad()
        );
    }

    public static List<AccesoItem> allToUI(
            List<LoginAccessResponse> accesos
    ) {
        List<AccesoItem> lista = new ArrayList<>();

        if (accesos == null) {
            return lista;
        }

        for (LoginAccessResponse acceso : accesos) {
            if (acceso != null) {
                lista.add(toUI(acceso));
            }
        }

        return lista;
    }
}