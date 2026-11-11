package pe.reciclaya.app.ui.main.rewards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.rewards.Benefit;

public final class BenefitAdapter extends RecyclerView.Adapter<BenefitAdapter.Holder> {
    private final List<Benefit> values = new ArrayList<>();
    private final Consumer<Benefit> action;
    private int balance;
    public BenefitAdapter(Consumer<Benefit> action) { this.action = action; }
    public void submit(List<Benefit> items, int currentBalance) {
        values.clear();
        if (items != null) values.addAll(items);
        balance = currentBalance;
        notifyDataSetChanged();
    }
    @NonNull @Override public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_benefit, parent, false));
    }
    @Override public void onBindViewHolder(@NonNull Holder holder, int position) { holder.bind(values.get(position)); }
    @Override public int getItemCount() { return values.size(); }

    final class Holder extends RecyclerView.ViewHolder {
        final TextView name, provider, description, points, conditions;
        final Button redeem;
        Holder(View view) {
            super(view);
            name = view.findViewById(R.id.TVNombreBeneficio);
            provider = view.findViewById(R.id.TVProveedorBeneficio);
            description = view.findViewById(R.id.TVDescripcionBeneficio);
            points = view.findViewById(R.id.TVPuntosBeneficio);
            conditions = view.findViewById(R.id.TVCondicionesBeneficio);
            redeem = view.findViewById(R.id.BtnCanjearBeneficio);
        }
        void bind(Benefit value) {
            name.setText(value.name);
            provider.setText(value.provider);
            description.setText(value.description);
            points.setText(value.pointsRequired + " puntos");
            conditions.setText(value.conditions);
            boolean available = value.stock == null || value.stock > 0;
            redeem.setEnabled(available);
            redeem.setAlpha(redeem.isEnabled() ? 1f : 0.5f);
            redeem.setText(available ? "Canjear" : "Agotado");
            redeem.setOnClickListener(view -> action.accept(value));
        }
    }
}
