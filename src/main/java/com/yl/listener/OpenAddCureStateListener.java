package com.yl.listener;

import com.yl.view.CureStateAorUFrame;
import com.yl.view.CureStateFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Administrator on 2018/5/18.
 */
public class OpenAddCureStateListener implements ActionListener {

    private Long customerId;
    private CureStateFrame cureStateFrame;

    public OpenAddCureStateListener(Long customerId, CureStateFrame cureStateFrame) {
        this.customerId = customerId;
        this.cureStateFrame = cureStateFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new CureStateAorUFrame(customerId, cureStateFrame);
    }
}
