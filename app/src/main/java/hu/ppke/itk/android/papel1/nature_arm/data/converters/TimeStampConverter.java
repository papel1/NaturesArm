package hu.ppke.itk.android.papel1.nature_arm.data.converters;

import android.annotation.SuppressLint;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampConverter
{
    public static String TIME_STAMP_FORMAT = "YYYY-MM-DD HH:MM:SS.SSS";
    @SuppressLint("SimpleDateFormat")
    static DateFormat df = new SimpleDateFormat(TIME_STAMP_FORMAT);

    @TypeConverter
    public static Date fromTimestamp(String value)
    {
        if (value != null)
        {
            try
            {
                return df.parse(value);
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return new Date();
    }

    @TypeConverter
    public static String dateToTimestamp(Date value)
    {
        if (value != null)
            return df.format(value);
        return "";
    }
}
