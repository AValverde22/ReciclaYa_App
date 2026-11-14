package pe.reciclaya.app.ui.main.reputation;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import pe.reciclaya.app.R;
import pe.reciclaya.app.domain.model.reputation.Review;
import pe.reciclaya.app.ui.common.util.FechaHora;

public final class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Holder> {
    private final List<Review> values = new ArrayList<>();
    public void submit(List<Review> reviews) {
        values.clear();
        if (reviews != null) values.addAll(reviews);
        notifyDataSetChanged();
    }
    @NonNull @Override public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false));
    }
    @Override public void onBindViewHolder(@NonNull Holder holder, int position) { holder.bind(values.get(position)); }
    @Override public int getItemCount() { return values.size(); }
    static final class Holder extends RecyclerView.ViewHolder {
        final ImageView photo;
        final TextView name, stars, comment, date;
        Holder(View view) {
            super(view);
            photo = view.findViewById(R.id.IVAutorReview);
            name = view.findViewById(R.id.TVAutorReview);
            stars = view.findViewById(R.id.TVEstrellasReview);
            comment = view.findViewById(R.id.TVComentarioReview);
            date = view.findViewById(R.id.TVFechaReview);
        }
        void bind(Review value) {
            name.setText(value.reviewerName);
            stars.setText(value.score + " / 5");
            boolean hasComment = value.comment != null && !value.comment.isBlank();
            comment.setVisibility(hasComment ? View.VISIBLE : View.GONE);
            comment.setText(hasComment ? value.comment : "");
            String formatted = FechaHora.formatearLima(value.createdAt);
            date.setText((formatted == null ? "Fecha no disponible" : formatted) + " · Recojo #" + value.requestId);
            if (value.reviewerPhotoUrl != null && !value.reviewerPhotoUrl.isBlank())
                Glide.with(itemView).load(Uri.parse(value.reviewerPhotoUrl)).into(photo);
            else photo.setImageResource(R.drawable.foto_en_blanco);
        }
    }
}
