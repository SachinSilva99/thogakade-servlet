package com.sachin.thogakdeserveltbackend.service.custom.impl;


import com.sachin.thogakdeserveltbackend.dto.ItemDTO;
import com.sachin.thogakdeserveltbackend.entity.Item;
import com.sachin.thogakdeserveltbackend.repo.RepoFactory;
import com.sachin.thogakdeserveltbackend.repo.RepoType;
import com.sachin.thogakdeserveltbackend.repo.custom.ItemRepo;
import com.sachin.thogakdeserveltbackend.service.custom.ItemService;
import com.sachin.thogakdeserveltbackend.service.exception.DuplicateException;
import com.sachin.thogakdeserveltbackend.service.exception.FailedToDelete;
import com.sachin.thogakdeserveltbackend.service.exception.NotFoundException;
import com.sachin.thogakdeserveltbackend.service.exception.UpdateFailedException;
import com.sachin.thogakdeserveltbackend.util.FactoryConfiguration;
import com.sachin.thogakdeserveltbackend.util.Mapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

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

    @Override
    public void save(ItemDTO itemDTO) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            itemRepo.save(mapper.toItem(itemDTO), session);
            transaction.commit();
        } catch (ConstraintViolationException e) {
            transaction.rollback();
            throw new DuplicateException("Item already exists");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Item did not saved");
        } finally {
            session.close();
        }
    }

    @Override
    public List<ItemDTO> searchItems(String data) {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<ItemDTO> itemDTOList = itemRepo.findAll(session)
                .stream()
                .filter(item -> item.getCode().contains(data) || item.getDescription().contains(data))
                .map(item -> mapper.toItemDTO(item)).collect(Collectors.toList());
        session.close();
        return itemDTOList;
    }

    @Override
    public List<ItemDTO> getAllItems() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<ItemDTO> itemDTOList = itemRepo.findAll(session)
                .stream()
                .map(item -> mapper.toItemDTO(item)).collect(Collectors.toList());
        session.close();
        return itemDTOList;
    }

    @Override
    public void update(ItemDTO itemDTO) throws NotFoundException, UpdateFailedException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Optional<Item> byPk = itemRepo.findByPk(itemDTO.getCode(), session);
            if (byPk.isPresent()) {
                Item item = byPk.get();
                item.setDescription(itemDTO.getDescription());
                item.setUnitPrice(itemDTO.getUnitPrice());
                item.setQtyOnHand(itemDTO.getQtyOnHand());
                itemRepo.update(item, session);
                transaction.commit();
            } else {
                throw new NotFoundException(itemDTO.getCode() + " not found");
            }
        } catch (Exception e) {
            throw new UpdateFailedException();
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(String code) throws NotFoundException, FailedToDelete {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Optional<Item> byPk = itemRepo.findByPk(code, session);
            if (byPk.isPresent()) {
                Item item = byPk.get();
                itemRepo.delete(item, session);
                transaction.commit();
            } else {
                throw new NotFoundException(code + " not found");
            }
        } catch (Exception e) {
            throw new FailedToDelete();
        }finally {
            session.close();
        }
    }
}
