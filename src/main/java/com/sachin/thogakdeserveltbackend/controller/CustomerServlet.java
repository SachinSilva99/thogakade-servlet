package com.sachin.thogakdeserveltbackend.controller;

import com.sachin.thogakdeserveltbackend.dto.CustomerDTO;
import com.sachin.thogakdeserveltbackend.regex.Validates;
import com.sachin.thogakdeserveltbackend.regex.Validation;
import com.sachin.thogakdeserveltbackend.service.ServiceFactory;
import com.sachin.thogakdeserveltbackend.service.ServiceType;
import com.sachin.thogakdeserveltbackend.service.custom.CustomerService;
import com.sachin.thogakdeserveltbackend.service.exception.DuplicateException;
import com.sachin.thogakdeserveltbackend.service.exception.NotFoundException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

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
    private final Validation validation = new Validation();
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

        if (!validation.match(customerDTO.getId(), Validates.CUSTOMER_ID)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!validation.match(customerDTO.getName(), Validates.NAME)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!validation.match(customerDTO.getAddress(), Validates.ADDRESS)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            customerService.save(customerDTO);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DuplicateException e) {
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

        if (!validation.match(customerDTO.getId(), Validates.CUSTOMER_ID)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!validation.match(customerDTO.getName(), Validates.NAME)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!validation.match(customerDTO.getAddress(), Validates.ADDRESS)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
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
