package backend.service.imp;

import backend.entity.Category;
import backend.mapper.BaseMapper;
import backend.mapper.CategoryMapper;
import backend.payload.request.CategoryRequest;
import backend.payload.response.CategoryResponse;
import backend.repository.CategoryRepository;
import backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp extends BaseServiceImp<Category,Integer, CategoryRequest, CategoryResponse> implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    protected JpaRepository<Category, Integer> getRepository() {
        return categoryRepository;
    }

    @Override
    protected BaseMapper<Category, CategoryRequest, CategoryResponse> getMapper() {
        return categoryMapper;
    }
}
