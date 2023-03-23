package hu.ppke.itk.android.papel1.nature_arm;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import hu.ppke.itk.android.papel1.nature_arm.base.LanguageHelper;

import static android.hardware.Sensor.TYPE_LIGHT;

public class LightMeterActivity extends LanguageHelper
{
    SensorManager sensorManager;
    Sensor light_sensor;
    SensorEventListener light_sensorEventListener;
    View light_activity;
    float max_value;

    TextView measured_light;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch light_switch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightmeter);

        light_activity = findViewById(R.id.lightActivity);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        light_sensor = sensorManager.getDefaultSensor(TYPE_LIGHT);

        measured_light = findViewById(R.id.editTextNumber);
        light_switch = findViewById(R.id.fc_lux_switch);

        if (light_sensor == null)
        {
            Toast.makeText(this, R.string.warning_lux, Toast.LENGTH_SHORT).show();
            finish();
        }

        max_value = light_sensor.getMaximumRange();

        light_sensorEventListener = new SensorEventListener()
        {
            @Override
            public void onSensorChanged(SensorEvent event)
            {
                if (!light_switch.isChecked())
                {
                    float value_lux = event.values[0];
                    @SuppressLint("DefaultLocale") String string_value_lux = String.format("%.0f", value_lux);
                    measured_light.setText(string_value_lux);
                } else
                {
                    float value = event.values[0];
                    @SuppressLint("DefaultLocale") String string_value = String.format("%.2f", (value * 0.09290304));
                    measured_light.setText(string_value);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy)
            {

            }
        };
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(light_sensorEventListener, light_sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(light_sensorEventListener);
    }
}
