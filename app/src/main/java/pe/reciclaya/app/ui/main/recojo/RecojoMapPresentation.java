package pe.reciclaya.app.ui.main.recojo;

/** Role-specific view behavior, without branching throughout the shared Fragment. */
public interface RecojoMapPresentation {
    String markerTitle();
    String cancelTitle();
    String cancelDescription();
    boolean centersOnDevice();
}
