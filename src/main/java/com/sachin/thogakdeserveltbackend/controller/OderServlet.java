package com.sachin.thogakdeserveltbackend.controller;

import com.sachin.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.thogakdeserveltbackend.dto.OrderDTO;
import com.sachin.thogakdeserveltbackend.dto.OrderDetailDTO;
import com.sachin.thogakdeserveltbackend.dto.PlaceOrderDTO;
import com.sachin.thogakdeserveltbackend.service.ServiceFactory;
import com.sachin.thogakdeserveltbackend.service.ServiceType;
import com.sachin.thogakdeserveltbackend.service.custom.OrderService;
import com.sachin.thogakdeserveltbackend.service.custom.PlaceOrderService;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/*
Author : Sachin Silva
*/
@WebServlet(name = "order", value = "/order")
public class OderServlet extends HttpServlet {
    private final OrderService orderService = ServiceFactory.getInstance().getService(ServiceType.ORDER);
    private final PlaceOrderService placeOrderService = ServiceFactory.getInstance().getService(ServiceType.PLACE_ORDER);
    private final Jsonb jsonb = JsonbBuilder.create();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String includeDetailsParam = req.getParameter("includeDetails");
        boolean shouldIncludeDetails = "true".equalsIgnoreCase(includeDetailsParam);
        String lastOrder = req.getParameter("lastOrder");
        boolean isLastOrder = "true".equalsIgnoreCase(lastOrder);

        if (shouldIncludeDetails) {
            List<OrderDetailDTO> orderDetails = orderService.getAllOrderDetails();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson(orderDetails));
        } else if (isLastOrder) {
            String lastOrderId = orderService.getLastOrderId();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson(lastOrderId));
            System.out.println("is last order");
        } else {
            List<OrderDTO> orderDTOS = orderService.getAllOrders();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonb.toJson(orderDTOS));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PlaceOrderDTO placeOrderDTO = jsonb.fromJson(req.getReader(), PlaceOrderDTO.class);
        CustomerDTO customerDto = placeOrderDTO.getCustomerDto();
        OrderDTO orderDTO = new OrderDTO(placeOrderDTO.getOrderId(), new Date(), customerDto.getId());
        boolean placed = placeOrderService.placeOrder(orderDTO, placeOrderDTO.getCartDetailDTOS(), placeOrderDTO.getCustomerDto());
        if (placed) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            resp.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
        }
    }
}
