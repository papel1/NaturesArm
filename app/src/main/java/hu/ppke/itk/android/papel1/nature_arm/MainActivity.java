package hu.ppke.itk.android.papel1.nature_arm;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import hu.ppke.itk.android.papel1.nature_arm.base.LanguageHelper;
import hu.ppke.itk.android.papel1.nature_arm.data.model.Plant;

//TODO: A few stuff that I want to do later in the application.
//TODO: splash animation is only working with api 29
//TODO: database handling is on the main thread
//TODO: extra: different flower icon chooser
//TODO: maps location
//TODO: Todo list with reminders
//TODO: middle logo size correction on different devices
//TODO: viewbounding

public class MainActivity extends LanguageHelper
{
    ImageView settings;
    CardView myPlants;
    CardView todo;
    CardView lightMeter;
    CardView qrCodeReader;

    ArrayList<Plant> plantArrayList = new ArrayList<>();

    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = findViewById(R.id.settingImageView);
        lightMeter = findViewById(R.id.luxCard);
        qrCodeReader = findViewById(R.id.qrCard);
        myPlants = findViewById(R.id.my_plantCard);

        settings.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
            startActivityForResult(intent, 1);
        });

        lightMeter.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), LightMeterActivity.class);
            startActivityForResult(intent, 1);
        });

        qrCodeReader.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), QRCodeReaderActivity.class);
            startActivityForResult(intent, 1);
        });

        myPlants.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), PlantListActivity.class);
            intent.putExtra(getString(R.string.plantArrayList), plantArrayList);
            startActivityForResult(intent, 1);
        });

        requestMediaPermission();
    }

    public void requestMediaPermission()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.read_permission));
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setMessage(getString(R.string.enable_permission));
                builder.setOnDismissListener(dialog -> requestPermissions(
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE));
                builder.show();
            } else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
        {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, getString(R.string.disables_permission), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == QRCodeReaderActivity.QR_RESULT)
        {
            Intent qrOpenIntent = new Intent(getBaseContext(), PlantDataActivity.class);
            ArrayList<Plant> qrPlant;
            qrPlant = data.getParcelableArrayListExtra(getString(R.string.plantArrayList));

            qrOpenIntent.putExtra(getString(R.string.plantArrayList), qrPlant);

            if (qrPlant.size() > 0 && qrPlant.get(0) != null)
                startActivityForResult(qrOpenIntent, 1);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }
}
