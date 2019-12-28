package com.example.kursrpmos;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.junit.Assert.*;

public class Dialog_incomesTest {

    @Rule
    public ActivityTestRule<Dialog_incomes> mActivityRule = new ActivityTestRule<>(Dialog_incomes.class);

    @Test
    public void onAddNewIncomeClick() {
        Espresso.onView(ViewMatchers.withId(R.id.etAddIncomeDialog)).perform(ViewActions.typeText("Example")).perform(closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.btnAddNewIncome)).perform(ViewActions.click());
    }
}