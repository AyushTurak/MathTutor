package com.example.mathtutorial;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class Area_Calculator_Activity extends AppCompatActivity {

    private EditText dimension1EditText;
    private EditText dimension2EditText;
    private TextView resultTextView;
    private Spinner shapeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_area_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dimension1EditText = findViewById(R.id.dimension1EditText);
        dimension2EditText = findViewById(R.id.dimension2EditText);
        resultTextView = findViewById(R.id.resultTextView);
        shapeSpinner = findViewById(R.id.shapeSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.shapes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shapeSpinner.setAdapter(adapter);

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateArea();
            }
        });
    }

    private void calculateArea() {
        String dimension1Str = dimension1EditText.getText().toString().trim();
        String dimension2Str = dimension2EditText.getText().toString().trim();

        if (dimension1Str.isEmpty() || dimension2Str.isEmpty()) {
            resultTextView.setText("Please enter dimensions");
            return;
        }

        double dimension1 = Double.parseDouble(dimension1Str);
        double dimension2 = Double.parseDouble(dimension2Str);
        String selectedShape = shapeSpinner.getSelectedItem().toString();

        double area = 0.0;
        switch (selectedShape) {
            case "Square":
                area = dimension1 * dimension1;
                break;
            case "Rectangle":
                area = dimension1 * dimension2;
                break;
            case "Circle":
                area = Math.PI * Math.pow(dimension1, 2);
                break;
            case "Triangle":
                area = 0.5 * dimension1 * dimension2;
                break;
        }

        resultTextView.setText(String.format(Locale.getDefault(), "Area: %.2f", area));
    }
}