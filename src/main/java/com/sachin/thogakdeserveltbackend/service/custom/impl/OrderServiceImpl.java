package com.sachin.thogakdeserveltbackend.service.custom.impl;


import com.sachin.thogakdeserveltbackend.dto.OrderDTO;
import com.sachin.thogakdeserveltbackend.dto.OrderDetailDTO;
import com.sachin.thogakdeserveltbackend.repo.RepoFactory;
import com.sachin.thogakdeserveltbackend.repo.RepoType;
import com.sachin.thogakdeserveltbackend.repo.custom.OrderDetailRepo;
import com.sachin.thogakdeserveltbackend.repo.custom.OrderRepo;
import com.sachin.thogakdeserveltbackend.service.custom.OrderService;
import com.sachin.thogakdeserveltbackend.util.FactoryConfiguration;
import com.sachin.thogakdeserveltbackend.util.Mapper;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    OrderRepo orderRepo = RepoFactory.getInstance().getRepo(RepoType.ORDER);
    OrderDetailRepo orderDetailRepo = RepoFactory.getInstance().getRepo(RepoType.ORDER_DETAIL);
    private final Mapper mapper = new Mapper();

    @Override
    public String getLastOrderId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        return orderRepo.getLastOrderId(session);
    }

    @Override
    public List<OrderDetailDTO> getAllOrderDetails() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<OrderDetailDTO> orderDetailDTOS = orderDetailRepo
                .findAll(session)
                .stream()
                .map(od -> mapper.toOrderDetailDto(od)).collect(Collectors.toList());
        session.close();
        return orderDetailDTOS;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<OrderDTO> orderDTOS = orderRepo.findAll(session).stream().map(orders -> mapper.toOrderDto(orders)).collect(Collectors.toList());
        session.close();
        return orderDTOS;
    }
}
