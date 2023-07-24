package com.sachin.lk.thogakdeserveltbackend.service.custom.impl;


import com.sachin.lk.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.lk.thogakdeserveltbackend.entity.Customer;
import com.sachin.lk.thogakdeserveltbackend.repo.RepoFactory;
import com.sachin.lk.thogakdeserveltbackend.repo.RepoType;
import com.sachin.lk.thogakdeserveltbackend.repo.custom.CustomerRepo;
import com.sachin.lk.thogakdeserveltbackend.service.custom.CustomerService;
import com.sachin.lk.thogakdeserveltbackend.service.exception.DuplicateException;
import com.sachin.lk.thogakdeserveltbackend.service.exception.NotFoundException;
import com.sachin.lk.thogakdeserveltbackend.util.FactoryConfiguration;
import com.sachin.lk.thogakdeserveltbackend.util.Mapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo = RepoFactory.getInstance().getRepo(RepoType.CUSTOMER);
    private final Mapper mapper = new Mapper();

    @Override
    public boolean save(CustomerDTO customerDTO) throws DuplicateException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = mapper.toCustomer(customerDTO);
        try {
            customerRepo.save(customer, session);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            transaction.rollback();
            throw new DuplicateException("Customer already exists");
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public CustomerDTO search(String id) throws NotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Optional<Customer> customerOp = customerRepo.findByPk(id, session);
        Customer customer;
        if (customerOp.isPresent()) {
            customer = customerOp.get();
            session.close();
            return mapper.toCustomerDTO(customer);
        }
        return null;
    }

    @Override
    public List<String> loadCustomerIds() {
        Session session = FactoryConfiguration.getInstance().getSession();
        return customerRepo.findAll(session)
                .stream().map(customer -> mapper.toCustomerDTO(customer)
                        .getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        Session session = FactoryConfiguration.getInstance().getSession();
        return customerRepo.findAll(session)
                .stream()
                .map(customer -> mapper.toCustomerDTO(customer)).collect(Collectors.toList());
    }
}
