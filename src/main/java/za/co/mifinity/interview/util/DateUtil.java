package za.co.mifinity.interview.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateUtil {


    public static DateTimeFormatter FORMATER = DateTimeFormatter.ofPattern("yy/MM");

    public static String CONVERT_EXP_DATE(LocalDate date) {
        return DateUtil.FORMATER.format(date);
    }


}
