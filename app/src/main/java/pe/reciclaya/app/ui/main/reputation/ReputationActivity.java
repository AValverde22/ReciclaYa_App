package pe.reciclaya.app.ui.main.reputation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.reputation.Reputation;
import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.ui.common.event.EventObserver;

public final class ReputationActivity extends AppCompatActivity {
    private static final String EXTRA_USER_ID = "reputation_user_id";
    private ReputationViewModel viewModel;
    private ReviewAdapter adapter;
    private ImageView photo;
    private TextView name, average, count, empty;
    private LinearLayout loading;

    public static Intent intent(Context context, int userId) {
        return new Intent(context, ReputationActivity.class).putExtra(EXTRA_USER_ID, userId);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reputation);
        findViewById(R.id.IVRegresarReputation).setOnClickListener(view -> finish());
        photo = findViewById(R.id.IVFotoReputation);
        name = findViewById(R.id.TVNombreReputation);
        average = findViewById(R.id.TVPromedioReputation);
        count = findViewById(R.id.TVCantidadReputation);
        empty = findViewById(R.id.TVReviewsVacio);
        loading = findViewById(R.id.LLLoadingReputation);
        RecyclerView list = findViewById(R.id.RVReviews);
        adapter = new ReviewAdapter();
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        int userId = getIntent().getIntExtra(EXTRA_USER_ID, User.getInstance().getID());
        viewModel = new ViewModelProvider(this).get(ReputationViewModel.class);
        viewModel.getReputation().observe(this, this::show);
        viewModel.getLoading().observe(this, value -> loading.setVisibility(Boolean.TRUE.equals(value) ? View.VISIBLE : View.GONE));
        viewModel.getMessage().observe(this, new EventObserver<>(value -> Toast.makeText(this, value, Toast.LENGTH_LONG).show()));
        viewModel.load(userId);
    }

    private void show(Reputation value) {
        if (value == null) return;
        name.setText(value.fullName);
        average.setText(String.format(java.util.Locale.US, "%.1f / 5", value.averageRating));
        count.setText(value.totalRatings == 1 ? "1 calificación" : value.totalRatings + " calificaciones");
        adapter.submit(value.reviews);
        empty.setVisibility(value.reviews == null || value.reviews.isEmpty() ? View.VISIBLE : View.GONE);
        if (value.profilePhotoUrl != null && !value.profilePhotoUrl.isBlank())
            Glide.with(this).load(Uri.parse(value.profilePhotoUrl)).into(photo);
        else photo.setImageResource(R.drawable.foto_en_blanco);
    }
}
