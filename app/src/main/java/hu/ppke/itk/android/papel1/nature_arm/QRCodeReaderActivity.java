package hu.ppke.itk.android.papel1.nature_arm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import hu.ppke.itk.android.papel1.nature_arm.base.LanguageHelper;
import hu.ppke.itk.android.papel1.nature_arm.data.DAO.factory.AppDatabase;
import hu.ppke.itk.android.papel1.nature_arm.data.model.Plant;

public class QRCodeReaderActivity extends LanguageHelper
{
    final static int QR_RESULT = 13;

    TextView plant_name;
    ImageView plant_image;
    String qr_code_content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcodereader);

        plant_name = findViewById(R.id.plantNameTextView);
        plant_image = findViewById(R.id.plantImageView);

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null)
        {
            if (intentResult.getContents() == null)
            {
                Toast.makeText(getBaseContext(), R.string.warning_qr, Toast.LENGTH_SHORT).show();
                finish();
            } else
            {
                qr_code_content = intentResult.getContents();
                plant_name.setText(intentResult.getContents());

                ArrayList<Plant> plantArrayList = new ArrayList<>();
                int id = Integer.parseInt(qr_code_content.substring(0, qr_code_content.indexOf(getString(R.string.qrDelimeter))));
                plantArrayList.add(AppDatabase.getDatabase(getApplicationContext()).selectPlantById(id));

                Intent returnIntent = new Intent();

                returnIntent.putExtra(getString(R.string.plantArrayList), plantArrayList);
                setResult(QR_RESULT, returnIntent);
                finish();
            }
        } else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
