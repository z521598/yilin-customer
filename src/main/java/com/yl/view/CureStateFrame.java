package com.yl.view;


import com.yl.common.db.DBUtils;
import com.yl.common.swing.TableViewRenderer;
import com.yl.common.utils.DataUtils;
import com.yl.listener.CureStateMenuListener;
import com.yl.listener.OpenAddCureStateListener;
import com.yl.model.CureState;
import com.yl.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */
public class CureStateFrame extends JFrame {

    private Logger log = LoggerFactory.getLogger(CureStateFrame.class);

    private JPanel customerPanel = new JPanel();
    private Long customerId;

    private JPanel sexPanel = new JPanel();
    private JLabel sexLabel = new JLabel("性别：");
    private JLabel sex = new JLabel();

    private JPanel agePanel = new JPanel();
    private JLabel ageLabel = new JLabel("年龄：");
    private JLabel age = new JLabel();

    private JPanel heightPanel = new JPanel();
    private JLabel heightLabel = new JLabel("身高：");
    private JLabel height = new JLabel();
    private JLabel cmUnitLabel = new JLabel("cm");

    private JPanel weightPanel = new JPanel();
    private JLabel weightLabel = new JLabel("体重：");
    private JLabel weight = new JLabel();
    private JLabel kgUnitLabel = new JLabel("kg");

    private JPanel phoneNumberPanel = new JPanel();
    private JLabel phoneNumberLabel = new JLabel("电话号：");
    private JLabel phoneNumber = new JLabel();


    private JPanel calPanel = new JPanel();
    private JLabel calLabel = new JLabel("摄入卡路里：");
    private JLabel cal = new JLabel();
    private JLabel calUnitLabel = new JLabel("cal");

    private JPanel exercisePanel = new JPanel();
    private JLabel exerciseLabel = new JLabel("锻炼方法：");
    private JLabel exercise = new JLabel();

    private JPanel remarkPanel = new JPanel();
    private JLabel remarkLabel = new JLabel("备注：");
    private JLabel remark = new JLabel();

    private JPanel cureStatePanel = new JPanel();
    private JTable cureStateTable = new JTable();
    private JScrollPane cureStateJSroll;

    private JPanel operationPanel = new JPanel();
    private JButton addCureStateButton = new JButton("添加治疗记录");


    public CureStateFrame(Customer customer, List<CureState> cureStateList) throws SQLException {
        // ===========================上面部分（用户信息展示）=================================================
        customerPanel.setLayout(new GridLayout(5, 2));
        // customerId
        customerId = customer.getId();

        // 名字
        setTitle(customer.getName());

        // 性别
        sex.setText(customer.getSex());
        sexPanel.add(sexLabel);
        sexPanel.add(sex);
        customerPanel.add(sexPanel);

        // 年龄
        age.setText(DataUtils.toString(customer.getAge()));
        agePanel.add(ageLabel);
        agePanel.add(age);
        customerPanel.add(agePanel);

        // 身高
        height.setText(DataUtils.toString(customer.getHeight()));
        heightPanel.add(heightLabel);
        heightPanel.add(height);
        heightPanel.add(cmUnitLabel);
        customerPanel.add(heightPanel);

        // 体重
        weight.setText(DataUtils.toString(customer.getWeight()));
        weightPanel.add(weightLabel);
        weightPanel.add(weight);
        weightPanel.add(kgUnitLabel);
        customerPanel.add(weightPanel);

        // 手机号码
        phoneNumber.setText(DataUtils.toString(customer.getPhoneNumber()));
        phoneNumberPanel.add(phoneNumberLabel);
        phoneNumberPanel.add(phoneNumber);
        customerPanel.add(phoneNumberPanel);


        // 摄入卡路里
        cal.setText(DataUtils.toString(customer.getCal()));
        calPanel.add(calLabel);
        calPanel.add(cal);
        calPanel.add(calUnitLabel);
        customerPanel.add(calPanel);

        // 锻炼方法
        exercise.setText(DataUtils.toString(customer.getExercise()));
        exercisePanel.add(exerciseLabel);
        exercisePanel.add(exercise);
        customerPanel.add(exercisePanel);

        // 备注
        remark.setText(DataUtils.toString(customer.getRemark()));
        remarkPanel.add(remarkLabel);
        remarkPanel.add(remark);
        customerPanel.add(remarkPanel);

        add(customerPanel, BorderLayout.NORTH);

        // ===========================中间部分（治疗记录）====================================================
        cureStateTable = new JTable(DataUtils.convertCureStateToArray(cureStateList), CureState.tableColumns) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        initStateTable(cureStateTable);
        cureStateJSroll = new JScrollPane(cureStateTable);
        cureStatePanel.add(cureStateJSroll, BorderLayout.CENTER);
        add(cureStatePanel, BorderLayout.CENTER);

        // ===========================下面部分（底部按钮）======================================================
        addCureStateButton.addActionListener(new OpenAddCureStateListener(customerId, this));
        operationPanel.add(addCureStateButton);
        add(operationPanel, BorderLayout.SOUTH);

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initStateTable(JTable cureStateTable) {
        cureStateTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        cureStateTable.setPreferredScrollableViewportSize(new Dimension(1000, 300));
        cureStateTable.addMouseListener(new CureStateMenuListener(cureStateTable, this));
        TableColumnModel tableColumnModel = cureStateTable.getColumnModel();
        for (int i = 0; i < CureState.columnWidths.length; i++) {
            tableColumnModel.getColumn(i).setPreferredWidth(CureState.columnWidths[i]);
        }
        cureStateTable.setDefaultRenderer(Object.class, new TableViewRenderer());
    }


    public void updateCureStateTable(List<CureState> cureStateList) {
        cureStateTable = new JTable(DataUtils.convertCureStateToArray(cureStateList), CureState.tableColumns) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        initStateTable(cureStateTable);
        cureStateJSroll = new JScrollPane(cureStateTable);
        cureStatePanel.add(cureStateJSroll, BorderLayout.CENTER);
        cureStatePanel.setVisible(true);
    }

    public void refreshCureStateTable() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    cureStatePanel.remove(cureStateJSroll);
                    cureStatePanel.setVisible(false);
                    List<CureState> cureStateList = DBUtils.queryCureState(customerId);
                    updateCureStateTable(cureStateList);
                } catch (SQLException e) {
                    log.error("refreshCureStateTable failed:", e);
                } catch (Exception e) {
                    log.error("refreshCureStateTable failed:", e);
                }
            }
        });

    }

}
