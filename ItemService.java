package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.ItemRequestDTO;
import com.example.inventoryservice.dto.ItemResponseDTO;
import com.example.inventoryservice.entity.InventoryItem;
import org.springframework.data.domain.Page;

public interface ItemService {

    String addNewItem(ItemRequestDTO itemRequestDTO);

    Page<ItemResponseDTO> getAllItems(Integer page, Integer size);

    ItemResponseDTO convertItemEntityToItemResponse(InventoryItem entity);

    ItemResponseDTO getItemById(int id);

    String updateItem(ItemRequestDTO itemRequestDTO, int id);

    String deleteItem(int id);

    String updateItemQuantity(int id, int quantity);
}
