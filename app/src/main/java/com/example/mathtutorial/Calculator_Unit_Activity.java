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

public class Calculator_Unit_Activity extends AppCompatActivity {

    private EditText inputEditText;
    private TextView resultTextView;
    private Spinner sourceUnitSpinner;
    private Spinner targetUnitSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator_unit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.answer_quiz), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inputEditText = findViewById(R.id.inputEditText);
        resultTextView = findViewById(R.id.resultTextView);
        sourceUnitSpinner = findViewById(R.id.sourceUnitSpinner);
        targetUnitSpinner = findViewById(R.id.targetUnitSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.distance_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnitSpinner.setAdapter(adapter);
        targetUnitSpinner.setAdapter(adapter);

        Button convertButton = findViewById(R.id.convertButton);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertDistance();
            }
        });
    }

    private void convertDistance() {
        String inputValueStr = inputEditText.getText().toString().trim();
        if (inputValueStr.isEmpty()) {
            resultTextView.setText("Please enter a value");
            return;
        }

        double inputValue = Double.parseDouble(inputValueStr);
        String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
        String targetUnit = targetUnitSpinner.getSelectedItem().toString();
        double convertedValue = 0.0;

        // Conversion logic
        if (sourceUnit.equals("Meters") && targetUnit.equals("Kilometers")) {
            convertedValue = inputValue / 1000;
        } else if (sourceUnit.equals("Kilometers") && targetUnit.equals("Meters")) {
            convertedValue = inputValue * 1000;
        } else if (sourceUnit.equals("Meters") && targetUnit.equals("Feet")) {
            convertedValue = inputValue * 3.28084; // 1 meter = 3.28084 feet
        } else if (sourceUnit.equals("Feet") && targetUnit.equals("Meters")) {
            convertedValue = inputValue / 3.28084;
        } else if (sourceUnit.equals("Inches") && targetUnit.equals("Centimeters")) {
            convertedValue = inputValue * 2.54; // 1 inch = 2.54 centimeters
        } else if (sourceUnit.equals("Centimeters") && targetUnit.equals("Inches")) {
            convertedValue = inputValue / 2.54;
        } else if (sourceUnit.equals("Miles") && targetUnit.equals("Kilometers")) {
            convertedValue = inputValue * 1.60934; // 1 mile = 1.60934 kilometers
        } else if (sourceUnit.equals("Kilometers") && targetUnit.equals("Miles")) {
            convertedValue = inputValue / 1.60934;
        }

        resultTextView.setText(String.format(Locale.getDefault(), "%.2f %s = %.2f %s",
                inputValue, sourceUnit, convertedValue, targetUnit));
    }
}