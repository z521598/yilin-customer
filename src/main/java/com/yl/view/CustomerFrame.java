package com.yl.view;

import com.yl.common.db.DBUtils;
import com.yl.common.utils.DataUtils;
import com.yl.common.utils.SwingUtils;
import com.yl.listener.CustomerMenuListener;
import com.yl.listener.QueryListener;
import com.yl.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;


/**
 * Created by Administrator on 2018/4/25.
 */
public class CustomerFrame extends JFrame {

    private Logger log = LoggerFactory.getLogger(CustomerFrame.class);

    private final static int PAGE_SIZE = 37;
    private int currentOffset = 0;
    private JPanel topPanel = new JPanel();
    private JTextField searchField = new JTextField();
    private JButton searchButton = new JButton("查询");
    private JButton addCustomerButton = new JButton("添加顾客");

    private JPanel middlePanel = new JPanel();
    private JTable customerTable;
    private JScrollPane customerPane;

    private JPanel bottomPanel = new JPanel();
    private JButton lastButton = new JButton("上一页");
    private JButton nextButton = new JButton("下一页");

    public CustomerFrame() throws SQLException {
        setLayout(new BorderLayout());
        searchButton.addActionListener(new QueryListener(searchField));
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerAorUFrame(new Customer());
            }
        });
        topPanel.setLayout(new GridLayout(1, 5, 50, 10));
        topPanel.add(new JLabel());
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(addCustomerButton);
        topPanel.add(new JLabel());
        add(topPanel, BorderLayout.NORTH);
        SwingUtils.enterPressesWhenFocused(searchField, new QueryListener(searchField));

        customerPane = new JScrollPane(customerTable);
        List<Customer> customerList = DBUtils.getCustomerDao().queryBuilder().offset(currentOffset).limit(PAGE_SIZE).query();
        currentOffset = PAGE_SIZE;
        updateCustomerTable(customerList);
        add(middlePanel, BorderLayout.CENTER);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    final List<Customer> customerList = DBUtils.getCustomerDao().queryBuilder().offset(currentOffset).limit(currentOffset + PAGE_SIZE).query();
                    if (customerList.size() == 0) {
                        JOptionPane.showMessageDialog(null, "已经是最后一页了");
                        return;
                    }
                    currentOffset += PAGE_SIZE;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            updateCustomerTable(customerList);
                        }
                    });
                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (Exception e) {

                }
            }
        });
        lastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    if (currentOffset <= PAGE_SIZE) {
                        JOptionPane.showMessageDialog(null, "已经是第一页了");
                        return;
                    }
                    currentOffset -= PAGE_SIZE;
                    final List<Customer> customerList = DBUtils.getCustomerDao().queryBuilder().offset(currentOffset - PAGE_SIZE).limit(currentOffset).query();
                    if (customerList.size() == 0) {
                        JOptionPane.showMessageDialog(null, "没有数据了");
                        return;
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            updateCustomerTable(customerList);
                        }
                    });
                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (Exception e) {

                }
            }
        });
        bottomPanel.setLayout(new GridLayout(1, 5, 50, 10));
        bottomPanel.add(new JLabel());
        bottomPanel.add(lastButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(new JLabel());
        add(bottomPanel, BorderLayout.SOUTH);


        setTitle("艺霖正骨按摩");
        setExtendedState(JFrame.MAXIMIZED_BOTH);   //最大化
        Dimension screenSize = Toolkit.getDefaultToolkit()
                .getScreenSize();
        Rectangle bounds = new Rectangle(screenSize);
        Insets insets = Toolkit.getDefaultToolkit()
                .getScreenInsets(this.getGraphicsConfiguration());
        bounds.x += insets.left;
        bounds.y += insets.top;
        bounds.width -= insets.left + insets.right;
        bounds.height -= insets.top + insets.bottom;
        setBounds(bounds);
        setResizable(false);         //不能改变大小
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateCustomerTable(List<Customer> customerList) {
        middlePanel.remove(customerPane);
        middlePanel.setVisible(false);
        customerTable = new JTable(DataUtils.convertCustomersToArray(customerList), Customer.tableColumns) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        customerTable.setPreferredScrollableViewportSize(new Dimension(1000, 600));

        customerTable.addMouseListener(new CustomerMenuListener(customerTable));
        customerPane = new JScrollPane(customerTable);
        middlePanel.add(customerPane);
        middlePanel.setVisible(true);
    }

    public void refreshCustomerTable() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                List<Customer> customerList = null;
                try {
                    customerList = DBUtils.getCustomerDao().queryBuilder().offset(0).limit(PAGE_SIZE).query();
                    updateCustomerTable(customerList);
                } catch (SQLException e) {
                    log.error("refreshCustomerTable failed:", e);
                } catch (Exception e) {
                    log.error("refreshCustomerTable failed:", e);
                }
            }
        });

    }
}
