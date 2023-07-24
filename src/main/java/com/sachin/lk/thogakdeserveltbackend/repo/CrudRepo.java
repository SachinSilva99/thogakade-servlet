package com.sachin.lk.thogakdeserveltbackend.repo;


import com.sachin.lk.thogakdeserveltbackend.entity.SuperEntity;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.Optional;

public interface CrudRepo<T extends SuperEntity,ID> extends SuperRepo {
    T save(T t, Session session) throws ConstraintViolationException;

    T update(T t, Session session)throws ConstraintViolationException;

    void delete(T t, Session session)throws ConstraintViolationException;

    List<T> findAll(Session session) ;

    Optional<T> findByPk(ID pk,Session session);

    boolean existByPk(ID pk,Session session);

    long count(Session session) ;
}