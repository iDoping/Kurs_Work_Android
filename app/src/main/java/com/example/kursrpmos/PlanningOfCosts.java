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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PlanningOfCosts extends AppCompatActivity implements CustomDialogFragmentPlans.DialogListenerPlans {

    ListView plansList;
    DBHelper dbHelper;
    ArrayList<String> plansListItem;
    ArrayAdapter adapter;
    TextView selectedPlan;
    Date currentDate = new Date();
    DateFormat dateFormat = new SimpleDateFormat("MM", Locale.getDefault());
    final String MONTH = dateFormat.format(currentDate);


    /**
     * Задаёт начальную установку параметров при инициализации активности
     *
     * @param savedInstanceState Сохраненное состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_of_costs);

        selectedPlan = findViewById(R.id.SelectedPlan);
        dbHelper = new DBHelper(this);
        plansListItem = new ArrayList<>();
        plansList = findViewById(R.id.PlansList);
        selectPlansToList();

        plansList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             *
             * @param parent Определение интерфейса для обратного вызова, который будет вызываться при нажатии на элемент в этом AdapterView
             * @param view Нажатый пункт
             * @param position Порядковый номер пункта в списке
             * @param id Идентификатор элемента
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String text = plansList.getItemAtPosition(position).toString();
                String str[] = text.split(":");
                selectedPlan.setText(str[0].trim());
            }
        });
    }

    static final private int CHOOSE_PREP = 0;

    /**
     * Перход во вкладку добавления нового лимита
     *
     * @param view Параметр отвечающий за отображение
     */
    public void onAddNewPlanClick(View view) {
        Intent intent = new Intent(PlanningOfCosts.this, AddNewPlans.class);
        startActivityForResult(intent, CHOOSE_PREP);
    }

    /**
     * Вызов диалогового окна для удаления лимита
     *
     * @param view Параметр отвечающий за отображение
     */
    public void onDeleteNewPlanClick(View view) {
        CustomDialogFragmentPlans dialog = new CustomDialogFragmentPlans();
        dialog.show(getSupportFragmentManager(), "plans");
    }

    /**
     * Удаляет выбранный лимит
     */
    public void onYesClickedPlans() {
        dbHelper.deletePlans(selectedPlan.getText().toString());
        plansListItem.clear();
        selectPlansToList();
        selectedPlan.setText("");
        Toast.makeText(getApplicationContext(), "Лимит удален", Toast.LENGTH_SHORT).show();
    }

    /**
     * @param requestCode Используется, чтобы отличать друг от друга пришедшие результаты
     * @param resultCode  Позволяет определить успешно прошел вызов или нет
     * @param data        Cодержит данные с предыдущего Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String label1 = data.getStringExtra(AddNewPlans.TEMP3);
            String label2 = data.getStringExtra(AddNewPlans.TEMP4);
            dbHelper.insertPlan(label1, label2, MONTH);
        }
        plansListItem.clear();
        selectPlansToList();
        selectedPlan.setText("");
    }

    /**
     * Добавление всех лимитов и категорий расходов в список
     */
    public void selectPlansToList() {
        Cursor cursor = dbHelper.selectPlansToList();

        while (cursor.moveToNext()) {
            String temp = cursor.getString(1);
            if (cursor.getString(1) == null) {
                temp = "0";
            }
            plansListItem.add(cursor.getString(0) + " : " + temp + " рублей");
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, plansListItem);
        plansList.setAdapter(adapter);
    }
}