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

    TextView tvCostDateForSQL, tvPlansText;
    Button btnAddCosts;
    DBHelper dbHelper;
    EditText etSum, etDate;
    Spinner spinner;
    Calendar dateAndTime = Calendar.getInstance();

    /**
     * Задаёт начальную установку параметров при инициализации активности
     *
     * @param savedInstanceState Сохраненное состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_costs);

        btnAddCosts = findViewById(R.id.btnAddCosts);

        etSum = findViewById(R.id.etSumm);
        etSum.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
        etDate = findViewById(R.id.etDate);

        dbHelper = new DBHelper(this);

        spinner = findViewById(R.id.spinCostCat);
        tvCostDateForSQL = findViewById(R.id.tvCostDateForSQL);
        tvCostDateForSQL.setVisibility(View.INVISIBLE);
        tvPlansText = findViewById(R.id.tvPlansText);
        setInitialDateTime();
        setInitialDateTimeForSQLite();
        loadSpinnerData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             *
             * @param adapter Определение интерфейса для обратного вызова, который будет вызываться при нажатии на элемент в этом AdapterView
             * @param v Нажатый пункт
             * @param i Порядковый номер пункта в списке
             * @param lng Идентификатор элемента
             */
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

                String value = adapter.getItemAtPosition(i).toString();
                String temp = dbHelper.getCostsPlans(value);
                if (temp == null) {
                    tvPlansText.setText("");
                } else {
                    if (Double.valueOf(temp) < 0) {
                        tvPlansText.setText("Лимит категории " + value + " превышен");
                    } else {
                        tvPlansText.setText("Лимит категории " + value + " составляет " + temp + " рублей");
                    }
                }
            }

            /**
             * Ничего не выбрано
             * @param parentView Определение интерфейса для обратного вызова, который будет вызываться при нажатии на элемент в этом AdapterView
             */
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    /**
     * Добавление нового расхода
     *
     * @param view Параметр отвечающий за отображение
     */
    public void onAddCostsClick(View view) {

        String sum = etSum.getText().toString();
        String cost_cat = spinner.getSelectedItem().toString();
        String DateForSQLite = tvCostDateForSQL.getText().toString();

        String lol = dbHelper.getCostsPlans(cost_cat);
        if (lol == null) {
            if (sum.equals("")) {
                Toast.makeText(getApplicationContext(), "Заполните поле Сумма", Toast.LENGTH_SHORT).show();
            } else {
                tvPlansText.setText("");
                dbHelper.insertCost(sum, DateForSQLite, cost_cat);
                etSum.setText("");
                Toast.makeText(getApplicationContext(), "Расход добавлен", Toast.LENGTH_SHORT).show();
            }
        } else {

            if (sum.equals("")) {
                Toast.makeText(getApplicationContext(), "Заполните поле Сумма", Toast.LENGTH_SHORT).show();
            } else {

                double start_value = Double.valueOf(dbHelper.getCostsPlans(cost_cat));
                double end_value = Double.valueOf(sum);
                double result = start_value - end_value;
                dbHelper.insertChanges(result, cost_cat);
                dbHelper.insertCost(sum, DateForSQLite, cost_cat);
                etSum.setText("");
                Toast.makeText(getApplicationContext(), "Расход добавлен", Toast.LENGTH_SHORT).show();
                String temp = dbHelper.getCostsPlans(cost_cat);
                if (Double.valueOf(temp) < 0) {
                    tvPlansText.setText("Лимит категории " + cost_cat + " превышен");
                } else {
                    tvPlansText.setText("Лимит категории " + cost_cat + " составляет " + temp + " рублей");
                }
            }
        }
    }

    /**
     * Установка выбранной даты
     *
     * @param view Параметр отвечающий за отображение
     */
    public void onSelectDateClick(View view) {
        new DatePickerDialog(Costs.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Установка даты, которую видит пользователь
     */
    private void setInitialDateTime() {
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        etDate.setText(thisDate);
    }

    /**
     * Установка даты, которая записывается в базу данных
     */
    private void setInitialDateTimeForSQLite() {
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        tvCostDateForSQL.setText(thisDate);
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
                tvCostDateForSQL.setText(date2);
            } else {
                String date2 = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                tvCostDateForSQL.setText(date2);
            }
            etDate.setText(date);
        }
    };

    /**
     * Получение всех категорий расходов
     */
    public void loadSpinnerData() {
        List<String> lables = dbHelper.getAllCosts();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    static final private int CHOOSE_PREP = 0;

    /**
     * Вызов диалога для добавления категории расхода
     *
     * @param view Параметр отвечающий за отображение
     */
    public void onDialogClick(View view) {
        Intent intent = new Intent(Costs.this, Dialog_costs.class);
        startActivityForResult(intent, CHOOSE_PREP);
    }

    /**
     * Получение данных от вызаемой Activity
     *
     * @param requestCode Используется, чтобы отличать друг от друга пришедшие результаты
     * @param resultCode  Позволяет определить успешно прошел вызов или нет
     * @param data        Содержит данные с предыдущего Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String label = data.getStringExtra(Dialog_costs.TEMP);
            dbHelper.insertTypeCost(label);
        }
        loadSpinnerData();
    }
}