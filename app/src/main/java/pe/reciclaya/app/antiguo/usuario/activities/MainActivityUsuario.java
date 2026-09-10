package pe.reciclaya.app.antiguo.usuario.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pe.reciclaya.app.R;

public class MainActivityUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView TVProximamente = findViewById(R.id.TVProximamenteMain);
        TVProximamente.setText("PROXIMAMENTE: PANTALLA DE USUARIO.");

    }
}