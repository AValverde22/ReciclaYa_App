package pe.reciclaya.app.domain.model.recojo;

/** Immutable application snapshot, independent of the server JSON and Android. */
public final class Recojo {
    public final int id, creatorId, recyclerId;
    public final String material, size, address, day, time, status, counterpartName, locationUpdatedAt;
    public final GeoPoint destination, recyclerLocation;
    public final boolean userConfirmed, recyclerConfirmed, arrived, hasRated;
    public final int pointsAwarded;
    public final Integer pointsBalance;
    public Recojo(int id, int creatorId, int recyclerId, String material, String size, String address,
                  String day, String time, String status, String counterpartName, String locationUpdatedAt,
                  GeoPoint destination, GeoPoint recyclerLocation, boolean userConfirmed,
                  boolean recyclerConfirmed, boolean arrived, boolean hasRated,
                  int pointsAwarded, Integer pointsBalance) {
        this.id=id; this.creatorId=creatorId; this.recyclerId=recyclerId; this.material=material;
        this.size=size; this.address=address; this.day=day; this.time=time; this.status=status; this.counterpartName=counterpartName;
        this.locationUpdatedAt=locationUpdatedAt; this.destination=destination; this.recyclerLocation=recyclerLocation;
        this.userConfirmed=userConfirmed; this.recyclerConfirmed=recyclerConfirmed; this.arrived=arrived; this.hasRated=hasRated;
        this.pointsAwarded=pointsAwarded; this.pointsBalance=pointsBalance;
    }
    public boolean isFinalized() { return "Finalizada".equals(status); }
    public boolean confirmedBy(int userId) { return userId==creatorId?userConfirmed:userId==recyclerId && recyclerConfirmed; }
    public boolean confirmedByBoth() { return userConfirmed && recyclerConfirmed; }
    public Recojo withConfirmation(int userId) {
        return copy(userConfirmed || userId == creatorId, recyclerConfirmed || userId == recyclerId, hasRated);
    }
    public Recojo withRating() { return copy(userConfirmed, recyclerConfirmed, true); }
    public Recojo withCompletion(int awarded, Integer balance) {
        return new Recojo(id,creatorId,recyclerId,material,size,address,day,time,"Finalizada",counterpartName,locationUpdatedAt,
                destination,recyclerLocation,true,true,arrived,hasRated,awarded,balance);
    }
    private Recojo copy(boolean userConfirmed, boolean recyclerConfirmed, boolean rated) {
        return new Recojo(id,creatorId,recyclerId,material,size,address,day,time,status,counterpartName,locationUpdatedAt,
            destination,recyclerLocation,userConfirmed,recyclerConfirmed,arrived,rated,pointsAwarded,pointsBalance);
    }
}
