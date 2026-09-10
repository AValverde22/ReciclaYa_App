package pe.reciclaya.app.antiguo.general.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pe.reciclaya.app.R;

public class TyCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tyc_activity);

        TextView TVTitulo = findViewById(R.id.TVTituloTyC);
        TextView TVTexto = findViewById(R.id.TVTextoTyC);

        Intent intent = getIntent();
        int tipo = intent.getIntExtra("Tipo", 0);

        if(tipo == 0){
            TVTitulo.setText(getResources().getText(R.string.TerminosDeServicio));
            TVTexto.setText(getResources().getText(R.string.MensajeTerminosDeServicio));
        } else {
            TVTitulo.setText(getResources().getText(R.string.PoliticaDePrivacidad));
            TVTexto.setText(getResources().getText(R.string.MensajePoliticaDePrivacidad));
        }
    }
}