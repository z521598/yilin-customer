import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yl.model.Customer;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/25.
 */
public class DbTest {

    @Test
    public void testName() throws Exception {
//        Class.forName("org.sqlite.JDBC");
        String jdbcUrl = "jdbc:sqlite:c.db";
        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(jdbcUrl);
        connectionSource.setTestBeforeGet(true);
        connectionSource.setMaxConnectionAgeMillis(5 * 60 * 1000);

        TableUtils.createTableIfNotExists(connectionSource, Customer.class);
        Dao<Customer, Long> customerDao = DaoManager.createDao(connectionSource, Customer.class);
        Customer customer = new Customer();
        customer.setSex("男");
        customer.setName("郎世权");
        customer.setPhoneNumber("15754308614");
        customerDao.create(customer);

        Customer customer1 = customerDao.queryForId(1L);

    }
}
