package com.yl.common.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yl.model.Customer;
import com.yl.model.CureState;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by Administrator on 2018/5/17.
 */
public class DBUtils {
    private static Dao<Customer, Long> customerDao;
    private static Dao<CureState, Long> cureStateDao;
    private static JdbcPooledConnectionSource connectionSource;

    public static void init(String dbPath) throws SQLException {
        String jdbcUrl = "jdbc:sqlite:" + dbPath;
        connectionSource = new JdbcPooledConnectionSource(jdbcUrl);
        connectionSource.setTestBeforeGet(true);
        connectionSource.setMaxConnectionAgeMillis(5 * 60 * 1000);
        initTable();
        initDao();
    }

    private static void initTable() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, Customer.class);
        TableUtils.createTableIfNotExists(connectionSource, CureState.class);
    }

    private static void initDao() throws SQLException {
        customerDao = DaoManager.createDao(connectionSource, Customer.class);
        cureStateDao = DaoManager.createDao(connectionSource, CureState.class);
    }

    public static Dao<Customer, Long> getCustomerDao() {
        return customerDao;
    }

    public static Dao<CureState, Long> getCureStateDao() {
        return cureStateDao;
    }

    public static JdbcPooledConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public static List<CureState> queryCureState(Long customerId) throws SQLException {
        return cureStateDao.queryBuilder().orderBy("date", false).where().eq("customerId", customerId).query();
    }
}
