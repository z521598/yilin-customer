package com.yl.run;

import com.yl.common.db.DBUtils;
import com.yl.model.Customer;
import com.yl.view.CustomerFrame;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/5/17.
 */
public class Main {
    public static CustomerFrame customerFrame;

    public static void main(final String[] args) {
        String dbPath;
        // 初始化db
        if (args.length == 0) {
            dbPath = "customer.db";
        } else {
            dbPath = args[0];
        }
        try {
            DBUtils.init(dbPath);
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "数据库初始化失败", "初始化失败", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "未知原因数据库初始化失败", "初始化失败", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 渲染主页
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    customerFrame = new CustomerFrame();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "初始化主页失败", "初始化失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
