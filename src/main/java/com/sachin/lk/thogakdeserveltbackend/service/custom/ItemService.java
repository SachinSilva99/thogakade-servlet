package com.sachin.lk.thogakdeserveltbackend.service.custom;


import com.sachin.lk.thogakdeserveltbackend.dto.ItemDTO;
import com.sachin.lk.thogakdeserveltbackend.service.SuperService;
import com.sachin.lk.thogakdeserveltbackend.service.exception.FailedToDelete;
import com.sachin.lk.thogakdeserveltbackend.service.exception.NotFoundException;
import com.sachin.lk.thogakdeserveltbackend.service.exception.UpdateFailedException;

import java.util.List;

public interface ItemService extends SuperService {
    List<String> loadItemCodes();
    ItemDTO search(String code);

    void save(ItemDTO itemDTO) throws Exception;

    List<ItemDTO> searchItems(String data);

    List<ItemDTO> getAllItems();

    void update(ItemDTO itemDTO) throws NotFoundException, UpdateFailedException;

    void delete(String code)throws NotFoundException, FailedToDelete;
}
