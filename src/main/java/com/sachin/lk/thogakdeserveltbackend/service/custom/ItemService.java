package com.sachin.lk.thogakdeserveltbackend.service.custom;



import com.sachin.lk.thogakdeserveltbackend.dto.ItemDTO;
import com.sachin.lk.thogakdeserveltbackend.service.SuperService;

import java.util.List;

public interface ItemService extends SuperService {
    List<String> loadItemCodes();
    ItemDTO search(String code);

}
