package pe.reciclaya.app.general.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

import pe.reciclaya.app.R;
import pe.reciclaya.app.general.fragments.register.FRDatos;
import pe.reciclaya.app.general.util.Singleton;

public class RegisterActivity extends AppCompatActivity {

    private LinearLayout LLLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        LLLoading = findViewById(R.id.LLLoadingRegister);
        Singleton.getMostrarLLLoadingRegister().observe(this, mostrar -> {
            if(mostrar) LLLoading.setVisibility(View.VISIBLE);
            else LLLoading.setVisibility(View.GONE);
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FLRegister, new FRDatos())
                .commit();
    }

    public void cambiarFragment(@NonNull Fragment fragment, @Nullable Bundle bundle){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.FLRegister, fragment.getClass(), bundle)
                .addToBackStack(null)
                .commit();
    }

    public void eliminarFragment() { getSupportFragmentManager().popBackStack(); }
}