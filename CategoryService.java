package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.CategoryRequestDTO;
import com.example.inventoryservice.dto.CategoryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    String addNewCategory(CategoryRequestDTO categoryRequestDTO);

    String deleteCategory(int id);

    List<CategoryResponseDTO> getAllCategories();
}
