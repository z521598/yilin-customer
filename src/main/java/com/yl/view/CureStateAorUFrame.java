package com.yl.view;


import com.yl.common.exception.DataFormatException;
import com.yl.common.utils.DataUtils;
import com.yl.listener.AddOrUpdateCureStateListener;
import com.yl.model.CureState;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/23.
 */
public class CureStateAorUFrame extends JFrame {

    private Long id;
    private Long customerId;

    private JPanel symptomPanel = new JPanel();
    private JLabel symptomLabel = new JLabel("症状：");
    private JTextArea symptomField = new JTextArea(3, 20);


    private JPanel peoplePanel = new JPanel();
    private JLabel peopleLabel = new JLabel("按摩师：");
    private JTextField peopleField = new JTextField(10);


    private JPanel effectPanel = new JPanel();
    private JLabel effectLabel = new JLabel("效果：");
    private JTextArea effectField = new JTextArea(3, 20);

    private JPanel remarkPanel = new JPanel();
    private JLabel remarkLabel = new JLabel("备注：");
    private JTextArea remarkField = new JTextArea(3, 20);

    private JPanel datePanel = new JPanel();
    private JLabel dateLabel = new JLabel("时间：");
    //获得时间日期模型
    private SpinnerDateModel model = new SpinnerDateModel();
    //获得JSPinner对象
    private JSpinner dateSpinner = new JSpinner(model);

    private JPanel operationPanel = new JPanel();
    private JButton confrimButton = new JButton("确定");

    public CureStateAorUFrame(CureState cureState, CureStateFrame cureStateFrame) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 1));
        setLocationRelativeTo(null);

        // id
        id = cureState.getId();
        // customerId
        customerId = cureState.getCustomerId();


        // 症状
        symptomField.setText(DataUtils.toString(cureState.getSymptom()));
        symptomPanel.add(symptomLabel);
        symptomPanel.add(new JScrollPane(symptomField));
        add(symptomPanel);

        // 推拿师
        peopleField.setText(DataUtils.toString(cureState.getPeople()));
        peoplePanel.add(peopleLabel);
        peoplePanel.add(peopleField);
        add(peoplePanel);

        // 效果
        effectField.setText(DataUtils.toString(cureState.getEffect()));
        effectPanel.add(effectLabel);
        effectPanel.add(new JScrollPane(effectField));
        add(effectPanel);

        // 备注
        remarkField.setText(DataUtils.toString(cureState.getRemark()));
        remarkPanel.add(remarkLabel);
        remarkPanel.add(new JScrollPane(remarkField));
        add(remarkPanel);

        datePanel.add(dateLabel);
        dateSpinner.setValue(new Date());
        //设置时间格式
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);
        dateSpinner.setBounds(34, 67, 219, 22);
        datePanel.add(dateSpinner);
        add(datePanel);

        // 底部按钮
        confrimButton.addActionListener(new AddOrUpdateCureStateListener(this, cureStateFrame));
        operationPanel.add(confrimButton);
        add(operationPanel);
        setTitle("添加或修改诊疗记录");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public CureStateAorUFrame(Long customerId, CureStateFrame cureStateFrame) {
        this(new CureState(customerId), cureStateFrame);
    }

    public CureState checkAndgetCureState() throws DataFormatException {
        CureState cureState = new CureState();
        cureState.setId(id);
        cureState.setCustomerId(customerId);
        cureState.setSymptom(symptomField.getText());
        cureState.setPeople(peopleField.getText());
        cureState.setEffect(effectField.getText());
        cureState.setRemark(remarkField.getText());
        Date date = null;
        try {
            date = (Date) dateSpinner.getValue();
        } catch (Exception e) {
            throw new DataFormatException("日期格式异常,例如:2018-5-17");
        }
        cureState.setDate(date);
        return cureState;
    }

}
