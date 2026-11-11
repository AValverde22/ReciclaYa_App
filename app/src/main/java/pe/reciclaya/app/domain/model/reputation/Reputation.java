package pe.reciclaya.app.domain.model.reputation;

import java.util.List;

public final class Reputation {
    public final int userId;
    public final String fullName;
    public final String profilePhotoUrl;
    public final float averageRating;
    public final int totalRatings;
    public final List<Review> reviews;

    public Reputation(int userId, String fullName, String profilePhotoUrl, float averageRating,
                      int totalRatings, List<Review> reviews) {
        this.userId = userId;
        this.fullName = fullName;
        this.profilePhotoUrl = profilePhotoUrl;
        this.averageRating = averageRating;
        this.totalRatings = totalRatings;
        this.reviews = reviews;
    }
}
