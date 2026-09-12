package pe.reciclaya.app.ui.util;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Validaciones {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
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
}
