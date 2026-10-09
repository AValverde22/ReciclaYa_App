package pe.reciclaya.app.ui.auth.register.rol_list;

import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.user.UserRole;

public class Usuario implements Rol {
    @Override
    public UserRole getRol() {
        return UserRole.USUARIO;
    }

    @Override
    public String getTitulo() {
        return "Persona que recicla";
    }

    @Override
    public String getDescripcion() {
        return "Deseo publicar solicitudes de recolección para mis residuos";
    }

    @Override
    public int getIVSeleccionado() {
        return  R.drawable.persona_seleccionada;
    }

    @Override
    public int getIVNoSeleccionado() {
        return  R.drawable.persona_no_seleccionada;
    }
}
