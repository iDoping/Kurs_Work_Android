package com.example.kursrpmos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    TextView tvCostDateForSQL;
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
        etDate = findViewById(R.id.etDate);

        dbHelper = new DBHelper(this);

        spinner = findViewById(R.id.spinCostCat);

        tvCostDateForSQL = findViewById(R.id.tvtestdate);

        setInitialDateTime();
        setInitialDateTimeForSQLite();
        LoadSpinnerData();
    }

    public void onAddCostsClick(View view) {

        String sum = etSum.getText().toString();
        String cost_cat = spinner.getSelectedItem().toString();
        String DateForSQLite = tvCostDateForSQL.getText().toString();

        if (sum.equals("")) {
            Toast.makeText(getApplicationContext(), "Заполните поле Сумма", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.InsertCost(sum, DateForSQLite, cost_cat);
            etSum.setText("");
            Toast.makeText(getApplicationContext(), "Расход добавлен", Toast.LENGTH_SHORT).show();
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