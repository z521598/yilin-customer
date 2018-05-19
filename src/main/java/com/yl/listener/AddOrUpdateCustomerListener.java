package com.yl.listener;

import com.yl.common.db.DBUtils;
import com.yl.common.exception.DataFormatException;
import com.yl.model.Customer;
import com.yl.run.Main;
import com.yl.view.CustomerAorUFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 2018/5/17.
 */
public class AddOrUpdateCustomerListener implements ActionListener {

    private Logger log = LoggerFactory.getLogger(AddOrUpdateCustomerListener.class);

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
        } catch (NumberFormatException ne) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "身高、体重或摄入卡路里必须为数字", "操作失败", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            log.error("add Customer failed:", e);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "请检查数据是否合法", "操作失败", JOptionPane.ERROR_MESSAGE);
        }
    }
}
