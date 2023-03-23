package hu.ppke.itk.android.papel1.nature_arm.data.converters;

import android.net.Uri;

import androidx.room.TypeConverter;

public class UriConverter
{
    @TypeConverter
    public static Uri fromUri(String value)
    {
        if (value != null)
            return Uri.parse(value);

        return Uri.EMPTY;
    }

    @TypeConverter
    public static String uriToString(Uri value)
    {
        if (value != null)
            return value.toString();
        return "";
    }
}
