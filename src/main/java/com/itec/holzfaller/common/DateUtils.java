package com.itec.holzfaller.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    public static String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy");
        return simpleDateFormat.format(date);
    }

    public static Date getDateFromLocalDate(LocalDate localDate){
        return Date.from(Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())));
    }

    public static boolean before(Date before, Date after) {
        org.joda.time.LocalDate beforeDate = new org.joda.time.LocalDate(before);
        org.joda.time.LocalDate afterDate = new org.joda.time.LocalDate(after);
        return beforeDate.compareTo(afterDate) <= 0;
    }

    public static Date parseDate(String startDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyy");
        return simpleDateFormat.parse(startDate);
    }
}
