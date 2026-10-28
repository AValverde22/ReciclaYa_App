package pe.reciclaya.app.data.local;

import pe.reciclaya.app.domain.model.user.User;
import pe.reciclaya.app.domain.repository.recojo.RecojoSession;

public final class RecojoSessionImp implements RecojoSession {
    @Override public int userId() { return User.getInstance().getID(); }
    @Override public boolean isRecycler() { return User.getInstance().getRole()!=null && "Reciclador".equals(User.getInstance().getRole().getRole()); }
}
