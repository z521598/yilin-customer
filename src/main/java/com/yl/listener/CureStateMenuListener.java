package com.yl.listener;

import com.yl.common.db.DBUtils;
import com.yl.model.CureState;
import com.yl.model.Customer;
import com.yl.view.CureStateAorUFrame;
import com.yl.view.CureStateFrame;
import com.yl.view.CustomerAorUFrame;

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
public class CureStateMenuListener implements MouseListener {
    JTable customerTable;
    CureStateFrame cureStateFrame;

    public CureStateMenuListener(JTable customerTable, CureStateFrame cureStateFrame) {
        this.customerTable = customerTable;
        this.cureStateFrame = cureStateFrame;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if (evt.getButton() == MouseEvent.BUTTON3) { // BUTTON3为鼠标右键
            // 选中的行
            final int focusedRowIndex = customerTable.rowAtPoint(evt.getPoint());
            // 选中的列
            final int focusColumnIndex = customerTable.columnAtPoint(evt.getPoint());
            if (focusedRowIndex == -1) {
                return;
            }
            // 将表格所选项设为当前右键点击的行.
            customerTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
            final Long id = (Long) customerTable.getValueAt(focusedRowIndex, 0);

            // 右键菜单
            final JPopupMenu customerTableMenu = new JPopupMenu();
            JMenuItem updateMenuItem = new JMenuItem();
            updateMenuItem.setText("修改");
            // 修改弹窗
            updateMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        final CureState cureState = DBUtils.getCureStateDao().queryForId(id);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                new CureStateAorUFrame(cureState, cureStateFrame);
                            }
                        });
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, "打开失败", "操作失败", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            customerTableMenu.add(updateMenuItem);
            // 弹出菜单
            customerTableMenu.show(customerTable, evt.getX(), evt.getY());
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
