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
    public static void campoGenerico(@NonNull EditText editText) {
        editText.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) Validaciones.campoGenerico(editText);
        });
    }

    public static void email(@NonNull EditText ETEmail) {
        ETEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) Validaciones.email(ETEmail);
        });
    }

    public static void password(@NonNull EditText ETPassword) {
        ETPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) Validaciones.password(ETPassword);
        });
    }

    public static void confirmPassword(@NonNull EditText ETPassword,
                                       @NonNull EditText ETConfirmPassword
    ) {
        ETConfirmPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) Validaciones.confirmPassword(ETPassword, ETConfirmPassword);
        });
    }

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
