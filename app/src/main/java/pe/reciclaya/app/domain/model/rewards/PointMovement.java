package pe.reciclaya.app.domain.model.rewards;

public final class PointMovement {
    public final int id;
    public final String type;
    public final int points;
    public final String description;
    public final String createdAt;

    public PointMovement(int id, String type, int points, String description, String createdAt) {
        this.id = id;
        this.type = type;
        this.points = points;
        this.description = description;
        this.createdAt = createdAt;
    }
}
