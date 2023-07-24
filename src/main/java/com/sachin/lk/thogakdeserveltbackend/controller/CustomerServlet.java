package com.sachin.lk.thogakdeserveltbackend.controller;

import com.sachin.lk.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.lk.thogakdeserveltbackend.service.ServiceFactory;
import com.sachin.lk.thogakdeserveltbackend.service.ServiceType;
import com.sachin.lk.thogakdeserveltbackend.service.SuperService;
import com.sachin.lk.thogakdeserveltbackend.service.custom.CustomerService;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
Author : Sachin Silva
*/
@WebServlet(name = "customer", value = "/customer")
public class CustomerServlet extends HttpServlet {
    private final CustomerService cs = ServiceFactory.getInstance().getService(ServiceType.CUSTOMER);
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        cs.save(customerDTO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getAllCustomers(resp);
    }

    private void getAllCustomers(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        jsonb.toJson(cs.getAllCustomers(), resp.getWriter());
    }
}
