package com.sachin.lk.thogakdeserveltbackend.service.custom.impl;


import com.sachin.lk.thogakdeserveltbackend.dto.ItemDTO;
import com.sachin.lk.thogakdeserveltbackend.entity.Item;
import com.sachin.lk.thogakdeserveltbackend.repo.RepoFactory;
import com.sachin.lk.thogakdeserveltbackend.repo.RepoType;
import com.sachin.lk.thogakdeserveltbackend.repo.custom.ItemRepo;
import com.sachin.lk.thogakdeserveltbackend.service.custom.ItemService;
import com.sachin.lk.thogakdeserveltbackend.util.FactoryConfiguration;
import com.sachin.lk.thogakdeserveltbackend.util.Mapper;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService {
    private final ItemRepo itemRepo = RepoFactory.getInstance().getRepo(RepoType.ITEM);
    private final Mapper mapper = new Mapper();


    @Override
    public List<String> loadItemCodes() {
        Session session = FactoryConfiguration.getInstance().getSession();
        return itemRepo.findAll(session).stream().map(Item::getCode).collect(Collectors.toList());
    }

    @Override
    public ItemDTO search(String code) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Optional<Item> byPk = itemRepo.findByPk(code, session);
        return byPk.map(mapper::toItemDTO).orElse(null);
    }


}
