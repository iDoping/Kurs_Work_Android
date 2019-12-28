package com.example.kursrpmos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    Button btnDelete;

    /**
     * Задаёт начальную установку параметров при инициализации активности
     *
     * @param savedInstanceState Сохраненное состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_cost);
        btnDelete = findViewById(R.id.btnDeleteNewCost);
        selectedCost = findViewById(R.id.SelectedCost);
        dbHelper = new DBHelper(this);
        listItem = new ArrayList<>();
        costCategoryList = findViewById(R.id.costCategoryList);
        selectCostsToList();

        costCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Устанавливает выбранный элемент списка в textView
             * @param parent Определение интерфейса для обратного вызова, который будет вызываться при нажатии на элемент в этом AdapterView
             * @param view Нажатый пункт
             * @param position Порядковый номер пункта в списке
             * @param id Идентификатор элемента
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = costCategoryList.getItemAtPosition(position).toString();
                selectedCost.setText(text);
            }
        });
    }

    static final private int CHOOSE_PREP = 0;

    /**
     * Перход во вкладку добавления новой категории расхода
     *
     * @param view параметр отвечающий за отображение
     */
    public void onAddNewCostClick(View view) {
        Intent intent = new Intent(Choose_cost.this, Dialog_costs.class);
        startActivityForResult(intent, CHOOSE_PREP);
    }

    /**
     * Вызов диалогового окна для удаления выбранной категории расхода
     *
     * @param view параметр отвечающий за отображение
     */
    public void onDeleteNewCostClick(View view) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");
    }

    /**
     * Удаляет выбранную категорию расхода
     */
    public void onYesClicked() {
        if (listItem.size() == 1) {
            Toast.makeText(getApplicationContext(), "Должна остаться хотя-бы одна категория. Сначала создайте новую категорию", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.deleteCost(selectedCost.getText().toString());
            listItem.clear();
            selectCostsToList();
            selectedCost.setText("");
        }
    }

    /**
     * Добавление категорий расходов в listView
     */
    public void selectCostsToList() {
        Cursor cursor = dbHelper.selectCostsToList();

        while (cursor.moveToNext()) {
            listItem.add(cursor.getString(0));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
        costCategoryList.setAdapter(adapter);
    }

    /**
     * Получение данных от вызываемой Activity
     *
     * @param requestCode ID  Используется, чтобы отличать друг от друга пришедшие результаты
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
        listItem.clear();
        selectCostsToList();
        selectedCost.setText("");
    }
}