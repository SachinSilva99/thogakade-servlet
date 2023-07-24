package com.sachin.lk.thogakdeserveltbackend.service.custom.impl;


import com.sachin.lk.thogakdeserveltbackend.repo.RepoFactory;
import com.sachin.lk.thogakdeserveltbackend.repo.RepoType;
import com.sachin.lk.thogakdeserveltbackend.repo.custom.OrderRepo;
import com.sachin.lk.thogakdeserveltbackend.service.custom.OrderService;
import com.sachin.lk.thogakdeserveltbackend.util.FactoryConfiguration;
import org.hibernate.Session;

public class OrderServiceImpl implements OrderService {
    OrderRepo orderRepo = RepoFactory.getInstance().getRepo(RepoType.ORDER);
    @Override
    public String generateNextOrderId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        return orderRepo.getLastOrderId(session);
    }

    @Override
    public String generateNextOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("D0");
            int id = Integer.parseInt(split[1]);
            id += 1;
            return "D0" + id;
        }
        return "O001";
    }
}
