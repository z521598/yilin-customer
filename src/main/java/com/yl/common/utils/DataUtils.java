package com.yl.common.utils;

import com.yl.common.db.DBUtils;
import com.yl.model.CureState;
import com.yl.model.Customer;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 */
public class DataUtils {

    public static String toString(Object object) {
        if (object == null) {
            return null;
        } else {
            return String.valueOf(object);
        }
    }

    public static boolean isEnglish(String word) {
        boolean sign = true; // 初始化标志为为'true'
        for (int i = 0; i < word.length(); i++) {
            if (!(word.charAt(i) >= 'A' && word.charAt(i) <= 'Z')
                    && !(word.charAt(i) >= 'a' && word.charAt(i) <= 'z')) {
                return false;
            }
        }
        return true;
    }


    public static boolean isNumber(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Object[][] convertCustomersToArray(List<Customer> customerList) {
        if (customerList == null) {
            return new Object[][]{};
        }
        Object[][] data = new Object[customerList.size()][];
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            Object[] custArray = customer.toArray();
            data[i] = custArray;
        }
        return data;
    }

    public static Object[][] convertCureStateToArray(List<CureState> cureStateList) {
        if (cureStateList == null) {
            return new Object[][]{};
        }
        Object[][] data = new Object[cureStateList.size()][];
        for (int i = 0; i < cureStateList.size(); i++) {
            CureState cureState = cureStateList.get(i);
            Object[] custArray = cureState.toArray();
            data[i] = custArray;
        }
        return data;
    }

}
