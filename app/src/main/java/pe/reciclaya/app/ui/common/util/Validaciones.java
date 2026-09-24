package pe.reciclaya.app.ui.common.util;

import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Validaciones {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[A-Z])" +
                    "(?=.*[@#$^&+=])" +
                    "(?=\\S+$)" +
                    ".{8,}" +
                    "$");

    public static String campoGenerico(String campo) {
        if(campo.isBlank()) return "El campo no puede estar vacío";
        return null;
    }

    public static String email(String email) {
        if(email.isBlank()) return "El campo no puede estar vacío";

        if(!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches())
            return "Por favor, ingrese un correo válido";

        return null;
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

    public static String confirmPassword(String password, String confirmPassword) {
        if(confirmPassword.isBlank()) return "El campo no puede estar vacío";

        if(!confirmPassword.trim().equals(password.trim()))
            return "Las contraseñas no coinciden";

        return null;
    }

    public static String role(String role) {
        if(role == null) return "Debe de seleccionar un rol";
        else return null;
    }

    public static String codigo(String cod1, String cod2, String cod3, String cod4, String cod5, String cod6){
        if(cod1.isBlank() || cod2.isBlank() || cod3.isBlank() || cod4.isBlank() || cod5.isBlank() || cod6.isBlank())
            return "Los campos no pueden estar vacíos.";

        return null;
    }
}
