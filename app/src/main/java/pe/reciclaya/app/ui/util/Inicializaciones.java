package pe.reciclaya.app.ui.util;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

import pe.reciclaya.app.R;

public class Inicializaciones {
    public static void mostrarPassword(@NonNull EditText ETPassword,
                                       @NonNull ImageView IVOjo
    ) {
        boolean[] bloquear = { true };
        IVOjo.setOnClickListener(view -> {
            bloquear[0] = !bloquear[0];

            if(bloquear[0]) {
                ETPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                IVOjo.setBackgroundResource(R.drawable.desbloquear_password);

            } else {
                ETPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                IVOjo.setBackgroundResource(R.drawable.bloquear_password);
            }
        });
    }
}
