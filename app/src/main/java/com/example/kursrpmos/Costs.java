package com.example.kursrpmos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Costs extends AppCompatActivity {

    TextView tvCostDateForSQL, tvTest;
    Button btnAddCosts;
    DBHelper dbHelper;
    EditText etSum, etDate;
    Spinner spinner;
    Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costs);

        btnAddCosts = findViewById(R.id.btnAddCosts);

        etSum = findViewById(R.id.etSumm);
        etSum.setInputType(InputType.TYPE_CLASS_NUMBER);
        etDate = findViewById(R.id.etDate);

        dbHelper = new DBHelper(this);

        spinner = findViewById(R.id.spinCostCat);
        tvCostDateForSQL = findViewById(R.id.tvtestdate);
        tvTest = findViewById(R.id.tvTest);
        setInitialDateTime();
        setInitialDateTimeForSQLite();
        LoadSpinnerData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

                String value = adapter.getItemAtPosition(i).toString();
                String temp = dbHelper.getCostsPlans(value);
                if (temp == null) {
                    tvTest.setText("");
                } else {
                    if (Integer.valueOf(temp) < 0) {
                        tvTest.setText("Лимит категории " + value + " превышен");
                    } else {
                        tvTest.setText("Лимит категории " + value + " составляет " + temp + " рублей");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    public void onAddCostsClick(View view) {

        String sum = etSum.getText().toString();
        String cost_cat = spinner.getSelectedItem().toString();
        String DateForSQLite = tvCostDateForSQL.getText().toString();

        String lol = dbHelper.getCostsPlans(cost_cat);
        if(lol == null)
        {
            if (sum.equals("")) {
                Toast.makeText(getApplicationContext(), "Заполните поле Сумма", Toast.LENGTH_SHORT).show();
            } else {
                tvTest.setText("");
                dbHelper.InsertCost(sum, DateForSQLite, cost_cat);
                etSum.setText("");
                Toast.makeText(getApplicationContext(), "Расход добавлен", Toast.LENGTH_SHORT).show();
            }
        } else {

            if (sum.equals("")) {
                Toast.makeText(getApplicationContext(), "Заполните поле Сумма", Toast.LENGTH_SHORT).show();
            } else {

                int start_value = Integer.valueOf(dbHelper.getCostsPlans(cost_cat));
                int end_value = Integer.valueOf(sum);
                int result = start_value - end_value;
                dbHelper.insertChanges(result, cost_cat);
                dbHelper.InsertCost(sum, DateForSQLite, cost_cat);
                etSum.setText("");
                Toast.makeText(getApplicationContext(), "Расход добавлен", Toast.LENGTH_SHORT).show();
                String temp = dbHelper.getCostsPlans(cost_cat);
                if (Integer.valueOf(temp) < 0) {
                    tvTest.setText("Лимит категории " + cost_cat + " превышен");
                } else {
                    tvTest.setText("Лимит категории " + cost_cat + " составляет " + temp + " рублей");
                }
            }
        }
    }

    public void onSelectDateClick(View view) {
        new DatePickerDialog(Costs.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setInitialDateTime() {
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        etDate.setText(thisDate);
    }

    private void setInitialDateTimeForSQLite() {
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        tvCostDateForSQL.setText(thisDate);
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String s = "";
            if (dayOfMonth < 10) {
                s = "0" + dayOfMonth;
                String date2 = year + "/" + (monthOfYear + 1) + "/" + s;
                tvCostDateForSQL.setText(date2);
            } else {
                String date2 = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                tvCostDateForSQL.setText(date2);
            }
            etDate.setText(date);
        }
    };

    public void LoadSpinnerData() {
        // database handler

        // Spinner Drop down elements
        List<String> lables = dbHelper.getAllLabels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        //dbHelper.close();
    }

    static final private int CHOOSE_PREP = 0;

    public void onDialogClick(View view) {
        Intent intent = new Intent(Costs.this, Dialog_costs.class);
        startActivityForResult(intent, CHOOSE_PREP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String label = data.getStringExtra(Dialog_costs.TEMP);
            dbHelper.insertLabel(label);
        }
        LoadSpinnerData();
    }
}