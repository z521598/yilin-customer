package com.yl.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.yl.common.db.DBUtils;
import com.yl.model.Customer;

import java.sql.SQLException;

/**
 * Created by Administrator on 2018/5/17.
 */
public class CustomerDao {
    Dao<Customer, Long> customerDao;

    public CustomerDao() throws SQLException {
        customerDao = DaoManager.createDao(DBUtils.getConnectionSource(), Customer.class);
    }

    public void addCustomer(Customer customer) throws SQLException {
        customerDao.create(customer);
    }

}
