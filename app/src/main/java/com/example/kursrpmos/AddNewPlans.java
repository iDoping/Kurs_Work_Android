package com.example.kursrpmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddNewPlans extends AppCompatActivity {

    Button btnAddNewPlan;
    EditText etPlanAmount;
    Spinner spinPlanCat;
    DBHelper dbHelper;

    public final static String TEMP3 = "com.example.kursrpmos.AddNewPlans.TEMP3";
    public final static String TEMP4 = "com.example.kursrpmos.AddNewPlans.TEMP4";

    /**
     * Задаёт начальную установку параметров при инициализации активности
     *
     * @param savedInstanceState Сохраненное состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_plans);

        btnAddNewPlan = findViewById(R.id.btnAddNewPlan);
        etPlanAmount = findViewById(R.id.etPlanAmount);
        etPlanAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
        spinPlanCat = findViewById(R.id.spinPlanCat);
        dbHelper = new DBHelper(this);
        loadSpinnerData();
    }

    /**
     * Устанавливает лимит на выбраную категорию
     *
     * @param view Параметр, отвечающий за отображение
     */
    public void onAddNewPlanClick(View view) {

        Intent answerIntent = new Intent();
        String label1 = etPlanAmount.getText().toString();
        String label2 = spinPlanCat.getSelectedItem().toString();
        if (label1.trim().length() > 0) {

            setResult(RESULT_OK, answerIntent);
            answerIntent.putExtra(TEMP3, label1);
            answerIntent.putExtra(TEMP4, label2);
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "Пожалуйста, введите планируемую сумму",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Загружает все имена категорий доходов в объект spinner
     */
    public void loadSpinnerData() {
        List<String> lables = dbHelper.getAllCosts();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPlanCat.setAdapter(dataAdapter);
    }
}