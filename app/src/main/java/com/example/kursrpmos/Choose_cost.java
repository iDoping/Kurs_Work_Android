package com.example.kursrpmos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Choose_cost extends AppCompatActivity implements CustomDialogFragment.DialogListenerCosts {

    ListView costCategoryList;
    DBHelper dbHelper;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    TextView selectedCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_cost);
        selectedCost = findViewById(R.id.SelectedCost);
        dbHelper = new DBHelper(this);
        listItem = new ArrayList<>();
        costCategoryList = findViewById(R.id.costCategoryList);
        selectCostsToList();

        costCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = costCategoryList.getItemAtPosition(position).toString();
                selectedCost.setText(text);
            }
        });
    }

    static final private int CHOOSE_PREP = 0;

    public void onAddNewCostClick(View view) {
        Intent intent = new Intent(Choose_cost.this, Dialog_costs.class);
        startActivityForResult(intent, CHOOSE_PREP);
    }

    public void onDeleteNewCostClick(View view) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }

    public void onYesClicked() {
        if (listItem.size() == 1)
        {
            Toast.makeText(getApplicationContext(), "Должна остаться хотя-бы одна категория. Сначала создайте новую категорию", Toast.LENGTH_SHORT).show();
        }
        else {
            dbHelper.deleteCost(selectedCost.getText().toString());
            listItem.clear();
            selectCostsToList();
            selectedCost.setText("");
        }
    }

    public void selectCostsToList() {
        Cursor cursor = dbHelper.selectCostsToList();

        while (cursor.moveToNext()) {
            listItem.add(cursor.getString(0));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
        costCategoryList.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String label = data.getStringExtra(Dialog_costs.TEMP);
            dbHelper.insertTypeCost(label);
        }
        listItem.clear();
        selectCostsToList();
        selectedCost.setText("");
    }
}