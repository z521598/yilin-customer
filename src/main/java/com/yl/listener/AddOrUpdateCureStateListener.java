package com.yl.listener;

import com.yl.common.db.DBUtils;
import com.yl.common.exception.DataFormatException;
import com.yl.model.CureState;
import com.yl.model.Customer;
import com.yl.view.CureStateAorUFrame;
import com.yl.view.CureStateFrame;
import com.yl.view.CustomerAorUFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 2018/5/17.
 */
public class AddOrUpdateCureStateListener implements ActionListener {

    private CureStateAorUFrame checkAndgetCustomer;

    private CureStateFrame cureStateFrame;

    public AddOrUpdateCureStateListener(CureStateAorUFrame checkAndgetCustomer, CureStateFrame cureStateFrame) {
        this.checkAndgetCustomer = checkAndgetCustomer;
        this.cureStateFrame = cureStateFrame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            CureState cureState = checkAndgetCustomer.checkAndgetCureState();
            DBUtils.getCureStateDao().createOrUpdate(cureState);
            JOptionPane.showMessageDialog(null, "操作成功");
            checkAndgetCustomer.dispose();
            cureStateFrame.refreshCureStateTable();
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
