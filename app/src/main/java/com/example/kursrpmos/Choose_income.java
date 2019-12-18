package com.example.kursrpmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Choose_income extends AppCompatActivity implements CustomDialogFragmentIncomes.DialogListenerIncomes {

    ListView incomeCategoryList;
    DBHelper dbHelper;
    ArrayList<String> listItemIncomes;
    ArrayAdapter adapterincomes;
    TextView SelectedIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_income);
        SelectedIncome = findViewById(R.id.SelectedIncome);
        dbHelper = new DBHelper(this);
        listItemIncomes = new ArrayList<>();
        incomeCategoryList = findViewById(R.id.incomeCategoryList);
        SelectIncomesToList();

        incomeCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = incomeCategoryList.getItemAtPosition(position).toString();
                SelectedIncome.setText(text);
            }
        });
    }

    static final private int CHOOSE_PREP2 = 0;

    public void onAddNewIncomeClick(View view) {
        Intent intent = new Intent(Choose_income.this, Dialog_incomes.class);
        startActivityForResult(intent, CHOOSE_PREP2);
    }

    public void onDeleteNewIncomeClick(View view) {
        CustomDialogFragmentIncomes dialog = new CustomDialogFragmentIncomes();
        dialog.show(getSupportFragmentManager(), "custom");
    }

    public void OnYesClickedIncomes() {
        dbHelper.DeleteIncome(SelectedIncome.getText().toString());
        listItemIncomes.clear();
        SelectIncomesToList();
        SelectedIncome.setText("");
        dbHelper.LabelsOfIncomes();
    }

    public void SelectIncomesToList() {
        Cursor cursor = dbHelper.SelectIncomesToList();

        while (cursor.moveToNext()) {
            listItemIncomes.add(cursor.getString(1));
        }
        adapterincomes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItemIncomes);
        incomeCategoryList.setAdapter(adapterincomes );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String label = data.getStringExtra(Dialog_incomes.TEMP2);
            dbHelper.insertLabel2(label);
        }
        listItemIncomes.clear();
        SelectIncomesToList();
        SelectedIncome.setText("");
        dbHelper.LabelsOfIncomes();
    }
}