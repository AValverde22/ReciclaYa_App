package pe.reciclaya.app.domain.model.rewards;

public final class Benefit {
    public final int id;
    public final String name;
    public final String description;
    public final String provider;
    public final int pointsRequired;
    public final String conditions;
    public final Integer stock;

    public Benefit(int id, String name, String description, String provider, int pointsRequired,
                   String conditions, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.provider = provider;
        this.pointsRequired = pointsRequired;
        this.conditions = conditions;
        this.stock = stock;
    }
}
