package pe.reciclaya.app.ui.util;

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
import pe.reciclaya.app.antiguo.reciclador.activities.MainActivityReciclador;
import pe.reciclaya.app.ui.login.LoginActivity;
import pe.reciclaya.app.antiguo.usuario.activities.MainActivityUsuario;

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
                context.startActivity(new Intent(context, MainActivityUsuario.class));
                break;
            case "Reciclador":
                context.startActivity(new Intent(context, MainActivityReciclador.class));
                break;
            default: break;
        }
    }
}
