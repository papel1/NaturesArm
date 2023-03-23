package hu.ppke.itk.android.papel1.nature_arm.data.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import hu.ppke.itk.android.papel1.nature_arm.data.model.Plant;

@Dao
public interface PlantDAO
{
    public static String dbName = "plantDB";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPlant(Plant plant);

    @Update
    int updatePlants(Plant plant);

    @Delete
    int deletePlant(Plant plant);

    @Query("SELECT * FROM Plants")
    public Plant[] selectAllPlants();

    @Query("DELETE FROM Plants")
    public void eraseAll();

    @Query("SELECT * FROM Plants WHERE id = :id LIMIT 1")
    public Plant selectPlantById(int id);
}
