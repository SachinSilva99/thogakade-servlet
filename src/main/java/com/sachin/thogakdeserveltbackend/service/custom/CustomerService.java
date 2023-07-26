package com.sachin.thogakdeserveltbackend.service.custom;



import com.sachin.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.thogakdeserveltbackend.service.SuperService;
import com.sachin.thogakdeserveltbackend.service.exception.DuplicateException;
import com.sachin.thogakdeserveltbackend.service.exception.NotFoundException;

import java.util.List;

public interface CustomerService extends SuperService {
    boolean save(CustomerDTO customerDTO) throws DuplicateException;
    CustomerDTO search(String id) throws NotFoundException;
    List<String> loadCustomerIds();
    List<CustomerDTO>getAllCustomers();

    void delete(String id) throws Exception;

    CustomerDTO update(CustomerDTO customerDTO);

    List<CustomerDTO> searchCustomers(String data);
}
