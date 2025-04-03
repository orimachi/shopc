package backend.service.imp;

import backend.mapper.BaseMapper;
import backend.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public abstract class BaseServiceImp<E, Key, Req, Res> implements BaseService<E, Key, Req, Res> {
    protected abstract JpaRepository<E, Key> getRepository();
    protected abstract BaseMapper<E,Req,Res> getMapper();

    @Override
    public List<Res> getAll() {
        return getRepository().findAll().stream().map(getMapper()::toDTO).toList();
    }

    @Override
    public Optional<Res> getOneByID(Key id) {
        return Optional.of(getMapper().toDTO(getRepository().getReferenceById(id)));
    }

    @Override
    public Res save(Req request) {
        E entity = getMapper().toEntity(request);
        return getMapper().toDTO(getRepository().save(entity));
    }

    @Override
    public void deleteByID(Key id) {
        getRepository().deleteById(id);
    }
}
