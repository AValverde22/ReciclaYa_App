package pe.reciclaya.app.ui.common.util;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public final class FechaHora {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss", Locale.forLanguageTag("es-PE"));
    private FechaHora() { }

    public static String formatearLima(String iso) {
        if (iso == null || iso.trim().isEmpty()) return null;
        try {
            return OffsetDateTime.parse(iso.trim()).atZoneSameInstant(ZoneId.of("America/Lima")).format(FORMATO);
        } catch (DateTimeParseException error) { return null; }
    }
}
