package pe.reciclaya.app.domain.model.recojo;

/** Immutable application snapshot, independent of the server JSON and Android. */
public final class Recojo {
    public final int id, creatorId, recyclerId;
    public final String material, size, address, day, time, counterpartName, locationUpdatedAt;
    public final GeoPoint destination, recyclerLocation;
    public final boolean userConfirmed, recyclerConfirmed, arrived, hasRated;
    public Recojo(int id, int creatorId, int recyclerId, String material, String size, String address,
                  String day, String time, String counterpartName, String locationUpdatedAt,
                  GeoPoint destination, GeoPoint recyclerLocation, boolean userConfirmed,
                  boolean recyclerConfirmed, boolean arrived, boolean hasRated) {
        this.id=id; this.creatorId=creatorId; this.recyclerId=recyclerId; this.material=material;
        this.size=size; this.address=address; this.day=day; this.time=time; this.counterpartName=counterpartName;
        this.locationUpdatedAt=locationUpdatedAt; this.destination=destination; this.recyclerLocation=recyclerLocation;
        this.userConfirmed=userConfirmed; this.recyclerConfirmed=recyclerConfirmed; this.arrived=arrived; this.hasRated=hasRated;
    }
    public boolean confirmedBy(int userId) { return userId==creatorId?userConfirmed:userId==recyclerId && recyclerConfirmed; }
    public boolean confirmedByBoth() { return userConfirmed && recyclerConfirmed; }
    public Recojo withConfirmation(int userId) {
        return copy(userConfirmed || userId == creatorId, recyclerConfirmed || userId == recyclerId, hasRated);
    }
    public Recojo withRating() { return copy(userConfirmed, recyclerConfirmed, true); }
    private Recojo copy(boolean userConfirmed, boolean recyclerConfirmed, boolean rated) {
        return new Recojo(id,creatorId,recyclerId,material,size,address,day,time,counterpartName,locationUpdatedAt,
            destination,recyclerLocation,userConfirmed,recyclerConfirmed,arrived,rated);
    }
}
