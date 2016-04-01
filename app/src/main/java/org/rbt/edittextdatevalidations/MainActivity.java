package org.rbt.edittextdatevalidations;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "DATEVA_LIDATION";
    private EditText monthText, yearText;
    private TextView helperText;
    private int MIN_YEAR, MAX_YEAR;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //edit text fields
        monthText = (EditText) findViewById(R.id.month);
        monthText.setText(getCurrentMonth());
        monthText.setSelection(getCurrentMonth().length());
        monthText.addTextChangedListener(new MonthWatcher());

        yearText = (EditText) findViewById(R.id.year);
        yearText.setText(getCurrentYear());
        yearText.setSelection(getCurrentYear().length());
        yearText.addTextChangedListener(new YearWatcher());

        //text view
        helperText = (TextView) findViewById(R.id.helperText);

        //button
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        //assigning minimum and maximum years to globally declared variables
        MIN_YEAR = Integer.parseInt(getCurrentYear());
        MAX_YEAR = Integer.parseInt(getCurrentYear()) + 20; //putting a maximum validation of 20 years

        getLog("Minimum year:::::" + MIN_YEAR);
        getLog("Maximum Year:::::" + MAX_YEAR);
    }

    /**
     * Returns current month
     *
     * @return
     */
    private String getCurrentMonth() {

        int month = Calendar.getInstance().get(Calendar.MONTH);

        month = month + 1;

        return Integer.toString(month);
    }

    /**
     * Returns current year
     *
     * @return
     */
    private String getCurrentYear() {

        int year = Calendar.getInstance().get(Calendar.YEAR);

        return Integer.toString(year);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.submitButton:

                if (helperText.getVisibility() == View.GONE) {
                    Toast.makeText(this, "Successful Validation", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Errors", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void getLog(String msg) {
        Log.d(TAG, msg);
    }

    /**
     * Additional validation of year
     */
    private void yearValidation() {

        int size = yearText.length();
        String str = yearText.getText().toString();

        if (size > 0 && size == 4 && str.matches("[0-9]+") && Integer.parseInt(str) >= MIN_YEAR && Integer.parseInt(str) <= MAX_YEAR) { //this check makes sure the string does not contain any other characters
            helperText.setVisibility(View.GONE);
        } else {
            //errors should be handled here
            helperText.setVisibility(View.VISIBLE);
            helperText.setText("Enter valid year");
            helperText.setTextColor(Color.RED);

            getLog("Not a number or string can be empty");
        }
    }

    /**
     * Additional validation of month
     */
    private void monthValidation() {

        int size = monthText.length();
        String str = monthText.getText().toString();

        if (size > 0 && str.matches("[0-9]+") && !str.equalsIgnoreCase("0") && !str.equalsIgnoreCase("00")) { //this check makes sure the string does not contain any other characters
            helperText.setVisibility(View.GONE);
        } else {
            //errors should be handled here
            helperText.setVisibility(View.VISIBLE);
            helperText.setText("Enter valid month");
            helperText.setTextColor(Color.RED);

            getLog("Not a number or string can be empty");
        }
    }

    /**
     * Text watcher class for month
     */
    private class MonthWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String str = s.toString();

            if (s.length() > 0 && str.matches("[0-9]+") && !str.equalsIgnoreCase("0") && !str.equalsIgnoreCase("00")) { //this check makes sure the string does not contain any other characters
                int monthValue = Integer.parseInt(s.toString());
                if (monthValue > 12) {
                    monthText.setText(Integer.toString(12));
                    monthText.setSelection(s.length());
                }
                helperText.setVisibility(View.GONE);

                //performing additional check of year for displaying error message
                yearValidation();
            } else {
                //errors should be handled here
                helperText.setVisibility(View.VISIBLE);
                helperText.setText("Enter valid month");
                helperText.setTextColor(Color.RED);

                getLog("Not a number or string can be empty");
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * Text watcher class for year
     */
    private class YearWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String str = s.toString();

            if (s.length() > 0 && s.length() == 4 && str.matches("[0-9]+") && Integer.parseInt(str) >= MIN_YEAR && Integer.parseInt(str) <= MAX_YEAR) { //this check makes sure the string does not contain any other characters
                helperText.setVisibility(View.GONE);

                //performing additional check of month for displaying error message
                monthValidation();
            } else {
                //errors should be handled here
                helperText.setVisibility(View.VISIBLE);
                helperText.setText("Enter valid year");
                helperText.setTextColor(Color.RED);

                getLog("Not a number or string can be empty");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
