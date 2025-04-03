package backend.mapper;

import backend.entity.Category;
import backend.payload.request.CategoryRequest;
import backend.payload.response.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<Category, CategoryRequest, CategoryResponse>{
}
