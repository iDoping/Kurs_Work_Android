package com.example.kursrpmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dialog_incomes extends AppCompatActivity {

    Button btnCancelIncome, btnAddNewIncome;
    EditText etAddIncomeDialog;

    public final static String TEMP2 = "com.example.kursrpmos.Dialog_incomes.TEMP2";

    /**
     * Задаёт начальную установку параметров при инициализации активности
     *
     * @param savedInstanceState Сохраненное состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_incomes);

        btnAddNewIncome = findViewById(R.id.btnAddNewIncome);
        btnCancelIncome = findViewById(R.id.btnCancelIncome);

        etAddIncomeDialog = findViewById(R.id.etAddIncomeDialog);
    }

    /**
     * Завершение действий данной Activity
     *
     * @param view Параметр, отвечающий за отображение
     */
    public void OnCancelIncomeClick(View view) {
        finish();
    }

    /**
     * Передает введенный пользователем текст на предыдущую Activity
     *
     * @param view Параметр, отвечающий за отображение
     */
    public void onAddNewIncomeClick(View view) {
        Intent answerIntent = new Intent();
        String label = etAddIncomeDialog.getText().toString();
        if (label.trim().length() > 0) {

            setResult(RESULT_OK, answerIntent);
            answerIntent.putExtra(TEMP2, label);
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "Пожалуйста, введите название категории",
                    Toast.LENGTH_SHORT).show();
        }
    }
}