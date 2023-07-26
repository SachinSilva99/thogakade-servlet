package com.sachin.thogakdeserveltbackend.repo;


import com.sachin.thogakdeserveltbackend.repo.custom.impl.CustomerRepoImpl;
import com.sachin.thogakdeserveltbackend.repo.custom.impl.ItemRepoImpl;
import com.sachin.thogakdeserveltbackend.repo.custom.impl.OrderDetailRepoImpl;
import com.sachin.thogakdeserveltbackend.repo.custom.impl.OrderRepoImpl;

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
