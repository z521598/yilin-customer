package com.yl.listener;

import com.yl.common.db.DBUtils;
import com.yl.model.CureState;
import com.yl.model.Customer;
import com.yl.view.CustomerAorUFrame;
import com.yl.view.CureStateFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 */
public class CustomerMenuListener implements MouseListener {
    JTable customerTable;

    public CustomerMenuListener(JTable customerTable) {
        this.customerTable = customerTable;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        // 选中的行
        final int focusedRowIndex = customerTable.rowAtPoint(evt.getPoint());
        if (focusedRowIndex == -1) {
            return;
        }
        // 将表格所选项设为当前右键点击的行.
        customerTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
        final Long id = (Long) customerTable.getValueAt(focusedRowIndex, 0);


        if (evt.getButton() == MouseEvent.BUTTON3) { // 鼠标右键事件

            // 右键菜单
            final JPopupMenu customerTableMenu = new JPopupMenu();
            JMenuItem queryMenuItem = new JMenuItem();
            queryMenuItem.setText("查看");
            // 查看弹窗
            queryMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Customer customer = DBUtils.getCustomerDao().queryForId(id);
                                List<CureState> cureStateList = DBUtils.queryCureState(id);
                                new CureStateFrame(customer, cureStateList);
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, "打开失败", "操作失败", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                }
            });
            JMenuItem updateMenuItem = new JMenuItem();
            updateMenuItem.setText("修改");
            // 修改弹窗
            updateMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        final Customer customer = DBUtils.getCustomerDao().queryForId(id);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                new CustomerAorUFrame(customer);
                            }
                        });
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "打开失败", "操作失败", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            customerTableMenu.add(queryMenuItem);
            customerTableMenu.add(updateMenuItem);
            // 弹出菜单
            customerTableMenu.show(customerTable, evt.getX(), evt.getY());
        } else if (evt.getClickCount() >= 2) { // 双击事件
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Customer customer = DBUtils.getCustomerDao().queryForId(id);
                        List<CureState> cureStateList = DBUtils.queryCureState(id);
                        new CureStateFrame(customer, cureStateList);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "打开失败", "操作失败", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
