package hu.ppke.itk.android.papel1.nature_arm.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import hu.ppke.itk.android.papel1.nature_arm.R;
import hu.ppke.itk.android.papel1.nature_arm.data.model.Plant;

public class PlantModificationHelper extends CameraHelper
{
    protected Button saveButton;
    protected ImageButton imageButton, galleryButton;
    protected ImageView imageView;
    protected EditText name_et;
    protected EditText desc_et;
    protected EditText loc_et;
    protected EditText water_et;
    protected EditText fert_et;
    protected EditText trans_et;
    protected String extra;

    protected ArrayList<Plant> plantArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newplant);

        saveButton = findViewById(R.id.saveButton);
        imageButton = findViewById(R.id.imageButton);
        galleryButton = findViewById(R.id.galleryButton);
        imageView = findViewById(R.id.imageView);
        name_et = findViewById(R.id.nameEditText);
        desc_et = findViewById(R.id.descriptionEditText);
        loc_et = findViewById(R.id.locationEditText);
        water_et = findViewById(R.id.waterEditText);
        fert_et = findViewById(R.id.fertilizeEditText);
        trans_et = findViewById(R.id.transplatEditText);

        imageButton.setOnClickListener(v -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else
            {
                dispatchTakePictureIntent();
            }
        });

        galleryButton.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType(getString(R.string.photoPickerType));
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        });
    }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        imageView.setImageURI(imageUri);
    }

    protected void sendIntent(String extra)
    {
        Intent broadcastIntent = new Intent();
        broadcastIntent.putExtra(extra, plantArrayList);
        broadcastIntent.setAction(getString(R.string.plantListIntent));
        sendBroadcast(broadcastIntent);
    }
}
