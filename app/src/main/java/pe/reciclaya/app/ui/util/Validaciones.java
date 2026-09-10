package pe.reciclaya.app.ui.util;

import android.util.Patterns;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

public class Validaciones {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$^&+=])" +
                    "(?=\\S+$)" +
                    ".{8,}" +
                    "$");

    public static boolean campoGenerico(@NonNull EditText editText) {
        String campo = editText.getText().toString().trim();

        if(campo.isBlank()) {
            editText.setError("El campo no puede estar vacío");
            return false;
        }

        editText.setError(null);
        return true;
    }

    public static String campoGenerico(String campo) {
        if(campo.isBlank()) return "El campo no puede estar vacío";
        return null;
    }

    public static boolean email(@NonNull EditText ETEmail) {
        String email = ETEmail.getText().toString().trim();

        if(email.isBlank()) {
            ETEmail.setError("El campo no puede estar vacío");
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ETEmail.setError("Por favor, ingrese un correo válido");
            return false;
        }

        ETEmail.setError(null);
        return true;

    }

    public static String email(String email) {
        if(email.isBlank()) return "El campo no puede estar vacío";

        if(!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches())
            return "Por favor, ingrese un correo válido";

        return null;
    }


    public static boolean password(@NonNull EditText ETPassword) {
        String password = ETPassword.getText().toString().trim();

        if(password.isBlank()) {
            ETPassword.setError("El campo no puede estar vacío");
            return false;
        }

        if(!PASSWORD_PATTERN.matcher(password).matches()) {
            ETPassword.setError(
                    "La contraseña debe de contenter al menos:\n" +
                            "   • Un caracter especial\n" +
                            "   • Una mayúscula\n" +
                            "   • 8 caractéres"
            );
            return false;
        }

        ETPassword.setError(null);
        return true;
    }

    public static String password(String password) {
        if(password.isBlank()) return "El campo no puede estar vacío";

        if(!PASSWORD_PATTERN.matcher(password).matches()) {
            return "La contraseña debe de contenter al menos:\n" +
                            "   • Un caracter especial\n" +
                            "   • Una mayúscula\n" +
                            "   • 8 caractéres";
        }

        return null;
    }

    public static boolean confirmPassword(@NonNull EditText ETPassword,
                                          @NonNull EditText ETConfirmPassword
    ) {
        String confirmPassword = ETConfirmPassword.getText().toString().trim();

        if(confirmPassword.isBlank()) {
            ETConfirmPassword.setError("El campo no puede estar vacío");
            return false;
        }

        String password = ETPassword.getText().toString().trim();
        if(!confirmPassword.equals(password)) {
            ETConfirmPassword.setError("Las contraseñas deben de coincidir");
            return false;
        }

        ETConfirmPassword.setError(null);
        return true;
    }

    public static String confirmPassword(String password, String confirmPassword) {
        if(confirmPassword.isBlank()) return "El campo no puede estar vacío";

        if(!confirmPassword.trim().equals(password.trim()))
            return "Las contraseñas no coinciden";

        return null;
    }
}
