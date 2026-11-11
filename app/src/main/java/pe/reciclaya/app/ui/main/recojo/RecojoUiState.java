package pe.reciclaya.app.ui.main.recojo;

import pe.reciclaya.app.domain.model.recojo.*;
import pe.reciclaya.app.ui.common.util.FechaHora;

/** Read-only screen state. Button rules have one owner, outside the Fragment. */
public final class RecojoUiState {
    public final Recojo pickup;
    public final LocationFix location;
    public final GeoPoint centerPoint;
    public final String details, status, routePolyline;
    public final long nowMillis;
    public final boolean loading, blocked, busy, showPermission, showFinish, canFinish,
        showRating, canRate, showCancel, canCancel, showNavigate, showCenter;

    public RecojoUiState(boolean known, boolean busy, boolean recycler, boolean permission, int userId,
                          Recojo pickup, LocationFix fix, long now, String route, String routeNote, String error) {
        this.pickup=known?pickup:null;
        this.nowMillis=now;
        this.location=permission && fix!=null && fix.isRecent(now,120000)?fix:null;
        this.routePolyline=route;
        boolean active=known && pickup!=null;
        boolean finalized=active && pickup.isFinalized();
        this.loading=!known; this.busy=busy; this.blocked=!known || (active && !finalized);
        this.showPermission=recycler && !permission && active && !finalized;
        boolean mine=active && pickup.confirmedBy(userId);
        boolean both=active && pickup.confirmedByBoth();
        this.showFinish=active && !finalized && !mine;
        this.canFinish=showFinish && !busy && pickup.arrived;
        this.showRating=active && finalized && !pickup.hasRated;
        this.canRate=showRating && !busy;
        this.showCancel=active && !finalized && !both && !pickup.hasRated;
        this.canCancel=showCancel && !busy;
        this.showNavigate=active && !finalized && recycler;
        this.showCenter=recycler || active;
        this.centerPoint=recycler ? (location==null?null:location.point) : (active?pickup.destination:null);
        this.details=!known?"Consultando reciclaje activo…":!active?"No tienes un reciclaje activo.":
            "Solicitud #"+pickup.id+" · "+pickup.material+" · "+pickup.size+"\n"+pickup.address+"\n"+pickup.day+" · "+pickup.time+
            (pickup.counterpartName==null?"":"\n"+(recycler?"Usuario: ":"Reciclador: ")+pickup.counterpartName);
        String message;
        if (!known) message="Comprobando el estado del recojo…";
        else if (!active) message="Cuando se acepte una solicitud, podrás seguir el recojo aquí.";
        else if (finalized) {
            String points=pickup.pointsAwarded>0?" Recibiste "+pickup.pointsAwarded+" puntos"+(pickup.pointsBalance==null?".":" y tu saldo es "+pickup.pointsBalance+"."):"";
            message=pickup.hasRated?"Calificación registrada correctamente.":"Recojo finalizado."+points+" Puedes calificar a la otra persona y dejar una reseña opcional.";
        }
        else if (both) message="Procesando la finalización del recojo…";
        else if (mine) message="Confirmaste el fin. Podrás calificar cuando la otra persona también confirme.";
        else if (pickup.arrived) message="El reciclador llegó. Pueden confirmar el fin del reciclaje.";
        else if (pickup.recyclerLocation==null) message="Esperando ubicación del reciclador. Mantenga su aplicación abierta y el GPS activo.";
        else {
            String date=FechaHora.formatearLima(pickup.locationUpdatedAt);
            message="Reciclador en camino.\n"+(date==null?"Última actualización no disponible.":"Última actualización: "+date);
        }
        if (active && routeNote!=null && !both) message+="\n"+routeNote;
        this.status=error==null?message:message+"\n"+error;
    }
}
