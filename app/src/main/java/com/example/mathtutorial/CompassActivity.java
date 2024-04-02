package com.example.mathtutorial;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    TextView degreetxt;
    ImageView compassimg;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.answer_quiz), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        degreetxt = findViewById(R.id.tvDegrees);
        compassimg = findViewById(R.id.imgCompass);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensorList) {
            Log.d("Sensor", s.toString());  // In case i need a specific sensor for something. Otherwise can be deleted.
        }
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER));
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int degree = Math.round(sensorEvent.values[0]);
        degreetxt.setText("Heading to: " + degree + " ° ");
        compassimg.setRotation(-degree);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}