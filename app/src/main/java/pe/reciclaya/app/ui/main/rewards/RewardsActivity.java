package pe.reciclaya.app.ui.main.rewards;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.rewards.Benefit;
import pe.reciclaya.app.domain.model.rewards.RewardsAccount;
import pe.reciclaya.app.ui.common.event.EventObserver;

public final class RewardsActivity extends AppCompatActivity {
    private RewardsViewModel viewModel;
    private BenefitAdapter benefitAdapter;
    private PointMovementAdapter historyAdapter;
    private TextView balance, emptyHistory;
    private LinearLayout loading;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);
        findViewById(R.id.IVRegresarRewards).setOnClickListener(view -> finish());
        balance = findViewById(R.id.TVSaldoRewards);
        emptyHistory = findViewById(R.id.TVHistorialVacioRewards);
        loading = findViewById(R.id.LLLoadingRewards);
        RecyclerView benefits = findViewById(R.id.RVBeneficiosRewards);
        RecyclerView history = findViewById(R.id.RVHistorialRewards);
        benefitAdapter = new BenefitAdapter(this::confirmRedeem);
        historyAdapter = new PointMovementAdapter();
        benefits.setLayoutManager(new LinearLayoutManager(this));
        history.setLayoutManager(new LinearLayoutManager(this));
        benefits.setAdapter(benefitAdapter);
        history.setAdapter(historyAdapter);
        benefits.setNestedScrollingEnabled(false);
        history.setNestedScrollingEnabled(false);

        viewModel = new ViewModelProvider(this).get(RewardsViewModel.class);
        viewModel.getAccount().observe(this, this::showAccount);
        viewModel.getBenefits().observe(this, values -> benefitAdapter.submit(values,
                viewModel.getAccount().getValue() == null ? 0 : viewModel.getAccount().getValue().balance));
        viewModel.getLoading().observe(this, value -> loading.setVisibility(Boolean.TRUE.equals(value) ? View.VISIBLE : View.GONE));
        viewModel.getMessage().observe(this, new EventObserver<>(value -> Toast.makeText(this, value, Toast.LENGTH_LONG).show()));
    }

    private void showAccount(RewardsAccount value) {
        if (value == null) return;
        balance.setText(value.balance + " puntos");
        historyAdapter.submit(value.history);
        emptyHistory.setVisibility(value.history == null || value.history.isEmpty() ? View.VISIBLE : View.GONE);
        benefitAdapter.submit(viewModel.getBenefits().getValue() == null ? Collections.emptyList() : viewModel.getBenefits().getValue(), value.balance);
    }

    private void confirmRedeem(Benefit value) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar canje")
                .setMessage("Canjear “" + value.name + "” por " + value.pointsRequired + " puntos.")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Confirmar", (dialog, which) -> viewModel.redeem(value))
                .show();
    }
}
