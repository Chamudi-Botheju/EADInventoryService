package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.dto.ItemRequestDTO;
import com.example.inventoryservice.dto.ItemResponseDTO;
import com.example.inventoryservice.entity.Category;
import com.example.inventoryservice.entity.InventoryItem;
import com.example.inventoryservice.repository.CategoryRepository;
import com.example.inventoryservice.repository.ItemRepository;
import com.example.inventoryservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public String addNewItem(ItemRequestDTO itemRequestDTO) {
        Category category = categoryRepository.findById(itemRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + itemRequestDTO.getCategoryId()));

        InventoryItem newInventoryItem = InventoryItem.builder()
                .itemName(itemRequestDTO.getItemName())
                .category(category)
                .description(itemRequestDTO.getDescription())
                .unitPrice(itemRequestDTO.getUnitPrice())
                .imageURL(itemRequestDTO.getImageURL())
                .availableQuantity(itemRequestDTO.getAvailableQuantity())
                .unit(itemRequestDTO.getUnit())
                .build();
        itemRepository.save(newInventoryItem);
        return "Product is successfully added to the inventory";
    }

    @Override
    public Page<ItemResponseDTO> getAllItems(Integer page, Integer size) {
        if (page < 0 || size < 0) {
            throw new IllegalArgumentException("Page and size should be non-negative");
        }
        PageRequest pageable = PageRequest.of(page, size);
        Page<InventoryItem> pageEntities = itemRepository.findAll(pageable);

        List<InventoryItem> entityList = pageEntities.getContent();
        List<ItemResponseDTO> dtoList = new ArrayList<>();

        entityList.forEach(entity -> dtoList.add(convertItemEntityToItemResponse(entity)));

        return new PageImpl<>(dtoList, pageable, pageEntities.getTotalElements());
    }

    @Override
    public ItemResponseDTO convertItemEntityToItemResponse(InventoryItem entity) {
        return ItemResponseDTO.builder()
                .itemName(entity.getItemName())
                .category(entity.getCategory())
                .availableQuantity(entity.getAvailableQuantity())
                .unitPrice(entity.getUnitPrice())
                .itemId(entity.getItemId())
                .description(entity.getDescription())
                .imageURL(entity.getImageURL())
                .unit(entity.getUnit())
                .build();
    }

    @Override
    public ItemResponseDTO getItemById(int id) {
        InventoryItem inventoryItemEntity = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with ID: " + id));

        return convertItemEntityToItemResponse(inventoryItemEntity);
    }

    @Override
    public String updateItem(ItemRequestDTO itemRequestDTO, int id) {
        Category category = categoryRepository.findById(itemRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + itemRequestDTO.getCategoryId()));

        InventoryItem inventoryItemEntity = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with ID: " + id));

        inventoryItemEntity.setItemName(itemRequestDTO.getItemName());
        inventoryItemEntity.setCategory(category);
        inventoryItemEntity.setUnitPrice(itemRequestDTO.getUnitPrice());
        inventoryItemEntity.setDescription(itemRequestDTO.getDescription());
        inventoryItemEntity.setAvailableQuantity(itemRequestDTO.getAvailableQuantity());
        inventoryItemEntity.setImageURL(itemRequestDTO.getImageURL());
        inventoryItemEntity.setUnit(itemRequestDTO.getUnit());
        itemRepository.save(inventoryItemEntity);
        return "Item is successfully updated";
    }

    @Override
    public String deleteItem(int id) {
        itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with ID: " + id));
        itemRepository.deleteById(id);
        return "Item is successfully deleted";
    }

    @Override
    public String updateItemQuantity(int id, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity should not be negative");
        }

        InventoryItem inventoryItemEntity = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found with ID: " + id));

        inventoryItemEntity.setAvailableQuantity(inventoryItemEntity.getAvailableQuantity() + quantity);
        itemRepository.save(inventoryItemEntity);
        return "Item Quantity(stock) is successfully updated";
    }
}
