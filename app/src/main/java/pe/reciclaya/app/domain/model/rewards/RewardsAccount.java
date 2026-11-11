package pe.reciclaya.app.domain.model.rewards;

import java.util.List;

public final class RewardsAccount {
    public final int balance;
    public final List<PointMovement> history;

    public RewardsAccount(int balance, List<PointMovement> history) {
        this.balance = balance;
        this.history = history;
    }
}
