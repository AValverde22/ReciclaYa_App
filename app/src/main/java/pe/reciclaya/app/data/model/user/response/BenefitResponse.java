package pe.reciclaya.app.data.model.user.response;

import com.google.gson.annotations.SerializedName;

public final class BenefitResponse {
    public int id;
    public String name;
    public String description;
    public String provider;
    @SerializedName("points_required") public int pointsRequired;
    public String conditions;
    public Integer stock;
}
