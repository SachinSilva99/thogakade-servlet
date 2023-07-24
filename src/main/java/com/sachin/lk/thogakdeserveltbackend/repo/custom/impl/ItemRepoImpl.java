package com.sachin.lk.thogakdeserveltbackend.repo.custom.impl;


import com.sachin.lk.thogakdeserveltbackend.entity.Item;
import com.sachin.lk.thogakdeserveltbackend.repo.custom.ItemRepo;
import com.sachin.lk.thogakdeserveltbackend.repo.exception.ConstraintViolationException;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public class ItemRepoImpl implements ItemRepo {

    @Override
    public Item save(Item item, Session session) throws ConstraintViolationException {
        try {
            session.save(item);
            return item;
        }catch (Exception e){
            throw new ConstraintViolationException("Item did not save");
        }
    }

    @Override
    public Item update(Item item,Session session) throws ConstraintViolationException {
        try {
            session.update(item);
            return item;
        }catch (Exception e){
            throw new ConstraintViolationException("Did not update item");
        }
    }

    @Override
    public void delete(Item item,Session session) throws ConstraintViolationException {
        try {
            session.delete(item);
        }catch (Exception e){
            throw new ConstraintViolationException("Did not delete item");
        }
    }

    @Override
    public List<Item> findAll(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Item> query = criteriaBuilder.createQuery(Item.class);
        query.from(Item.class);
        List<Item> items = session.createQuery(query).getResultList();
        session.close();
        return items;
    }

    @Override
    public Optional<Item> findByPk(String pk,Session session) {
        Item item = session.load(Item.class, pk);
        session.close();
        return item == null ? Optional.empty() : Optional.of(item);
    }

    @Override
    public boolean existByPk(String pk,Session session) {
        Item item = session.load(Item.class, pk);
        session.close();
        return item != null;
    }

    @Override
    public long count(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Item> query = criteriaBuilder.createQuery(Item.class);
        query.from(Item.class);
        int size = session.createQuery(query).getResultList().size();
        session.close();
        return size;
    }
}
