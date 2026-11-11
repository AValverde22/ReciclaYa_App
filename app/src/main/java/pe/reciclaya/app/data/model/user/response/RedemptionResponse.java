package pe.reciclaya.app.data.model.user.response;

import com.google.gson.annotations.SerializedName;

public final class RedemptionResponse {
    public String message;
    public Redemption redemption;

    public static final class Redemption {
        @SerializedName("redemption_id") public int redemptionId;
        @SerializedName("benefit_id") public int benefitId;
        @SerializedName("benefit_name") public String benefitName;
        @SerializedName("points_spent") public int pointsSpent;
        public int balance;
    }
}
