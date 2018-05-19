package com.yl.view;


import com.yl.common.exception.DataFormatException;
import com.yl.common.utils.SpellUtils;
import com.yl.common.utils.DataUtils;
import com.yl.listener.AddOrUpdateCustomerListener;
import com.yl.model.Customer;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 2018/4/23.
 */
public class CustomerAorUFrame extends JFrame {
    private Long id;

    private JPanel namePanel = new JPanel();
    private JLabel nameLabel = new JLabel("姓名：");
    private JTextField nameField = new JTextField(10);

    private JPanel sexPanel = new JPanel();
    private JLabel sexLabel = new JLabel("性别：");
    private ButtonGroup sexRadioButtonGroup = new ButtonGroup();
    private JRadioButton sexManRadioButton = new JRadioButton("男");
    private JRadioButton sexWomanRadioButton = new JRadioButton("女");

    private JPanel agePanel = new JPanel();
    private JLabel ageLabel = new JLabel("年龄：");
    private JTextField ageField = new JTextField(10);

    private JPanel heightPanel = new JPanel();
    private JLabel heightLabel = new JLabel("身高：");
    private JTextField heightField = new JTextField(10);
    private JLabel cmUnitLabel = new JLabel("cm");

    private JPanel weightPanel = new JPanel();
    private JLabel weightLabel = new JLabel("体重：");
    private JTextField weightField = new JTextField(10);
    private JLabel kgUnitLabel = new JLabel("kg");

    private JPanel phoneNumberPanel = new JPanel();
    private JLabel phoneNumberLabel = new JLabel("电话号：");
    private JTextField phoneNumberField = new JTextField(10);


    private JPanel calPanel = new JPanel();
    private JLabel calLabel = new JLabel("摄入卡路里：");
    private JTextField calField = new JTextField(10);
    private JLabel calUnitLabel = new JLabel("cal");

    private JPanel exercisePanel = new JPanel();
    private JLabel exerciseLabel = new JLabel("锻炼方法：");
    private JTextField exerciseField = new JTextField(10);

    private JPanel remarkPanel = new JPanel();
    private JLabel remarkLabel = new JLabel("备注：");
    private JTextArea remarkField = new JTextArea(3, 20);

    private JPanel operationPanel = new JPanel();
    private JButton confrimButton = new JButton("确定");


    public CustomerAorUFrame(Customer customer) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(10, 1, 0, 0));
        setLocationRelativeTo(null);
        setTitle("添加或者修改顾客");

        // id
        id = customer.getId();

        // 名字
        nameField.setText(DataUtils.toString(customer.getName()));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        add(namePanel);

        // 性别
//        if ("男".equals(customer.getSex())) {
//
//        }
        if ("女".equals(customer.getSex())) {
            sexWomanRadioButton.setSelected(true);
        }else{
            sexManRadioButton.setSelected(true);
        }
        sexPanel.add(sexLabel);
        sexRadioButtonGroup.add(sexManRadioButton);
        sexRadioButtonGroup.add(sexWomanRadioButton);
        sexPanel.add(sexManRadioButton);
        sexPanel.add(sexWomanRadioButton);
        add(sexPanel);

        // 年龄
        ageField.setText(DataUtils.toString(customer.getAge()));
        agePanel.add(ageLabel);
        agePanel.add(ageField);
        add(agePanel);

        // 身高
        heightField.setText(DataUtils.toString(customer.getHeight()));
        heightPanel.add(heightLabel);
        heightPanel.add(heightField);
        heightPanel.add(cmUnitLabel);
        add(heightPanel);

        // 体重
        weightField.setText(DataUtils.toString(customer.getWeight()));
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);
        weightPanel.add(kgUnitLabel);
        add(weightPanel);

        // 手机号码
        phoneNumberField.setText(DataUtils.toString(customer.getPhoneNumber()));
        phoneNumberPanel.add(phoneNumberLabel);
        phoneNumberPanel.add(phoneNumberField);
        add(phoneNumberPanel);


        // 摄入卡路里
        calField.setText(DataUtils.toString(customer.getCal()));
        calPanel.add(calLabel);
        calPanel.add(calField);
        calPanel.add(calUnitLabel);
        add(calPanel);

        // 锻炼方法
        exerciseField.setText(DataUtils.toString(customer.getExercise()));
        exercisePanel.add(exerciseLabel);
        exercisePanel.add(exerciseField);
        add(exercisePanel);

        // 备注
        remarkField.setText(DataUtils.toString(customer.getRemark()));
        remarkPanel.add(remarkLabel);
        remarkPanel.add(new JScrollPane(remarkField));
        add(remarkPanel);

        // 底部按钮
        operationPanel.add(confrimButton);
        confrimButton.addActionListener(new AddOrUpdateCustomerListener(this));
        add(operationPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Customer checkAndgetCustomer() throws DataFormatException {
        Customer customer = new Customer();
        customer.setId(id);
        String name = nameField.getText();
        if (StringUtils.isBlank(name)) {
            throw new DataFormatException("姓名不能为空");
        }
        if (!SpellUtils.isChinese(name)) {
            throw new DataFormatException("姓名必须为纯中文");
        }
        customer.setName(name);
        customer.setEnglishName(SpellUtils.getSpell(name));
        customer.setHeadEnglishName(SpellUtils.converterToFirstSpell(name));
        if (sexManRadioButton.isSelected()) {
            customer.setSex(sexManRadioButton.getText());
        } else {
            customer.setSex(sexWomanRadioButton.getText());
        }

        String ageStr = ageField.getText();
        if (StringUtils.isNotBlank(ageStr)) {
            customer.setAge(Integer.parseInt(ageStr.trim()));
        }

        String heightStr = heightField.getText();
        if (StringUtils.isNotBlank(heightStr)) {
            customer.setHeight(Integer.parseInt(heightStr.trim()));
        }
        String weightStr = weightField.getText();
        if (StringUtils.isNotBlank(weightStr)) {
            customer.setWeight(Integer.parseInt(weightStr.trim()));
        }
        String phoneNumber = phoneNumberField.getText();
        if (StringUtils.isBlank(phoneNumber)) {
            throw new DataFormatException("手机号码不能为空");
        }
        customer.setPhoneNumber(phoneNumber);
        customer.setExercise(exerciseField.getText());
        String calStr = calField.getText();
        if (StringUtils.isNotBlank(calStr)) {
            customer.setCal(Integer.parseInt(calStr.trim()));
        }
        customer.setRemark(remarkField.getText());
        return customer;
    }
}
