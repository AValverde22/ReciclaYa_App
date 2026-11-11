package pe.reciclaya.app.data.model.user.response;

import com.google.gson.annotations.SerializedName;

public final class PointMovementResponse {
    public int id;
    public String type;
    public int points;
    public String description;
    @SerializedName("created_at") public String createdAt;
}
