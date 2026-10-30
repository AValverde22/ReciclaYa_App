package pe.reciclaya.app.ui.common.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;

// Casillas de un código de verificación: un dígito por casilla
public final class CodigoVerificacion {
    private CodigoVerificacion() {}

    // Avanza al escribir un dígito y retrocede al borrar en una casilla vacía
    public static void configurar(EditText... casillas) {
        for(int i = 0; i < casillas.length; i++) {
            final EditText actual = casillas[i];
            final EditText siguiente = (i + 1 < casillas.length) ? casillas[i + 1] : null;
            final EditText anterior = (i > 0) ? casillas[i - 1] : null;

            actual.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.length() == 1 && siguiente != null) siguiente.requestFocus();
                }
            });

            actual.setOnKeyListener((view, keyCode, event) -> {
                if(keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN
                        && actual.getText().length() == 0 && anterior != null) {
                    anterior.setText("");
                    anterior.requestFocus();
                    return true;
                }
                return false;
            });
        }
    }

    // El código se lee como texto para no perder ceros iniciales
    public static String leer(EditText... casillas) {
        StringBuilder codigo = new StringBuilder();
        for(EditText casilla : casillas) codigo.append(casilla.getText().toString().trim());
        return codigo.toString();
    }

    public static void limpiar(EditText... casillas) {
        for(EditText casilla : casillas) casilla.setText("");
        if(casillas.length > 0) casillas[0].requestFocus();
    }
}
