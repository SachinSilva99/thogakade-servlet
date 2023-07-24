package com.sachin.lk.thogakdeserveltbackend.repo;

import lk.ijse.thogakade.repo.custom.impl.CustomerRepoImpl;
import lk.ijse.thogakade.repo.custom.impl.ItemRepoImpl;
import lk.ijse.thogakade.repo.custom.impl.OrderDetailRepoImpl;
import lk.ijse.thogakade.repo.custom.impl.OrderRepoImpl;

public class RepoFactory {
    private static RepoFactory repoFactory = new RepoFactory();
    private RepoFactory(){}
    public static RepoFactory getInstance(){
        return repoFactory;
    }
    public <T extends SuperRepo>T getRepo(RepoType repoType){
        switch (repoType){
            case CUSTOMER:
                return (T) new CustomerRepoImpl();

            case ITEM:
                return (T) new ItemRepoImpl();

            case ORDER:
                return (T) new OrderRepoImpl();

            case ORDER_DETAIL:
                return (T) new OrderDetailRepoImpl();

            default:
                return null;
        }
    }
}
