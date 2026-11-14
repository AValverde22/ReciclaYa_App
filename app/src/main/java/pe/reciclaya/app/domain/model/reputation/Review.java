package pe.reciclaya.app.domain.model.reputation;

public final class Review {
    public final int id;
    public final int requestId;
    public final int score;
    public final String comment;
    public final String createdAt;
    public final int reviewerId;
    public final String reviewerName;
    public final String reviewerPhotoUrl;

    public Review(int id, int requestId, int score, String comment, String createdAt,
                  int reviewerId, String reviewerName, String reviewerPhotoUrl) {
        this.id = id;
        this.requestId = requestId;
        this.score = score;
        this.comment = comment;
        this.createdAt = createdAt;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.reviewerPhotoUrl = reviewerPhotoUrl;
    }
}
