package pe.reciclaya.app.antiguo.reciclador.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pe.reciclaya.app.R;

public class MainActivityReciclador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView TVProximamente = findViewById(R.id.TVProximamenteMain);
        TVProximamente.setText("PROXIMAMENTE: PANTALLA DE RECICLADOR.");
    }
}