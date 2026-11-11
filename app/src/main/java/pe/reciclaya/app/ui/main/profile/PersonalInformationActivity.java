package pe.reciclaya.app.ui.main.profile;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.user.Profile;
import pe.reciclaya.app.ui.common.event.EventObserver;

public final class PersonalInformationActivity extends AppCompatActivity {
    private EditText fullName, email, photoUrl;
    private ImageView photo;
    private TextView role, status;
    private Button save;
    private LinearLayout loading;
    private PersonalInformationViewModel viewModel;
    private boolean initialized;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        findViewById(R.id.IVRegresarPI).setOnClickListener(view -> finish());
        fullName = findViewById(R.id.ETNombrePI);
        email = findViewById(R.id.ETCorreoPI);
        photoUrl = findViewById(R.id.ETFotoPI);
        photo = findViewById(R.id.IVFotoPI);
        role = findViewById(R.id.TVRolPI);
        status = findViewById(R.id.TVEstadoPI);
        save = findViewById(R.id.BtnGuardarPI);
        loading = findViewById(R.id.LLLoadingPI);

        viewModel = new ViewModelProvider(this).get(PersonalInformationViewModel.class);
        viewModel.getProfile().observe(this, this::showProfile);
        viewModel.getLoading().observe(this, value -> {
            boolean busy = Boolean.TRUE.equals(value);
            loading.setVisibility(busy ? View.VISIBLE : View.GONE);
            save.setEnabled(!busy);
        });
        viewModel.getFullNameError().observe(this, fullName::setError);
        viewModel.getEmailError().observe(this, email::setError);
        viewModel.getPhotoError().observe(this, photoUrl::setError);
        viewModel.getMessage().observe(this, new EventObserver<>(value ->
                Toast.makeText(this, value, Toast.LENGTH_LONG).show()));
        save.setOnClickListener(view -> viewModel.save(fullName.getText().toString(),
                email.getText().toString(), photoUrl.getText().toString()));
    }

    private void showProfile(Profile value) {
        if (value == null) return;
        if (!initialized) {
            fullName.setText(value.fullName);
            email.setText(value.email);
            photoUrl.setText(value.profilePhotoUrl);
            initialized = true;
        } else {
            fullName.setText(value.fullName);
            email.setText(value.email);
            photoUrl.setText(value.profilePhotoUrl);
        }
        role.setText(value.role == null ? "" : value.role.getRole());
        boolean pending = "Pendiente de validación".equals(value.validationStatus);
        status.setVisibility(pending ? View.VISIBLE : View.GONE);
        status.setText(pending ? "Correo actualizado · Pendiente de validación" : "");
        if (value.profilePhotoUrl != null && !value.profilePhotoUrl.isBlank())
            Glide.with(this).load(Uri.parse(value.profilePhotoUrl)).into(photo);
        else photo.setImageResource(R.drawable.foto_en_blanco);
    }
}
