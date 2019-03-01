package com.models;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author Manish Kumar
 * @since 2/9/17
 */


public abstract class BaseModel {

    public boolean isValidString (String data) {
        return data != null && !data.trim().isEmpty();
    }

    public static String getValidString (String data) {
        if (TextUtils.isEmpty(data)) {
            data = "";
        }
        return data;
    }

    public boolean isValidList (List list) {
        return list != null && list.size() > 0;
    }

    public String getValidDecimalFormat (String value) {
        if (!isValidString(value)) {
            return "0.00";
        }
        double netValue = Double.parseDouble(value);
        return getValidDecimalFormat(netValue);
    }

    public String getValidDecimalFormat (double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(value);
    }

    public long getValidLong(long value){
        if (Long.valueOf(value) == null) {
          return 0;
        }
       return value;
    }
}
