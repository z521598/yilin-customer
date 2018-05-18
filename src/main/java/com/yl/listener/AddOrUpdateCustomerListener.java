package com.yl.listener;

import com.yl.common.db.DBUtils;
import com.yl.common.exception.DataFormatException;
import com.yl.model.Customer;
import com.yl.run.Main;
import com.yl.view.CustomerAorUFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 2018/5/17.
 */
public class AddOrUpdateCustomerListener implements ActionListener {

    private CustomerAorUFrame customerAorUFrame;

    public AddOrUpdateCustomerListener(CustomerAorUFrame customerAorUFrame) {
        this.customerAorUFrame = customerAorUFrame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            Customer customer = customerAorUFrame.checkAndgetCustomer();
            DBUtils.getCustomerDao().createOrUpdate(customer);
            JOptionPane.showMessageDialog(null, "操作成功");
            customerAorUFrame.dispose();
            Main.customerFrame.refreshCustomerTable();
        } catch (DataFormatException dfe) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, dfe.getMessage(), "操作失败", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "请检查数据是否合法", "操作失败", JOptionPane.ERROR_MESSAGE);
        }
    }
}
