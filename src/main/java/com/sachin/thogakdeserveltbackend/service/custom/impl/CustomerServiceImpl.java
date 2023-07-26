package com.sachin.thogakdeserveltbackend.service.custom.impl;


import com.sachin.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.thogakdeserveltbackend.entity.Customer;
import com.sachin.thogakdeserveltbackend.repo.RepoFactory;
import com.sachin.thogakdeserveltbackend.repo.RepoType;
import com.sachin.thogakdeserveltbackend.repo.custom.CustomerRepo;
import com.sachin.thogakdeserveltbackend.service.custom.CustomerService;
import com.sachin.thogakdeserveltbackend.service.exception.DuplicateException;
import com.sachin.thogakdeserveltbackend.service.exception.NotFoundException;
import com.sachin.thogakdeserveltbackend.util.FactoryConfiguration;
import com.sachin.thogakdeserveltbackend.util.Mapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Collections;
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
        List<CustomerDTO> customerDTOList = customerRepo.findAll(session)
                .stream()
                .map(customer -> mapper.toCustomerDTO(customer)).collect(Collectors.toList());
        session.close();
        return customerDTOList;
    }

    @Override
    public void delete(String id) throws NotFoundException, ConstraintViolationException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Optional<Customer> customerOp = customerRepo.findByPk(id, session);
            if (customerOp.isPresent()) {
                Customer customer = customerOp.get();
                customerRepo.delete(customer, session);
                transaction.commit();
                System.out.println("Customer with ID: " + id + " deleted successfully");
            } else {
                throw new NotFoundException("Customer with ID: " + id + " not found");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        Customer updatedCustomer;
        try {
            transaction = session.beginTransaction();
            Optional<Customer> customerOp = customerRepo.findByPk(customerDTO.getId(), session);
            if (customerOp.isPresent()) {
                Customer customer = customerOp.get();
                customer.setName(customerDTO.getName());
                customer.setAddress(customerDTO.getAddress());
                updatedCustomer = customerRepo.update(customer, session);
                transaction.commit();
                return mapper.toCustomerDTO(updatedCustomer);
            } else {
                throw new NotFoundException("Customer with ID: " + customerDTO.getId() + " not found");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<CustomerDTO> searchCustomers(String data) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return customerRepo.findAll(session).stream()
                    .filter(customer -> customer.getId().contains(data) || customer.getName().contains(data))
                    .map(customer -> mapper.toCustomerDTO(customer))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return Collections.emptyList();
    }
}
