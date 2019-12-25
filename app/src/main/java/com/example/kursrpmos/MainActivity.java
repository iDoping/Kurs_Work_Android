package com.example.kursrpmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    public DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        String day = dateFormat.format(currentDate);
        // Форматирование времени как "часы:минуты:секунды"
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String time = timeFormat.format(currentDate);
        dbHelper.SetStartPlans(day,time);
    }

    public void onCostsButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, Costs.class);
        startActivity(intent);
    }

    public void onIncomeButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, Income.class);
        startActivity(intent);
    }

    public void onGraphicInfoClick(View view) {
        Intent intent = new Intent(MainActivity.this, Statistics.class);
        startActivity(intent);
    }

    public void onGraphicIncomesInfo(View view) {
        Intent intent = new Intent(MainActivity.this, Statistics_income.class);
        startActivity(intent);
    }

    public void onCostSettingsClick(View view) {
        Intent intent = new Intent(MainActivity.this, Choose_cost.class);
        startActivity(intent);
    }

    public void onIncomeSettingsClick(View view) {
        Intent intent = new Intent(MainActivity.this, Choose_income.class);
        startActivity(intent);
    }

    public void onPlanningClick(View view) {
        Intent intent = new Intent(MainActivity.this, PlanningOfCosts.class);
        startActivity(intent);
    }
}