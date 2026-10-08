package pe.reciclaya.app.ui.auth.register.rol_list;

import pe.reciclaya.app.domain.model.user.UserRole;

public interface Rol {
    UserRole getRol();
    String getTitulo();
    String getDescripcion();
    int getIVSeleccionado();
    int getIVNoSeleccionado();
}
