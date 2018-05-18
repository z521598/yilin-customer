package com.yl.listener;

import com.j256.ormlite.dao.Dao;
import com.yl.common.db.DBUtils;
import com.yl.common.utils.SpellUtils;
import com.yl.common.utils.DataUtils;
import com.yl.model.CureState;
import com.yl.model.Customer;
import com.yl.run.Main;
import com.yl.view.CureStateFrame;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 */
public class QueryListener implements ActionListener {
    private Dao<Customer, Long> customerDao = DBUtils.getCustomerDao();
    private Dao<CureState, Long> cureStateDao = DBUtils.getCureStateDao();

    private JTextField queryTextField;

    public QueryListener(JTextField queryTextField) {
        this.queryTextField = queryTextField;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            String str = queryTextField.getText().trim();
            if (StringUtils.isBlank(str)) {
                Main.customerFrame.refreshCustomerTable();
                return;
            }
            str = str.trim();

            List<Customer> customerList = null;
            if (SpellUtils.isChinese(str)) { // 汉字(完整姓名)
                customerList = customerDao.queryForEq("name", str);
            } else if (DataUtils.isNumber(str)) { // 数字（完整或者手机号）
                customerList = customerDao.queryBuilder().where().like("phoneNumber", "%" + str).query();
            } else if (DataUtils.isEnglish(str)) { // 英文（全拼或者拼音首字母）
                customerList = customerDao.queryBuilder().where().eq("englishName", str).or().eq("headEnglishName", str).query();
            } else {
                JOptionPane.showMessageDialog(null, "支持纯中文，纯英文，纯数字，请检查", "输入异常", JOptionPane.ERROR_MESSAGE);
            }
            if (customerList == null || customerList.size() == 0) {
                JOptionPane.showMessageDialog(null, "查无此人", "查无此人", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (customerList.size() == 1) {
                Customer customer = customerList.get(0);
                List<CureState> cureStateList = DBUtils.queryCureState(customer.getId());
                new CureStateFrame(customer, cureStateList);
                return;
            } else {
                Main.customerFrame.updateCustomerTable(customerList);
            }

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "查询失败", "初始化失败", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "未知原因，查询失败", "初始化失败", JOptionPane.ERROR_MESSAGE);
        }

    }
}
