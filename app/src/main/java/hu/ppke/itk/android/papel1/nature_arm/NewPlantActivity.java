package hu.ppke.itk.android.papel1.nature_arm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import hu.ppke.itk.android.papel1.nature_arm.base.PlantModificationHelper;
import hu.ppke.itk.android.papel1.nature_arm.data.DAO.factory.AppDatabase;
import hu.ppke.itk.android.papel1.nature_arm.data.model.Plant;

public class NewPlantActivity extends PlantModificationHelper
{

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        extra = getString(R.string.newPlant);

        imageView.setImageDrawable(getResources().getDrawable(R.drawable.demo_plant));
        plantArrayList = new ArrayList<>();

        saveButton.setOnClickListener(v -> {
            if (String.valueOf(name_et.getText()).isEmpty() || String.valueOf(desc_et.getText()).isEmpty() ||
                    String.valueOf(loc_et.getText()).isEmpty() || String.valueOf(water_et.getText()).isEmpty() ||
                    String.valueOf(fert_et.getText()).isEmpty() || String.valueOf(trans_et.getText()).isEmpty())
            {
                Toast.makeText(getBaseContext(), R.string.warning, Toast.LENGTH_LONG).show();
            } else
            {
                Plant newPlant = new Plant();

                newPlant.name = String.valueOf(name_et.getText());
                newPlant.description = String.valueOf(desc_et.getText());
                newPlant.location = String.valueOf(loc_et.getText());
                newPlant.wateringFreq = Integer.parseInt(String.valueOf(water_et.getText()));
                newPlant.fertilizingFreq = Integer.parseInt(String.valueOf(fert_et.getText()));
                newPlant.transplantingFreq = Integer.parseInt(String.valueOf(trans_et.getText()));

                if (imageUri != null)
                {
                    newPlant.imageUri = imageUri;
                }
                plantArrayList.add(newPlant);

                AppDatabase.getDatabase(getApplicationContext()).insertPlant(plantArrayList.get(0));
                sendIntent(extra);
                finish();
            }
        });
    }
}
