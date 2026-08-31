package pe.reciclaya.app.general.util;

import android.content.Context;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pe.reciclaya.app.R;
import pe.reciclaya.app.general.activities.LoginActivity;
import pe.reciclaya.app.usuario.activities.MainActivity;

public class Common {
    public static void toastMakeText(@NonNull Context context,
                                     @NonNull String message
    ) {
        Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
        ).show();
    }

    public static void irAMain(@NonNull Context context,
                               @Nullable String role
    ) {
        if(role == null) {
            context.startActivity(new Intent(context, LoginActivity.class));
            return;
        }

        switch (role) {
            case "Usuario":
                context.startActivity(new Intent(context, MainActivity.class));
                break;
            case "Reciclador":
                context.startActivity(new Intent(context, pe.reciclaya.app.reciclador.activities.MainActivity.class));
                break;
            default: break;
        }
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

            //ETPassword.setSelection(ETPassword.getText().length());
        });
    }
}
