package pe.reciclaya.app.ui.view.main.factory_method.fragment_perfil;

import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.MiActividad;

public class FragmentMainPerfilUsuario extends FragmentMainPerfil {
    @Override
    protected void inicializarMisActividades() {
        misActividades = new MiActividad[]{
                new MiActividad(
                        "Historial de Solicitudes",
                        "Ver tus solicitudes pasadas",
                        R.drawable.recientes
                ),
                new MiActividad(
                        "Gestionar Puntos",
                        "Gestionar recompensas",
                        R.drawable.billetera
                ),
                new MiActividad(
                        "Información Personal",
                        "Actualizar datos y fotos",
                        R.drawable.perfil
                )
        };
    }
}