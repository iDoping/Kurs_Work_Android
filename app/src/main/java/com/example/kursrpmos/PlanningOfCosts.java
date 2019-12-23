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

import java.util.ArrayList;

public class PlanningOfCosts extends AppCompatActivity implements CustomDialogFragmentPlans.DialogListenerPlans {

    ListView PlansList;
    DBHelper dbHelper;
    ArrayList<String> PlansListItem;
    ArrayAdapter adapter;
    TextView SelectedPlan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning_of_costs);

        SelectedPlan = findViewById(R.id.SelectedPlan);
        dbHelper = new DBHelper(this);
        PlansListItem = new ArrayList<>();
        PlansList = findViewById(R.id.PlansList);
        SelectPlansToList();

        PlansList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String text = PlansList.getItemAtPosition(position).toString();
                String str [] = text.split(":");
                SelectedPlan.setText(str[0].trim());
            }
        });
    }

    static final private int CHOOSE_PREP = 0;

    public void onAddNewPlanClick(View view) {
        Intent intent = new Intent(PlanningOfCosts.this, AddNewPlans.class);
        startActivityForResult(intent, CHOOSE_PREP);
    }

    public void onDeleteNewPlanClick(View view) {
            CustomDialogFragmentPlans dialog = new CustomDialogFragmentPlans();
            dialog.show(getSupportFragmentManager(), "plans");
    }

    public void OnYesClickedPlans() {

            dbHelper.TESTPLANS(SelectedPlan.getText().toString());
            PlansListItem.clear();
            SelectPlansToList();
            SelectedPlan.setText("");
            //dbHelper.LabelsOfCosts();
            Toast.makeText(getApplicationContext(), "Лимит удален", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String label1 = data.getStringExtra(AddNewPlans.TEMP3);
            String label2 = data.getStringExtra(AddNewPlans.TEMP4);
            dbHelper.insertPlan(label1,label2);
        }
        PlansListItem.clear();
        SelectPlansToList();
        SelectedPlan.setText("");
        //dbHelper.LabelsOfCosts();
    }

    public void SelectPlansToList() {
        Cursor cursor = dbHelper.SelectPlansToList();

        while (cursor.moveToNext()) {
            String temp = cursor.getString(1);
            if (cursor.getString(1) == null){
                temp = "0";
            }
            PlansListItem.add(cursor.getString(0)+ " : " + temp + " рублей");
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, PlansListItem);
        PlansList.setAdapter(adapter);
    }
}
