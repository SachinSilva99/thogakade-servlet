package com.sachin.lk.thogakdeserveltbackend.controller;

import com.sachin.lk.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.lk.thogakdeserveltbackend.service.ServiceFactory;
import com.sachin.lk.thogakdeserveltbackend.service.ServiceType;
import com.sachin.lk.thogakdeserveltbackend.service.custom.CustomerService;
import com.sachin.lk.thogakdeserveltbackend.service.exception.DuplicateException;
import com.sachin.lk.thogakdeserveltbackend.service.exception.NotFoundException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.hibernate.exception.ConstraintViolationException;

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
        try {
            customerService.save(customerDTO);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        }catch (DuplicateException e){
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String search = req.getParameter("search-data");
        if (id != null) {
            getCustomer(id, resp);
        } else if (search != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            jsonb.toJson(customerService.searchCustomers(search), resp.getWriter());
        } else {
            getAllCustomers(resp);
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
        try {
            CustomerDTO updatedCustomerDto = customerService.update(customerDTO);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            jsonb.toJson(updatedCustomerDto, resp.getWriter());

        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            customerService.delete(req.getParameter("id"));
            resp.setStatus(HttpServletResponse.SC_OK);
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
