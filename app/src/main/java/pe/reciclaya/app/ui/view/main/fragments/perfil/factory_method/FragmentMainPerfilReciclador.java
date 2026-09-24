package pe.reciclaya.app.ui.view.main.fragments.perfil.factory_method;

import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.MiActividad;

public class FragmentMainPerfilReciclador extends FragmentMainPerfil {
    @Override
    protected void inicializarMisActividades() {
        misActividades = new MiActividad[]{
                new MiActividad(
                        "Historial de Actividades",
                        "Ver tus actividades pasadas",
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