package be.ehb.CovidRecords.model.util;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDate;




public class DateConverter {
    @TypeConverter
    public static String dateToString(LocalDate date){
        return (date == null)? null : date.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDate stringToDate(String dateString){
        return (dateString == null)? null : LocalDate.parse(dateString);
    }
}
