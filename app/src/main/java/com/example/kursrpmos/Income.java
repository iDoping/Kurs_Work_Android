package com.example.kursrpmos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

public class Income extends AppCompatActivity {

    TextView tvIncomeDateForSQL;
    Button btnAddIncomes;
    DBHelper dbHelper;
    EditText etSumIncome, etDateIncome;
    Spinner spinIncCat;
    Calendar dateAndTime = Calendar.getInstance();

    /**
     * Задаёт начальную установку параметров при инициализации активности
     *
     * @param savedInstanceState Сохраненное состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        btnAddIncomes = findViewById(R.id.btnAddIncomes);

        etSumIncome = findViewById(R.id.etSumIncome);
        etSumIncome.setInputType(InputType.TYPE_CLASS_NUMBER);
        etDateIncome = findViewById(R.id.etDateIncome);

        dbHelper = new DBHelper(this);

        spinIncCat = findViewById(R.id.spinIncCat);

        tvIncomeDateForSQL = findViewById(R.id.tvIncomeDateForSQL);
        tvIncomeDateForSQL.setVisibility(View.INVISIBLE);

        setInitialDateTime();
        setInitialDateTimeForSQLite();
        loadSpinnerData();
    }

    /**
     * Добавление нового дохода
     *
     * @param view Параметр отвечающий за отображение
     */
    public void onAddIncomesClick(View view) {

        String income_sum = etSumIncome.getText().toString();
        String income_date = tvIncomeDateForSQL.getText().toString();
        String income_cat = spinIncCat.getSelectedItem().toString();

        if (income_sum.equals("")) {
            Toast.makeText(getApplicationContext(), "Заполните поле Сумма", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.insertIncome(income_sum, income_date, income_cat);
            etSumIncome.setText("");
            Toast.makeText(getApplicationContext(), "Доход добавлен", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Установка выбранной даты
     *
     * @param view Параметр отвечающий за отображение
     */
    public void onSelectIncomeDateClick(View view) {
        new DatePickerDialog(Income.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * Установка даты, которую видит пользователь
     */
    private void setInitialDateTime() {
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        etDateIncome.setText(thisDate);
    }

    /**
     * Установка даты, которая записывается в базу данных
     */
    private void setInitialDateTimeForSQLite() {
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        tvIncomeDateForSQL.setText(thisDate);
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
                tvIncomeDateForSQL.setText(date2);
            } else {
                String date2 = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                tvIncomeDateForSQL.setText(date2);
            }
            etDateIncome.setText(date);
        }
    };

    /**
     * Получение всех категорий доходов
     */
    public void loadSpinnerData() {
        List<String> lables = dbHelper.getAllIncomes();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinIncCat.setAdapter(dataAdapter);
        dbHelper.close();
    }

    static final private int CHOOSE_PREP = 0;

    /**
     * Вызов диалога для добавления категории расхода
     *
     * @param view Параметр отвечающий за отображение
     */
    public void onIncomeDialogClick(View view) {
        Intent intent = new Intent(Income.this, Dialog_incomes.class);
        startActivityForResult(intent, CHOOSE_PREP);
    }

    /**
     * @param requestCode Используется, чтобы отличать друг от друга пришедшие результаты
     * @param resultCode  Позволяет определить успешно прошел вызов или нет
     * @param data        Содержит данные с предыдущего Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String label = data.getStringExtra(Dialog_incomes.TEMP2);
            dbHelper.insertTypeIncome(label);
        }
        loadSpinnerData();
    }
}