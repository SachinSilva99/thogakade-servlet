package com.sachin.thogakdeserveltbackend.controller;

import com.sachin.thogakdeserveltbackend.dto.ItemDTO;
import com.sachin.thogakdeserveltbackend.regex.Validates;
import com.sachin.thogakdeserveltbackend.regex.Validation;
import com.sachin.thogakdeserveltbackend.service.ServiceFactory;
import com.sachin.thogakdeserveltbackend.service.ServiceType;
import com.sachin.thogakdeserveltbackend.service.custom.ItemService;
import com.sachin.thogakdeserveltbackend.service.exception.FailedToDelete;
import com.sachin.thogakdeserveltbackend.service.exception.NotFoundException;
import com.sachin.thogakdeserveltbackend.service.exception.UpdateFailedException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
Author : Sachin Silva
*/
@WebServlet(name = "item", value = "/item")
public class ItemServlet extends HttpServlet {
    private final ItemService itemService = ServiceFactory.getInstance().getService(ServiceType.ITEM);
    private final Validation validation = new Validation();
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");
        String data = req.getParameter("search-data");
        if (code != null) {
            ItemDTO itemDTO = itemService.search(code);
            if (itemDTO == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                jsonb.toJson(itemDTO, resp.getWriter());
            }
        } else if (data != null) {
            List<ItemDTO> itemDTOS = itemService.searchItems(data);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            jsonb.toJson(itemDTOS, resp.getWriter());
        } else {
            List<ItemDTO> itemDTOS = itemService.getAllItems();
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            jsonb.toJson(itemDTOS, resp.getWriter());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
        if (!validation.match(itemDTO.getCode(), Validates.ITEM_CODE)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!validation.match(itemDTO.getDescription(), Validates.ITEM_DESCRIPTION)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!validation.match(String.valueOf(itemDTO.getQtyOnHand()), Validates.QTY)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (!validation.match(String.valueOf(itemDTO.getUnitPrice()), Validates.PRICE)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            itemService.save(itemDTO);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("application/json");
            jsonb.toJson(itemDTO, resp.getWriter());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
        try {
            itemService.update(itemDTO);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            jsonb.toJson(itemDTO, resp.getWriter());
        } catch (UpdateFailedException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String code = req.getParameter("code");
        try {
            itemService.delete(code);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (FailedToDelete e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
