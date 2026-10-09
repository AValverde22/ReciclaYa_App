package pe.reciclaya.app.data.mapper;

import pe.reciclaya.app.data.model.user.response.UserSolicitudResponse;
import pe.reciclaya.app.domain.model.user.UserSolicitud;

public class UserSolicitudMapper {
    private UserSolicitudMapper() {}

    public static UserSolicitud toDomain(UserSolicitudResponse response) {
        if(response == null) return null;

        return new UserSolicitud(
                response.getID(),
                response.getFullName(),
                response.getProfilePhotoURL(),
                response.getScore()
        );
    }
}
