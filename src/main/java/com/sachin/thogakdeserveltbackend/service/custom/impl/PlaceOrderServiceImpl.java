package com.sachin.thogakdeserveltbackend.service.custom.impl;


import com.sachin.thogakdeserveltbackend.dto.CartDetailDTO;
import com.sachin.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.thogakdeserveltbackend.dto.OrderDTO;
import com.sachin.thogakdeserveltbackend.entity.Customer;
import com.sachin.thogakdeserveltbackend.entity.Item;
import com.sachin.thogakdeserveltbackend.entity.OrderDetail;
import com.sachin.thogakdeserveltbackend.entity.Orders;
import com.sachin.thogakdeserveltbackend.repo.RepoFactory;
import com.sachin.thogakdeserveltbackend.repo.RepoType;
import com.sachin.thogakdeserveltbackend.repo.custom.ItemRepo;
import com.sachin.thogakdeserveltbackend.repo.custom.OrderDetailRepo;
import com.sachin.thogakdeserveltbackend.repo.custom.OrderRepo;
import com.sachin.thogakdeserveltbackend.service.custom.PlaceOrderService;
import com.sachin.thogakdeserveltbackend.util.FactoryConfiguration;
import com.sachin.thogakdeserveltbackend.util.Mapper;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrderServiceImpl implements PlaceOrderService {
    private final OrderRepo orderRepo = RepoFactory.getInstance().getRepo(RepoType.ORDER);
    private final ItemRepo itemRepo = RepoFactory.getInstance().getRepo(RepoType.ITEM);
    private final OrderDetailRepo orderDetailRepo = RepoFactory.getInstance().getRepo(RepoType.ORDER_DETAIL);

    private final Mapper mapper = new Mapper();
    @Override
    public boolean placeOrder(OrderDTO orderDTO, ArrayList<CartDetailDTO> cartDetailDTOS, CustomerDTO customerDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<OrderDetail> orderDetails = new ArrayList<>();
        Orders orders = mapper.toOrders(orderDTO);
        try {

            for (CartDetailDTO cartDetailDTO : cartDetailDTOS) {
                Item item = mapper.toItem(cartDetailDTO.getItemDTO());
                OrderDetail od = new OrderDetail(orders, item, cartDetailDTO.getQty(), cartDetailDTO.getUnitPrice());
                orderDetails.add(od);
            }

            orders.setOrderDetails(orderDetails);
            Customer customer = mapper.toCustomer(customerDTO);
            List<Orders> orders1 = new ArrayList<>();
            orders1.add(orders);
            orders.setCustomer(customer);
            customer.setOrders(orders1);
            orderRepo.save(orders, session);
            for (OrderDetail od : orderDetails) {
                od.getItem().setOrderDetails(orderDetails);
                orderDetailRepo.save(od, session);
            }
            orderDetails.forEach(orderDetail -> itemRepo.update(
                    new Item(orderDetail.getItem().getCode(),
                            orderDetail.getItem().getDescription(),
                            orderDetail.getUnitPrice(),
                            ( orderDetail.getItem().getQtyOnHand()- orderDetail.getQty()),orderDetails)
                    ,session
            ));
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
        return true;
    }
}
