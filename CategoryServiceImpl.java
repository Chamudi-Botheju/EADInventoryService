package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.dto.CategoryRequestDTO;
import com.example.inventoryservice.dto.CategoryResponseDTO;
import com.example.inventoryservice.entity.Category;
import com.example.inventoryservice.repository.CategoryRepository;
import com.example.inventoryservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public String addNewCategory(CategoryRequestDTO categoryRequestDTO) {
        Category categoryEntity = Category.builder()
                .description(categoryRequestDTO.getDescription())
                .name(categoryRequestDTO.getName())
                .build();

        categoryRepository.save(categoryEntity);
        return "New category is successfully added";
    }

    @Override
    public String deleteCategory(int id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
        return "Category is successfully removed";
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDTO> categoryResponseDTOList = new ArrayList<>();
        for (Category categoryEntity : categoryList) {
            CategoryResponseDTO categoryResponseDTO = CategoryResponseDTO.builder()
                    .categoryId(categoryEntity.getCategoryId())
                    .description(categoryEntity.getDescription())
                    .name(categoryEntity.getName())
                    .build();
            categoryResponseDTOList.add(categoryResponseDTO);
        }
        return categoryResponseDTOList;
    }
}
