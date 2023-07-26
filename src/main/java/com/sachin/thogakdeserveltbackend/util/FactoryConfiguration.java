package com.sachin.thogakdeserveltbackend.util;


import com.sachin.thogakdeserveltbackend.entity.Customer;
import com.sachin.thogakdeserveltbackend.entity.Item;
import com.sachin.thogakdeserveltbackend.entity.OrderDetail;
import com.sachin.thogakdeserveltbackend.entity.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FactoryConfiguration {
    public static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {
        try {
            InitialContext context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/hibernate");

            Configuration configuration = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Item.class)
                    .addAnnotatedClass(Orders.class)
                    .addAnnotatedClass(OrderDetail.class);

            configuration.setProperty("hibernate.connection.datasource", "java:comp/env/jdbc/hibernate");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .applySetting("hibernate.connection.datasource", dataSource)
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (NamingException ex) {
            throw new RuntimeException("Error configuring Hibernate with JNDI DataSource.", ex);
        }

    }
    public static FactoryConfiguration getInstance() {
        return factoryConfiguration == null ?
                factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
