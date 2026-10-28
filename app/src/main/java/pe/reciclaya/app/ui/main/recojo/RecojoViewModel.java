package pe.reciclaya.app.ui.main.recojo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.function.Function;
import pe.reciclaya.app.domain.model.recojo.*;
import pe.reciclaya.app.domain.repository.RepositoryCallback;
import pe.reciclaya.app.domain.repository.recojo.*;
import pe.reciclaya.app.ui.common.event.Event;
import pe.reciclaya.app.ui.common.task.TaskScheduler;

/** Screen orchestration only; Android GPS, HTTP and Activity lifecycle are injected. */
public final class RecojoViewModel extends ViewModel {
    private final RecojoRepository repository;
    private final LocationProvider locations;
    private final RecojoSession session;
    private final TaskScheduler scheduler;
    private final MutableLiveData<RecojoUiState> state=new MutableLiveData<RecojoUiState>() {
        @Override protected void onActive() { if (foreground) refresh(); }
        @Override protected void onInactive() { scheduler.remove(poll); }
    };
    private final MutableLiveData<Event<String>> messages=new MutableLiveData<>();
    private final Runnable poll=this::refresh;
    private Recojo pickup;
    private LocationFix fix;
    private boolean known, busy, foreground, permission, fetching, sharing, routing, cleared;
    private long readVersion, shareVersion, routeVersion, lastRoute=Long.MIN_VALUE;
    private String route, routeNote, error;
    private RequestHandle read=RequestHandle.NONE, share=RequestHandle.NONE, routeRequest=RequestHandle.NONE;

    public RecojoViewModel(RecojoRepository repository, LocationProvider locations, RecojoSession session, TaskScheduler scheduler) {
        this.repository=repository; this.locations=locations; this.session=session; this.scheduler=scheduler;
        emit();
    }
    public LiveData<RecojoUiState> getState() { return state; }
    public LiveData<Event<String>> getMessages() { return messages; }
    public boolean blocked() { return !known || pickup!=null; }
    private void emit() {
        if (!cleared) state.setValue(new RecojoUiState(known,busy,session.isRecycler(),permission,session.userId(),pickup,fix,scheduler.nowMillis(),route,routeNote,error));
    }
    public void onForeground(boolean precisePermission) {
        if (cleared) return;
        foreground=true; onPermissionChanged(precisePermission); refresh();
    }
    public void onPermissionChanged(boolean precisePermission) {
        permission=precisePermission;
        if (!permission) { fix=null; locations.stop(); cancelSharing(); }
        else requestLocation();
        emit();
    }
    public void onBackground() {
        foreground=false; locations.stop(); cancelRead(); cancelSharing(); cancelRoute(); scheduler.remove(poll);
    }
    public void requestLocation() {
        if (!foreground || !permission || !session.isRecycler() || cleared) return;
        locations.start(new LocationProvider.Listener() {
            @Override public void onLocation(LocationFix location) {
                if (!foreground || !permission || cleared) return;
                if (!location.isRecent(scheduler.nowMillis(),30000)) { error="Esperando una ubicación GPS reciente y precisa…"; emit(); return; }
                fix=location; error=null; emit(); publishLocation(location);
            }
            @Override public void onError(String message) { if (!cleared && foreground) { error=message; emit(); } }
        });
    }
    private void publishLocation(LocationFix location) {
        if (!known || pickup==null || sharing || busy) return;
        sharing=true; final long token=++shareVersion;
        RequestHandle handle=repository.shareLocation(pickup.id,session.userId(),location,new RepositoryCallback<Void>() {
            @Override public void onSuccess(Void ignored) {
                if (cleared || token!=shareVersion) return;
                sharing=false; share=RequestHandle.NONE; refresh();
            }
            @Override public void onError(String message) {
                if (cleared || token!=shareVersion) return;
                sharing=false; share=RequestHandle.NONE; error=message; emit(); refresh();
            }
        });
        if (sharing && token==shareVersion) share=handle;
    }
    public void refresh() {
        scheduler.remove(poll);
        if (cleared || !foreground || fetching || busy) return;
        emit(); // Re-evaluate GPS freshness even while the next HTTP response is pending.
        fetching=true; final long token=++readVersion;
        RequestHandle handle=repository.getActive(session.userId(),new RepositoryCallback<Recojo>() {
            @Override public void onSuccess(Recojo value) {
                if (cleared || token!=readVersion) return;
                fetching=false; read=RequestHandle.NONE;
                int previousId=pickup==null?-1:pickup.id;
                int nextId=value==null?-1:value.id;
                if (previousId!=nextId) { cancelSharing(); cancelRoute(); route=null; routeNote=null; lastRoute=Long.MIN_VALUE; }
                pickup=value; known=true; error=null; emit(); requestRoute(); schedule();
            }
            @Override public void onError(String message) {
                if (cleared || token!=readVersion) return;
                fetching=false; read=RequestHandle.NONE; known=false; error=message; emit(); schedule();
            }
        });
        if (fetching && token==readVersion) read=handle;
    }
    private void schedule() { if (foreground && state.hasActiveObservers() && !cleared) scheduler.post(poll,5000); }
    /** Discard stale reads after acceptance from the existing Reciclar flow. */
    public void invalidate() { cancelRead(); known=false; emit(); refresh(); }
    private void cancelRead() { readVersion++; read.cancel(); read=RequestHandle.NONE; fetching=false; }
    private void cancelSharing() { shareVersion++; share.cancel(); share=RequestHandle.NONE; sharing=false; }
    private void cancelRoute() { routeVersion++; routeRequest.cancel(); routeRequest=RequestHandle.NONE; routing=false; }

    public void confirm() {
        RecojoUiState current=state.getValue();
        if (current==null || !current.canFinish) return;
        final int id=pickup.id;
        action(callback -> repository.confirm(id,session.userId(),callback),() -> pickup=pickup.withConfirmation(session.userId()));
    }
    public void cancelPickup() {
        RecojoUiState current=state.getValue();
        if (current==null || !current.canCancel) return;
        final int id=pickup.id;
        action(callback -> repository.cancel(id,session.userId(),callback),() -> {
            pickup=null; known=true; route=null; routeNote=null; cancelRoute(); cancelSharing();
            messages.setValue(new Event<>(session.isRecycler()?"La solicitud vuelve a estar disponible.":"Solicitud cancelada."));
        });
    }
    public void rate(int score, String comment) {
        if (score<1 || score>5) { messages.setValue(new Event<>("Selecciona de 1 a 5 estrellas.")); return; }
        if (comment!=null && comment.length()>1000) { messages.setValue(new Event<>("La reseña admite hasta 1000 caracteres.")); return; }
        RecojoUiState current=state.getValue();
        if (current==null || !current.canRate) return;
        final int id=pickup.id;
        action(callback -> repository.rate(id,session.userId(),score,comment==null?"":comment.trim(),callback),() -> pickup=pickup.withRating());
    }
    private void action(Function<RepositoryCallback<Void>,RequestHandle> operation, Runnable success) {
        if (busy || cleared) return;
        cancelRead(); scheduler.remove(poll); busy=true; error=null; emit();
        operation.apply(new RepositoryCallback<Void>() {
            @Override public void onSuccess(Void ignored) {
                if (cleared) return;
                busy=false; success.run(); emit(); refresh();
            }
            @Override public void onError(String message) {
                if (cleared) return;
                busy=false; error=message; messages.setValue(new Event<>(message)); emit(); refresh();
            }
        });
    }
    private void requestRoute() {
        if (!foreground || !known || pickup==null || pickup.recyclerLocation==null || routing) return;
        long now=scheduler.nowMillis();
        if (lastRoute!=Long.MIN_VALUE && now-lastRoute<30000) return;
        lastRoute=now; routing=true; final int id=pickup.id; final long token=++routeVersion;
        RequestHandle handle=repository.getRoute(id,session.userId(),new RepositoryCallback<RutaRecojo>() {
            @Override public void onSuccess(RutaRecojo value) {
                if (!current()) return;
                routing=false; routeRequest=RequestHandle.NONE; route=value.encodedPolyline;
                routeNote=route==null?(session.isRecycler()?"Ruta vial no disponible. Puede abrir la navegación externa.":"Ruta vial no disponible. Se muestran las ubicaciones."):null; emit();
            }
            @Override public void onError(String message) {
                if (!current()) return;
                routing=false; routeRequest=RequestHandle.NONE; routeNote="No se pudo actualizar la ruta vial."; emit();
            }
            private boolean current() { return !cleared && token==routeVersion && pickup!=null && pickup.id==id; }
        });
        if (routing && token==routeVersion) routeRequest=handle;
    }
    @Override protected void onCleared() {
        cleared=true; locations.stop(); scheduler.remove(poll); cancelRead(); cancelSharing(); cancelRoute(); repository.close();
    }
}
