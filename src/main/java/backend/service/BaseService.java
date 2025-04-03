package backend.service;


import java.util.List;
import java.util.Optional;

public interface BaseService<E, Key, Req, Res> {
    List<Res> getAll();
    Optional<Res> getOneByID(Key id);
    Res save(Req request);
    void deleteByID(Key id);
}
