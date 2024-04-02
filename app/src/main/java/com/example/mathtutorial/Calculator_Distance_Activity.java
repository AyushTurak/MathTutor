package com.example.mathtutorial;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Calculator_Distance_Activity extends AppCompatActivity {

    private EditText latitude1EditText, longitude1EditText, latitude2EditText, longitude2EditText;
    private TextView distanceTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator_distance);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.answer_quiz), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        latitude1EditText = findViewById(R.id.latitude1EditText);
        longitude1EditText = findViewById(R.id.longitude1EditText);
        latitude2EditText = findViewById(R.id.latitude2EditText);
        longitude2EditText = findViewById(R.id.longitude2EditText);
        distanceTextView = findViewById(R.id.distanceTextView);

        Button calculateDistanceButton = findViewById(R.id.calculateDistanceButton);
        calculateDistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDistance();
            }
        });
    }

    private void calculateDistance() {
        // Get latitude and longitude values from EditText fields
        double lat1 = Double.parseDouble(latitude1EditText.getText().toString());
        double lon1 = Double.parseDouble(longitude1EditText.getText().toString());
        double lat2 = Double.parseDouble(latitude2EditText.getText().toString());
        double lon2 = Double.parseDouble(longitude2EditText.getText().toString());

        // Calculate distance using Haversine formula
        double distance = calculateHaversineDistance(lat1, lon1, lat2, lon2);

        // Display the calculated distance
        distanceTextView.setText("Distance: " + distance + " km");
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // Radius of the Earth in kilometers

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in kilometers
    }
}
