package pe.reciclaya.app.general.util;

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
        editText.clearFocus();

        String campo = editText.getText().toString();
        return !campo.isBlank();
    }

    public static boolean email(@NonNull EditText ETEmail) {
        ETEmail.clearFocus();

        String email = ETEmail.getText().toString();
        if(!email.isBlank()) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

        return false;
    }

    public static boolean[] password(@NonNull EditText ETPassword,
                                   @NonNull EditText ETConfirmPassword
    ) {
        // correctos[0]: para ETPassword
        // correctos[1]: para ETConfirmPassword
        boolean[] correctos = { false, false };

        ETPassword.clearFocus();
        ETConfirmPassword.clearFocus();

        String password = ETPassword.getText().toString();
        if(!password.isBlank()) {
            correctos[0] = PASSWORD_PATTERN.matcher(password).matches();

            String confirmPassword = ETConfirmPassword.getText().toString();
            if(!confirmPassword.isBlank()) correctos[1] = password.equals(confirmPassword);
            else correctos[1] = false;
        }
        else correctos[0] = false;
        return correctos;
    }

    public static boolean confirmPassword(@NonNull EditText ETPassword,
                                          @NonNull EditText ETConfirmPassword
    ) {
        ETPassword.clearFocus();
        ETConfirmPassword.clearFocus();

        String confirmPassword = ETConfirmPassword.getText().toString();
        if(!confirmPassword.isBlank()) {
            String password = ETPassword.getText().toString();
            return confirmPassword.equals(password);
        } return false;

    }
}
