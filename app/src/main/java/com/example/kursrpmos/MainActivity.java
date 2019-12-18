package com.example.kursrpmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}