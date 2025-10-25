package com.guai.onlinelearning.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static final DateFormat DOB_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static void checkDOB_Format(String dob) {
        toDateByDOB_Format(dob);
    }

    public static Date toDateByDOB_Format(String dob) {
        try {
            return DOB_FORMAT.parse(dob);
        } catch (ParseException e) {
            throw new RuntimeException("Date of Birth format is invalid. Please change it to yyyyMMdd format.");
        }
    }
}
