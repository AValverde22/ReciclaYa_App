package pe.reciclaya.app.ui.main.recojo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.SupportMapFragment;
import pe.reciclaya.app.R;
import pe.reciclaya.app.ui.common.event.EventObserver;

/** View binding, permissions and navigation. All pickup actions go through the ViewModel. */
public abstract class FragmentRecojoMapa extends Fragment {
    protected abstract RecojoMapPresentation presentation();
    private RecojoViewModel vm;
    private RecojoMapRenderer map;
    private RecojoDialogs dialogs;
    private RecojoMapPresentation role;
    private TextView detail,status;
    private Button finish,rate,navigate,permission,cancel,center;
    private final ActivityResultLauncher<String[]> permissions=registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),result -> {
        if (getView()!=null) vm.onPermissionChanged(hasFineLocation());
    });
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recojo_activo,container,false);
    }
    @Override public void onViewCreated(@NonNull View view,Bundle savedInstanceState) {
        role=presentation(); vm=RecojoViewModels.obtain(requireActivity()); dialogs=new RecojoDialogs(requireContext());
        detail=view.findViewById(R.id.detalleRecojo); status=view.findViewById(R.id.estadoRecojo);
        finish=view.findViewById(R.id.terminarRecojo); rate=view.findViewById(R.id.calificarRecojo);
        navigate=view.findViewById(R.id.navegarRecojo); permission=view.findViewById(R.id.ubicacionRecojo);
        cancel=view.findViewById(R.id.cancelarRecojo); center=view.findViewById(R.id.centrarRecojo);
        center.setText(role.centerLabel()); center.setOnClickListener(v -> centerMap());
        permission.setOnClickListener(v -> permissions.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}));
        finish.setOnClickListener(v -> dialogs.confirm(vm::confirm));
        cancel.setOnClickListener(v -> dialogs.cancel(role,vm::cancelPickup));
        rate.setOnClickListener(v -> dialogs.rate(vm::rate));
        navigate.setOnClickListener(v -> navigate());
        SupportMapFragment fragment=(SupportMapFragment)getChildFragmentManager().findFragmentByTag("recojoMap");
        if (fragment==null) {
            fragment=SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.mapaRecojo,fragment,"recojoMap").commitNow();
        }
        fragment.getMapAsync(googleMap -> {
            if (getView()!=view) return;
            map=new RecojoMapRenderer(googleMap,view.findViewById(R.id.mapaRecojo),role);
            render(vm.getState().getValue());
        });
        vm.getState().observe(getViewLifecycleOwner(),this::render);
        vm.getMessages().observe(getViewLifecycleOwner(),new EventObserver<>(message -> Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()));
    }
    private boolean hasFineLocation() { return ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED; }
    private void render(RecojoUiState state) {
        if (getView()==null || state==null) return;
        detail.setText(state.details); status.setText(state.status);
        permission.setVisibility(state.showPermission?View.VISIBLE:View.GONE);
        button(finish,state.showFinish,state.canFinish); button(rate,state.showRating,state.canRate);
        button(cancel,state.showCancel,state.canCancel);
        button(navigate,state.showNavigate,true); button(center,state.showCenter,map!=null);
        if (map!=null) map.render(state);
    }
    private void button(Button button,boolean visible,boolean enabled) {
        button.setVisibility(visible?View.VISIBLE:View.GONE); button.setEnabled(enabled); button.setAlpha(enabled?1f:0.5f);
    }
    private void centerMap() {
        RecojoUiState state=vm.getState().getValue();
        if (map==null || state==null) return;
        if (role.centersOnDevice() && !hasFineLocation()) {
            permissions.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}); return;
        }
        if (state.centerPoint!=null) map.center(state.centerPoint);
        else { vm.requestLocation(); Toast.makeText(requireContext(),"Esperando tu ubicación actual. Comprueba que el GPS esté activo.",Toast.LENGTH_LONG).show(); }
    }
    private void navigate() {
        RecojoUiState state=vm.getState().getValue();
        if (state==null || !state.showNavigate || state.pickup==null) return;
        String url="https://www.google.com/maps/dir/?api=1&destination="+state.pickup.destination.latitude+","+state.pickup.destination.longitude+"&travelmode=driving";
        try { startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url))); }
        catch (android.content.ActivityNotFoundException error) { Toast.makeText(requireContext(),"No hay una aplicación de navegación disponible.",Toast.LENGTH_LONG).show(); }
    }
    @Override public void onStop() { if (dialogs!=null) dialogs.dismiss(); super.onStop(); }
    @Override public void onDestroyView() {
        if (map!=null) map.close(); if (dialogs!=null) dialogs.dismiss(); map=null; dialogs=null;
        super.onDestroyView();
    }
}
