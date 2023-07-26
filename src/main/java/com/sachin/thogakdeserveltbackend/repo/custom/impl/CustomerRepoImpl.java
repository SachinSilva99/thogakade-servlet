package com.sachin.thogakdeserveltbackend.repo.custom.impl;


import com.sachin.thogakdeserveltbackend.entity.Customer;
import com.sachin.thogakdeserveltbackend.repo.custom.CustomerRepo;
import com.sachin.thogakdeserveltbackend.repo.exception.ConstraintViolationException;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public class CustomerRepoImpl implements CustomerRepo {

    @Override
    public Customer save(Customer customer, Session session) throws ConstraintViolationException {
        try {
            session.save(customer);
            return customer;
        }catch (Exception e){
            throw new ConstraintViolationException("Did not saved customer");
        }
    }

    @Override
    public Customer update(Customer customer,Session session) throws ConstraintViolationException {
        try {
            session.update(customer);
            return customer;
        }catch (Exception e){
            throw new ConstraintViolationException("Did not update customer");
        }
    }

    @Override
    public void delete(Customer customer, Session session) throws ConstraintViolationException {
        try {
            session.delete(customer);
        }catch (Exception e){
            throw new ConstraintViolationException("Did not delete customer");
        }
    }

    @Override
    public List<Customer> findAll(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);
        query.from(Customer.class);
        return session.createQuery(query).getResultList();
    }

    @Override
    public Optional<Customer> findByPk(String pk,Session session) {
        Customer customer = session.get(Customer.class, pk);
        return customer == null ? Optional.empty() : Optional.of(customer);
    }

    @Override
    public boolean existByPk(String pk,Session session) {
        Customer customer = session.get(Customer.class, pk);
        return customer != null;
    }

    @Override
    public long count(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> query = criteriaBuilder.createQuery(Customer.class);
        query.from(Customer.class);
        return session.createQuery(query).getResultList().size();
    }
}
