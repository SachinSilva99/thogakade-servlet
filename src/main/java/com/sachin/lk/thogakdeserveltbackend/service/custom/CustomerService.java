package com.sachin.lk.thogakdeserveltbackend.service.custom;



import com.sachin.lk.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.lk.thogakdeserveltbackend.service.SuperService;
import com.sachin.lk.thogakdeserveltbackend.service.exception.DuplicateException;
import com.sachin.lk.thogakdeserveltbackend.service.exception.NotFoundException;

import java.util.List;

public interface CustomerService extends SuperService {
    boolean save(CustomerDTO customerDTO) throws DuplicateException;
    CustomerDTO search(String id) throws NotFoundException;
    List<String> loadCustomerIds();

}
