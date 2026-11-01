package pe.reciclaya.app.ui.main.seguridad.historial;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import pe.reciclaya.app.R;

public class HistorialAccesosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_accesos);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.FLHistorialAccesos,
                            new FragmentHistorialAccesos()
                    )
                    .commit();
        }
    }
}