package pe.reciclaya.app.data.model.user.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public final class ReputationResponse {
    public UserSummary user;
    @SerializedName("average_rating") public float averageRating;
    @SerializedName("total_ratings") public int totalRatings;
    public List<Review> reviews;

    public static final class UserSummary {
        public int id;
        @SerializedName("full_name") public String fullName;
        @SerializedName("profile_photo_url") public String profilePhotoUrl;
        public float score;
    }

    public static final class Review {
        public int id;
        @SerializedName("solicitud_id") public int requestId;
        @SerializedName("puntuacion") public int score;
        @SerializedName("comentario") public String comment;
        @SerializedName("created_at") public String createdAt;
        @SerializedName("evaluador") public Reviewer reviewer;
    }

    public static final class Reviewer {
        public int id;
        @SerializedName("full_name") public String fullName;
        @SerializedName("profile_photo_url") public String profilePhotoUrl;
    }
}
