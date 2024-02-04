package com.example.targetsapvtoda;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etPassenger, etDiscounted;
    RadioGroup biyaheType;
    RadioButton rbRegular, rbSpecial;
    Button btnCalculate;
    Button btnReset;
    Button btnSave;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPassenger = findViewById(R.id.editPasahero);
        etDiscounted = findViewById(R.id.editDiscount);
        biyaheType = findViewById(R.id.tripType);
        rbRegular = findViewById(R.id.radioRegular);
        rbSpecial = findViewById(R.id.radioSpecial);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvResult = findViewById(R.id.viewResult);
        btnSave = findViewById(R.id.buttonSave);


        // etPassenger-Discounted  values min 1 max 4
        final int minValue = 1;
        final int maxValue = 4;


        //INPUT FILTER
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                try {
                    String currentValue = dest.toString();
                    String newValue = currentValue.substring(0, dstart) + source.subSequence(start, end) + currentValue.substring(dend);

                    // Parse the new value
                    int inputVal = Integer.parseInt(newValue);

                    // Check if the new value is within the desired range
                    if (inputVal >= minValue && inputVal <= maxValue) {
                        return null;  // Accept the input
                    } else {
                        // Error message for etPassenger < 1
                        if (inputVal < minValue) {
                            etPassenger.setError("Hala, may multo!");
                            etDiscounted.setError("Hala, may multo!");

                        } else {
                            etPassenger.setError("Noy, bawal sabit. Pasensya na "); // Error message for etPassenger-discounted > 4
                            etDiscounted.setError("Noy, sobra discount mo");
                        }
                        return "";  // Reject the input
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where parsing fails (e.g., empty input)
                    return "";  // Reject the input
                }
            }
        };
        // Apply the filter to the EditText
        etPassenger.setFilters(new InputFilter[]{filter});
        etDiscounted.setFilters(new InputFilter[]{filter});

        // Reset button
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Reset EditText values
                etPassenger.setText("");
                etDiscounted.setText("");

                // Clear any error messages
                etPassenger.setError(null);
                etDiscounted.setError(null);

                // Clear the radio group selection
                biyaheType.clearCheck();

                // Clear the result TextView
                tvResult.setText("");
            }
        });

        // Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create an Intent to start HistoryActivity
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        //CALCULATOR
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String passengerInput = etPassenger.getText().toString();
                String discountInput = etDiscounted.getText().toString();

                // setError if etPassenger is empty
                if (passengerInput.isEmpty()) {
                    etPassenger.setError("Noy, ilan ang pasahero?");
                    return;
                }

                int passengerCount = Integer.parseInt(passengerInput);

                // Validate that discount count does not exceed passenger count
                if (!discountInput.isEmpty()) {
                    int discountCount = Integer.parseInt(discountInput);
                    if (discountCount > passengerCount) {
                        etDiscounted.setError("Noy, sobra discount mo");
                        return;
                    }
                }

                int result = 0;

                int regularPrice = 10;
                int specialPrice = 30;

                //total trips tally
                int totalRegularTrips = 0;
                int totalSpecialTrips = 0;

                int selectedTrip = biyaheType.getCheckedRadioButtonId();

                if (selectedTrip == rbRegular.getId()) {
                    result = regularPrice * passengerCount;
                    totalRegularTrips += result; // Add regular trip to total
                } else if (selectedTrip == rbSpecial.getId()) {
                    // Check if the etPassenger is 1 or 2
                    if (passengerCount < 1 || passengerCount > 2) {
                        etPassenger.setError("Noy, 1 o 2 lang ang pwede sa special trip!");
                        return;
                    }
                    result = specialPrice; // rbSpecial fixed value
                    // rbSpecial, no discountCount
                    if (!discountInput.isEmpty()) {
                        etDiscounted.setError("Hala, hindi pwede ang discount sa special trip!");
                        return;
                    }
                    totalSpecialTrips += result; // Add special trip to total
                }//DISCOUNT
                if (!discountInput.isEmpty()) {
                    int discountCount = Integer.parseInt(discountInput);
                    // Discount
                    result -= discountCount * 2;
                }
                //DISPLAY
                tvResult.setText(String.valueOf(result));
            }
        });
    }
}