 package hu.ppke.itk.android.papel1.nature_arm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import hu.ppke.itk.android.papel1.nature_arm.base.PlantModificationHelper;
import hu.ppke.itk.android.papel1.nature_arm.data.DAO.factory.AppDatabase;

public class EditPlantActivity extends PlantModificationHelper
{

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        extra = getString(R.string.editedPlant);

        Intent intent = getIntent();
        plantArrayList = intent.getParcelableArrayListExtra(getString(R.string.plantArrayList));

        name_et.setText(plantArrayList.get(0).name);
        desc_et.setText(plantArrayList.get(0).description);
        loc_et.setText(plantArrayList.get(0).location);
        water_et.setText(Integer.toString(plantArrayList.get(0).wateringFreq));
        fert_et.setText(Integer.toString(plantArrayList.get(0).fertilizingFreq));
        trans_et.setText(Integer.toString(plantArrayList.get(0).transplantingFreq));

        if (plantArrayList.get(0).imageUri.toString().isEmpty())
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.demo_plant));
        else
            imageView.setImageURI(plantArrayList.get(0).imageUri);

        saveButton.setOnClickListener(v -> {

            if (String.valueOf(name_et.getText()).isEmpty() || String.valueOf(desc_et.getText()).isEmpty() ||
                    String.valueOf(loc_et.getText()).isEmpty() || String.valueOf(water_et.getText()).isEmpty() ||
                    String.valueOf(fert_et.getText()).isEmpty() || String.valueOf(trans_et.getText()).isEmpty())
            {
                Toast.makeText(getBaseContext(), R.string.warning, Toast.LENGTH_LONG).show();
            } else
            {
                plantArrayList.get(0).name = String.valueOf(name_et.getText());
                plantArrayList.get(0).description = String.valueOf(desc_et.getText());
                plantArrayList.get(0).location = String.valueOf(loc_et.getText());

                if (imageUri != null)
                {
                    plantArrayList.get(0).imageUri = imageUri;
                }

                plantArrayList.get(0).wateringFreq = Integer.parseInt(String.valueOf(water_et.getText()));
                plantArrayList.get(0).fertilizingFreq = Integer.parseInt(String.valueOf(fert_et.getText()));
                plantArrayList.get(0).transplantingFreq = Integer.parseInt(String.valueOf(trans_et.getText()));

                AppDatabase.getDatabase(getApplicationContext()).updatePlants(plantArrayList.get(0));
                sendIntent(extra);
                finish();
            }
        });
    }
}
