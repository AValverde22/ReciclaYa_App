package pe.reciclaya.app.ui.auth.register.rol_list;

import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.user.UserRole;

public class Reciclador implements Rol {
    @Override
    public UserRole getRol() {
        return UserRole.RECICLADOR;
    }

    @Override
    public String getTitulo() {
        return "Reciclador Local";
    }

    @Override
    public String getDescripcion() {
        return "Busco recolectar materiales y contribuir a la economía circular";
    }

    @Override
    public int getIVSeleccionado() {
        return R.drawable.reciclador_seleccionado;
    }

    @Override
    public int getIVNoSeleccionado() {
        return R.drawable.reciclador_no_seleccionado;
    }
}
