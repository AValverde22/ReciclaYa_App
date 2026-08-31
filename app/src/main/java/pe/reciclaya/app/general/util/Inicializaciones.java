package pe.reciclaya.app.general.util;

import android.util.Patterns;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

public class Inicializaciones {
    public interface IFragmentRegister { void IVRegresarUnoAtras(); }

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$^&+=])" +
                    "(?=\\S+$)" +
                    ".{8,}" +
                    "$");

    public static void campoGenerico(@NonNull EditText editText) {
        editText.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) {
                String campo = editText.getText().toString();
                if(campo.isBlank()) editText.setError("El campo no puede estar vacío");
            }
        });
    }

    public static void email(@NonNull EditText ETEmail) {
        ETEmail.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) {
                String email = ETEmail.getText().toString();

                if(!email.isBlank()) {
                    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                        ETEmail.setError("Por favor, ingrese un correo válido");
                } else ETEmail.setError("El campo no puede estar vacío");
            }
        });
    }

    public static void password(@NonNull EditText ETPassword,
                                @NonNull EditText ETConfirmPassword
    ) {
        ETPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) {
                String password = ETPassword.getText().toString();

                if(!password.isBlank()) {
                    if(!PASSWORD_PATTERN.matcher(password).matches())
                        ETPassword.setError(
                                "La contraseña debe de contenter al menos:\n" +
                                        "   • Un caracter especial\n" +
                                        "   • Una mayúscula\n" +
                                        "   • 8 caractéres"
                        );

                    String confirmPassword = ETConfirmPassword.getText().toString();
                    if(!confirmPassword.isBlank()) {
                        if(!password.equals(confirmPassword)) {
                            ETConfirmPassword.setError("Las contraseñas deben de coincidir");
                        }
                    }
                } else ETPassword.setError("El campo no puede estar vacío");
            }
        });
    }

    public static void confirmPassword(@NonNull EditText ETPassword,
                                       @NonNull EditText ETConfirmPassword
    ) {
        ETConfirmPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus) {
                String confirmPassword = ETConfirmPassword.getText().toString();

                if(!confirmPassword.isBlank()) {
                    String password = ETPassword.getText().toString();
                    if(!confirmPassword.equals(password))
                        ETConfirmPassword.setError("Las contraseñas deben de coincidir");
                } else ETConfirmPassword.setError("El campo no puede estar vacío");
            }
        });
    }
}
