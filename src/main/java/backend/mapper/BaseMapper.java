package backend.mapper;


public interface BaseMapper<E, Req, Res>{
     Res toDTO(E entity);
     E toEntity(Req request);
}
