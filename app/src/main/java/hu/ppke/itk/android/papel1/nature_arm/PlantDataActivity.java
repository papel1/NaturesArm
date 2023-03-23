package hu.ppke.itk.android.papel1.nature_arm;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import hu.ppke.itk.android.papel1.nature_arm.base.BitmapHelper;
import hu.ppke.itk.android.papel1.nature_arm.data.DAO.factory.AppDatabase;
import hu.ppke.itk.android.papel1.nature_arm.data.model.Plant;

public class PlantDataActivity extends BitmapHelper
{
    ImageView image;
    TextView name_tv;
    TextView desc_tv;
    TextView loc_tv;
    TextView water_tv;
    TextView fert_tv;
    TextView trans_tv;
    Button edit_button;
    Button delete_button;
    ImageButton qr_button;

    ArrayList<Plant> plantArrayList;

    public final static int QR_SIZE = 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantdata);

        image = findViewById(R.id.imageView);
        name_tv = findViewById(R.id.nameTextView);
        desc_tv = findViewById(R.id.descriptionTextView);
        loc_tv = findViewById(R.id.locationTextView);
        water_tv = findViewById(R.id.waterTextView);
        fert_tv = findViewById(R.id.fertilizeTextView);
        trans_tv = findViewById(R.id.transplatTextView);
        edit_button = findViewById(R.id.editButton);
        delete_button = findViewById(R.id.deleteButton);
        qr_button = findViewById(R.id.saveQRButton);

        Intent intent = getIntent();
        plantArrayList = intent.getParcelableArrayListExtra(getString(R.string.plantArrayList));

        IntentFilter plantListIntent = new IntentFilter(getString(R.string.plantListIntent));
        registerReceiver(new PlantDataActivity.MyReceiver(), plantListIntent);

        refreshView();

        edit_button.setOnClickListener(v -> {
            Intent editIntent = new Intent(getBaseContext(), EditPlantActivity.class);
            editIntent.putExtra(getString(R.string.plantArrayList), plantArrayList);
            startActivityForResult(editIntent, 1);
        });

        delete_button.setOnClickListener(v -> {
            sendIntent();
            finish();
        });

        qr_button.setOnClickListener(v -> {
            Toast.makeText(getBaseContext(), R.string.qr_save_toast, Toast.LENGTH_SHORT).show();
            String qrString = plantArrayList.get(0).id + getString(R.string.qrDelimeter) + plantArrayList.get(0).name;

            try
            {
                saveBitmap(getApplicationContext(), createQRImage(qrString, QR_SIZE, QR_SIZE));
            } catch (IOException | WriterException e)
            {
                e.printStackTrace();
            }
        });
    }

    public static Bitmap createQRImage(String url, int width, int height) throws WriterException
    {
        BitMatrix bitMatrix =
                new QRCodeWriter().encode(url,
                        BarcodeFormat.QR_CODE,
                        width, height,
                        Collections.singletonMap(EncodeHintType.CHARACTER_SET, "utf-8"));
        int[] qrArray = new int[width * height];

        for (int w = 0; w < width; w++)
        {
            for (int h = 0; h < height; h++)
            {
                qrArray[w * width + h] = bitMatrix.get(w, h) ? Color.BLACK : Color.WHITE;
            }
        }

        return Bitmap.createBitmap(qrArray, width, height, Bitmap.Config.ARGB_8888);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void refreshView()
    {
        Plant selectedPlant = plantArrayList.get(0);

        if (selectedPlant.imageUri != null && selectedPlant.imageUri.toString().isEmpty())
            image.setImageDrawable(getResources().getDrawable(R.drawable.demo_plant));
        else if (selectedPlant.imageUri != null)
            image.setImageURI(selectedPlant.imageUri);

        name_tv.setText(selectedPlant.name);
        desc_tv.setText(selectedPlant.description);
        loc_tv.setText(selectedPlant.location);
        water_tv.setText(String.valueOf(selectedPlant.wateringFreq));
        fert_tv.setText(String.valueOf(selectedPlant.fertilizingFreq));
        trans_tv.setText(String.valueOf(selectedPlant.transplantingFreq));
    }

    void sendIntent()
    {
        AppDatabase.getDatabase(getApplicationContext()).deletePlant(plantArrayList.get(0));
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(getString(R.string.plantListIntent));
        broadcastIntent.putExtra(getString(R.string.deletedPlant), plantArrayList);
        sendBroadcast(broadcastIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    private class MyReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            ArrayList<Plant> editedPlant = intent.getParcelableArrayListExtra(getString(R.string.editedPlant));

            if (editedPlant != null && editedPlant.size() > 0)
                plantArrayList.set(0, editedPlant.get(0));

            refreshView();
        }
    }
}
