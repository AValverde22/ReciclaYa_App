package pe.reciclaya.app.domain.model.recojo;

public final class LocationFix {
    public final GeoPoint point;
    public final float accuracyMeters;
    public final long elapsedMillis;
    public LocationFix(GeoPoint point, float accuracyMeters, long elapsedMillis) {
        this.point = point; this.accuracyMeters = accuracyMeters; this.elapsedMillis = elapsedMillis;
    }
    public boolean isRecent(long now, long maximumAge) {
        long age = now - elapsedMillis;
        return point != null && age >= 0 && age < maximumAge && Float.isFinite(accuracyMeters) && accuracyMeters >= 0 && accuracyMeters <= 100;
    }
}
