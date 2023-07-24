package com.sachin.lk.thogakdeserveltbackend.controller;

import com.sachin.lk.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.lk.thogakdeserveltbackend.service.ServiceFactory;
import com.sachin.lk.thogakdeserveltbackend.service.ServiceType;
import com.sachin.lk.thogakdeserveltbackend.service.custom.CustomerService;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
Author : Sachin Silva
*/
@WebServlet(name = "customer", value = "/customer")
public class CustomerServlet extends HttpServlet {
    private final CustomerService customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        customerService.save(customerDTO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            getAllCustomers(resp);
        } else {
            getCustomer(id, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            customerService.delete(req.getParameter("id"));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getCustomer(String id, HttpServletResponse resp) throws IOException {
        CustomerDTO customerDTO = customerService.search(id);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        jsonb.toJson(customerDTO, resp.getWriter());
    }

    private void getAllCustomers(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        jsonb.toJson(customerService.getAllCustomers(), resp.getWriter());
    }
}
