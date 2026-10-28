package pe.reciclaya.app.ui.main.recojo;

public final class UserMapPresentation implements RecojoMapPresentation {
    @Override public String markerTitle() { return "Reciclador"; }
    @Override public String centerLabel() { return "Punto de recojo"; }
    @Override public String cancelTitle() { return "¿Cancelar la solicitud?"; }
    @Override public String cancelDescription() { return "La solicitud quedará cancelada definitivamente y el reciclador dejará de atenderla."; }
    @Override public boolean centersOnDevice() { return false; }
}
