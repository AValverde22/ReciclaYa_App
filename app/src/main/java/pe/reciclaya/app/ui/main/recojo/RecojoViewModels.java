package pe.reciclaya.app.ui.main.recojo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.*;
import pe.reciclaya.app.R;
import pe.reciclaya.app.data.local.RecojoSessionImp;
import pe.reciclaya.app.data.location.FusedLocationProvider;
import pe.reciclaya.app.data.remote.BackendClient;
import pe.reciclaya.app.data.repository.recojo.RecojoRepositoryImp;
import pe.reciclaya.app.ui.common.task.MainThreadScheduler;

/** Composition root and UI-owned lifecycle binding; no Activity enters the ViewModel. */
public final class RecojoViewModels {
    private RecojoViewModels() { }
    public static RecojoViewModel obtain(FragmentActivity activity) {
        RecojoViewModel vm=new ViewModelProvider(activity,new ViewModelProvider.Factory() {
            @NonNull @SuppressWarnings("unchecked") @Override public <T extends ViewModel> T create(@NonNull Class<T> type) {
                if (!type.isAssignableFrom(RecojoViewModel.class)) throw new IllegalArgumentException("ViewModel no soportado");
                return (T)new RecojoViewModel(new RecojoRepositoryImp(BackendClient.getSolicitudService()),
                    new FusedLocationProvider(activity.getApplicationContext()),new RecojoSessionImp(),new MainThreadScheduler());
            }
        }).get(RecojoViewModel.class);
        View decor=activity.getWindow().getDecorView();
        if (decor.getTag(R.id.recojo_lifecycle_observer)==null) {
            DefaultLifecycleObserver observer=new DefaultLifecycleObserver() {
                private void foreground(LifecycleOwner owner) {
                    FragmentActivity host=(FragmentActivity)owner;
                    vm.onForeground(ContextCompat.checkSelfPermission(host,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED);
                }
                @Override public void onStart(@NonNull LifecycleOwner owner) { foreground(owner); }
                @Override public void onResume(@NonNull LifecycleOwner owner) { foreground(owner); }
                @Override public void onStop(@NonNull LifecycleOwner owner) { vm.onBackground(); }
                @Override public void onDestroy(@NonNull LifecycleOwner owner) { owner.getLifecycle().removeObserver(this); }
            };
            decor.setTag(R.id.recojo_lifecycle_observer,observer);
            activity.getLifecycle().addObserver(observer);
        }
        return vm;
    }
}
