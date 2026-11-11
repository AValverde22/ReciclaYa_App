package pe.reciclaya.app.domain.model.recojo;

public final class ConfirmationResult {
    public final boolean completed;
    public final int pointsAwarded;
    public final Integer pointsBalance;

    public ConfirmationResult(boolean completed, int pointsAwarded, Integer pointsBalance) {
        this.completed = completed;
        this.pointsAwarded = pointsAwarded;
        this.pointsBalance = pointsBalance;
    }
}
