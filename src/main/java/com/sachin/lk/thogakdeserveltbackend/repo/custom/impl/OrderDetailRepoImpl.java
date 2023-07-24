package com.sachin.lk.thogakdeserveltbackend.repo.custom.impl;


import com.sachin.lk.thogakdeserveltbackend.entity.OrderDetail;
import com.sachin.lk.thogakdeserveltbackend.repo.custom.OrderDetailRepo;
import com.sachin.lk.thogakdeserveltbackend.repo.exception.ConstraintViolationException;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public class OrderDetailRepoImpl implements OrderDetailRepo {

    @Override
    public OrderDetail save(OrderDetail orderDetail, Session session) throws ConstraintViolationException {
        try {
            session.save(orderDetail);
            return orderDetail;
        } catch (Exception e) {
            throw new ConstraintViolationException("Did not saved order detail");
        }
    }

    @Override
    public OrderDetail update(OrderDetail orderDetail,Session session) throws ConstraintViolationException {
        try {
            session.update(orderDetail);
            return orderDetail;
        }catch (Exception e){
            throw new ConstraintViolationException("Did not update order detail");
        }
    }

    @Override
    public void delete(OrderDetail orderDetail,Session session) throws ConstraintViolationException {
        try {
            session.delete(orderDetail);
        }catch (Exception e){
            throw new ConstraintViolationException("Did not delete customer");
        }
    }

    @Override
    public List<OrderDetail> findAll(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<OrderDetail> query = criteriaBuilder.createQuery(OrderDetail.class);
        query.from(OrderDetail.class);
        List<OrderDetail> orderDetails = session.createQuery(query).getResultList();
        session.close();
        return orderDetails;

    }

    @Override
    public Optional<OrderDetail> findByPk(String pk,Session session) {
        OrderDetail orderDetail = session.load(OrderDetail.class, pk);
        session.close();
        return orderDetail == null ? Optional.empty() : Optional.of(orderDetail);
    }

    @Override
    public boolean existByPk(String pk,Session session) {
        OrderDetail orderDetail = session.load(OrderDetail.class, pk);
        session.close();
        return orderDetail != null;
    }

    @Override
    public long count(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<OrderDetail> query = criteriaBuilder.createQuery(OrderDetail.class);
        query.from(OrderDetail.class);
        int size = session.createQuery(query).getResultList().size();
        session.close();
        return size;
    }
}
