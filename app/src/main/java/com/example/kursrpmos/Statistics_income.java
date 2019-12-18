package com.example.kursrpmos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Statistics_income extends AppCompatActivity {

    TextView tvStartDateIncome, tvEndDateIncome, tvStartDateEIncome, tvEndDateEIncome;
    Calendar dateAndTimeIncome = Calendar.getInstance();
    BarChart barChartIncome;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_income);

        tvStartDateIncome = findViewById(R.id.tvStartDateIncome);
        tvEndDateIncome = findViewById(R.id.tvEndDateIncome);

        tvStartDateEIncome = findViewById(R.id.tvStartDateEIncome);
        tvEndDateEIncome = findViewById(R.id.tvEndDateEIncome);

        tvStartDateEIncome.setVisibility(View.INVISIBLE);
        tvEndDateEIncome.setVisibility(View.INVISIBLE);

        barChartIncome = findViewById(R.id.barchart_income);

        dbHelper = new DBHelper(this);
    }

    public void onSetStartIncomeCLick(View view) {

        new DatePickerDialog(Statistics_income.this, d, dateAndTimeIncome.get(Calendar.YEAR), dateAndTimeIncome.get(Calendar.MONTH), dateAndTimeIncome.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String s = "";
            if (dayOfMonth < 10) {
                s = "0" + dayOfMonth;
                String date2 = year + "/" + (monthOfYear + 1) + "/" + s;
                tvStartDateEIncome.setText(date2);
            } else {
                String date2 = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                tvStartDateEIncome.setText(date2);
            }
            tvStartDateIncome.setText(date);
        }
    };

    public void onSetEndDateIncome(View view) {
        new DatePickerDialog(Statistics_income.this, d2, dateAndTimeIncome.get(Calendar.YEAR), dateAndTimeIncome.get(Calendar.MONTH), dateAndTimeIncome.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d2 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String s = "";
            if (dayOfMonth < 10) {
                s = "0" + dayOfMonth;
                String date2 = year + "/" + (monthOfYear + 1) + "/" + s;
                tvEndDateEIncome.setText(date2);
            } else {
                String date2 = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                tvEndDateEIncome.setText(date2);
            }
            tvEndDateIncome.setText(date);
        }
    };

    public void onMakeStatIncomeClick(View view) {
        AddDataToGraph();
    }

    public void AddDataToGraph() {
        String stDate = tvStartDateEIncome.getText().toString();
        String endDate = tvEndDateEIncome.getText().toString();

        ArrayList<BarEntry> barEntries = dbHelper.getBarEntriesIncomes(stDate, endDate);
        BarDataSet barDataSet = new BarDataSet(barEntries, "Расходы за указанный период");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        final List<String> lables = dbHelper.test2();

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);
        barChartIncome.animateY(2000);

        barChartIncome.setData(barData);
        XAxis xAxis = barChartIncome.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(lables));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        barChartIncome.getDescription().setEnabled(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(lables.size());
        barChartIncome.invalidate();
    }
}
