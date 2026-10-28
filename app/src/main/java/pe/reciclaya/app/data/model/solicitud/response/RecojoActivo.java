package pe.reciclaya.app.data.model.solicitud.response;

public class RecojoActivo {
    public Solicitud solicitud;
    public Persona counterpart;
    public boolean arrived, has_rated;
    public static class Persona { public int id; public String full_name; public float score; }
    public static class Solicitud {
        public int id, created_by, accepted_by;
        public String type, size, address, day, time, status, location_updated_at;
        public double latitude, longitude;
        public Double recycler_latitude, recycler_longitude;
        public boolean user_confirmed, recycler_confirmed;
    }
    public static class Ruta { public String polyline, message; }
}
