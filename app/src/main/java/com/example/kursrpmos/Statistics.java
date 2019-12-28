package com.example.kursrpmos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Statistics extends AppCompatActivity {

    TextView tvStartDate, tvEndDate, tvStartDateE, tvEndDateE;
    Button btnSetStartCosts,btnSetEndDateCosts,btnMakeStatCosts;
    Calendar dateAndTime = Calendar.getInstance();
    BarChart barChart;
    DBHelper dbHelper;

    /**
     * Задаёт начальную установку параметров при инициализации активности
     *
     * @param savedInstanceState Сохраненное состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);

        btnSetStartCosts = findViewById(R.id.btnSetStartCosts);
        btnSetEndDateCosts = findViewById(R.id.btnSetEndDateCosts);
        btnMakeStatCosts = findViewById(R.id.btnMakeStatCosts);

        tvStartDateE = findViewById(R.id.tvStartDateE);
        tvEndDateE = findViewById(R.id.tvEndDateE);

        tvStartDateE.setVisibility(View.INVISIBLE);
        tvEndDateE.setVisibility(View.INVISIBLE);

        barChart = findViewById(R.id.barchart);

        dbHelper = new DBHelper(this);
    }

    /**
     * Установка выбранной даты
     *
     * @param view Параметр отвечающий за отображение
     */
    public void onSetStartDateCLick(View view) {
        new DatePickerDialog(Statistics.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        /**
         * Получение параметров даты
         * @param view Отображение календаря
         * @param year Год
         * @param monthOfYear Месяц года
         * @param dayOfMonth День месяца
         */
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String s = "";
            if (dayOfMonth < 10) {
                s = "0" + dayOfMonth;
                String date2 = year + "/" + (monthOfYear + 1) + "/" + s;
                tvStartDateE.setText(date2);
            } else {
                String date2 = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                tvStartDateE.setText(date2);
            }
            tvStartDate.setText(date);

        }
    };

    /**
     * Установка выбранной даты
     *
     * @param view Параметр отвечающий за отображение
     */
    public void onEndStartDateCLick(View view) {
        new DatePickerDialog(Statistics.this, d2, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d2 = new DatePickerDialog.OnDateSetListener() {

        /**
         * Получение параметров даты
         * @param view Отображение календаря
         * @param year Год
         * @param monthOfYear Месяц года
         * @param dayOfMonth День месяца
         */
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String s = "";
            if (dayOfMonth < 10) {
                s = "0" + dayOfMonth;
                String date2 = year + "/" + (monthOfYear + 1) + "/" + s;
                tvEndDateE.setText(date2);
            } else {
                String date2 = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                tvEndDateE.setText(date2);
            }
            tvEndDate.setText(date);
        }
    };

    /**
     * Построение графика по расходам
     *
     * @param view Параметр отвечающий за отображение
     */
    public void onMakeStatClick(View view) {

        addDataToGraph();
    }

    /**
     * Добавление данных в столбчатую диаграмму
     */
    public void addDataToGraph() {
        String stDate = tvStartDateE.getText().toString();
        String endDate = tvEndDateE.getText().toString();

        ArrayList<BarEntry> barEntries = dbHelper.getBarEntriesCosts(stDate, endDate);
        BarDataSet barDataSet = new BarDataSet(barEntries, "Расходы за указанный период");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        final List<String> labels = dbHelper.getCostsBarLabel();

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);
        barChart.animateY(2000);

        barChart.setData(barData);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        barChart.getDescription().setEnabled(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());
        barChart.invalidate();
    }
}