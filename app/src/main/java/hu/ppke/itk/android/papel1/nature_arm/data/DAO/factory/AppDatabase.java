package hu.ppke.itk.android.papel1.nature_arm.data.DAO.factory;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import hu.ppke.itk.android.papel1.nature_arm.data.DAO.PlantDAO;
import hu.ppke.itk.android.papel1.nature_arm.data.converters.TimeStampConverter;
import hu.ppke.itk.android.papel1.nature_arm.data.converters.UriConverter;
import hu.ppke.itk.android.papel1.nature_arm.data.model.Plant;

@Database(entities = {Plant.class}, version = 1)
@TypeConverters({TimeStampConverter.class, UriConverter.class})
public abstract class AppDatabase extends RoomDatabase
{
    public abstract PlantDAO plantDAO();

    private static volatile AppDatabase INSTANCE = null;
    private static volatile PlantDAO DAO_INSTANCE = null;

    public static PlantDAO getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (AppDatabase.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context,
                            AppDatabase.class, PlantDAO.dbName).allowMainThreadQueries().build();
                    DAO_INSTANCE = INSTANCE.plantDAO();
                }
            }
        }

        return DAO_INSTANCE;
    }
}
