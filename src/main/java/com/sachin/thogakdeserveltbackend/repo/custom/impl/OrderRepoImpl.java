package com.sachin.thogakdeserveltbackend.repo.custom.impl;


import com.sachin.thogakdeserveltbackend.entity.Orders;
import com.sachin.thogakdeserveltbackend.repo.custom.OrderRepo;
import com.sachin.thogakdeserveltbackend.repo.exception.ConstraintViolationException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public class OrderRepoImpl implements OrderRepo {

    @Override
    public Orders save(Orders orders, Session session) throws ConstraintViolationException {
        try {
            session.save(orders);
            return orders;
        }catch (Exception e){
            e.printStackTrace();
            throw new ConstraintViolationException("Did not saved order");
        }
    }

    @Override
    public Orders update(Orders orders,Session session) throws ConstraintViolationException {
        try {
            session.update(orders);
            return orders;
        }catch (Exception e){
            throw new ConstraintViolationException("Did not update order");
        }
    }

    @Override
    public void delete(Orders orders,Session session) throws ConstraintViolationException {
        try {
            session.delete(orders);
        }catch (Exception e){
            throw new ConstraintViolationException("Did not delete order");
        }
    }

    @Override
    public List<Orders> findAll(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Orders> query = criteriaBuilder.createQuery(Orders.class);
        query.from(Orders.class);
        List<Orders> orders = session.createQuery(query).getResultList();
        session.close();
        return orders;
    }

    @Override
    public Optional<Orders> findByPk(String pk,Session session) {
        Orders orders = session.load(Orders.class, pk);
        session.close();
        return orders == null ? Optional.empty() : Optional.of(orders);
    }

    @Override
    public boolean existByPk(String pk,Session session) {
        Orders orders = session.load(Orders.class, pk);
        session.close();
        return orders != null;
    }

    @Override
    public long count(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Orders> query = criteriaBuilder.createQuery(Orders.class);
        query.from(Orders.class);
        int size = session.createQuery(query).getResultList().size();
        session.close();
        return size;
    }

    @Override
    public String getLastOrderId(Session session) {
        Query query = session.createQuery("FROM orders ORDER BY id DESC");
        query.setMaxResults(1);
        Orders orders = (Orders) query.uniqueResult();

        if(orders == null){
            return "D001";
        }
        return  orders.getOrderId();

    }
}
