package pe.reciclaya.app.ui.main.recojo;

public final class RecyclerMapPresentation implements RecojoMapPresentation {
    @Override public String markerTitle() { return "Tú"; }
    @Override public String centerLabel() { return "Mi ubicación"; }
    @Override public String cancelTitle() { return "¿Cancelar este recojo?"; }
    @Override public String cancelDescription() { return "Dejarás de atender este recojo y la solicitud volverá a estar disponible para otro reciclador."; }
    @Override public boolean centersOnDevice() { return true; }
}
