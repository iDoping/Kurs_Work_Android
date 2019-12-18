package com.example.kursrpmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dialog_costs extends AppCompatActivity {

    Button btnCancelCost, btnAddNewCost;
    EditText etAddCostDialog;

    public final static String TEMP = "com.example.kursrpmos.Dialog_costs.TEMP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_costs);

        btnAddNewCost = findViewById(R.id.btnAddNewCost);
        btnCancelCost = findViewById(R.id.btnCancelCost);

        etAddCostDialog = findViewById(R.id.etAddCostDialog);
    }

    public void OnCancelCostClick(View view) {
        finish();
    }

    public void onAddNewCostClick(View view) {
        Intent answerIntent = new Intent();
        String label = etAddCostDialog.getText().toString();
        if (label.trim().length() > 0) {

            setResult(RESULT_OK, answerIntent);
            answerIntent.putExtra(TEMP, label);
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "Пожалуйста, введите название категории",
                    Toast.LENGTH_SHORT).show();
        }
    }
}