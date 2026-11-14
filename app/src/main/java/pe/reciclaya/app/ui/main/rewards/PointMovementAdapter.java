package pe.reciclaya.app.ui.main.rewards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.rewards.PointMovement;
import pe.reciclaya.app.ui.common.util.FechaHora;

public final class PointMovementAdapter extends RecyclerView.Adapter<PointMovementAdapter.Holder> {
    private final List<PointMovement> values = new ArrayList<>();
    public void submit(List<PointMovement> items) {
        values.clear();
        if (items != null) values.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull @Override public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point_movement, parent, false));
    }
    @Override public void onBindViewHolder(@NonNull Holder holder, int position) { holder.bind(values.get(position)); }
    @Override public int getItemCount() { return values.size(); }
    static final class Holder extends RecyclerView.ViewHolder {
        final TextView description, date, points;
        Holder(View view) {
            super(view);
            description = view.findViewById(R.id.TVDescripcionMovimiento);
            date = view.findViewById(R.id.TVFechaMovimiento);
            points = view.findViewById(R.id.TVPuntosMovimiento);
        }
        void bind(PointMovement value) {
            description.setText(value.description);
            String formatted = FechaHora.formatearLima(value.createdAt);
            date.setText(formatted == null ? "Fecha no disponible" : formatted);
            points.setText((value.points > 0 ? "+" : "") + value.points);
            points.setTextColor(itemView.getContext().getColor(value.points >= 0 ? R.color.verde : R.color.rojo_dos));
        }
    }
}
