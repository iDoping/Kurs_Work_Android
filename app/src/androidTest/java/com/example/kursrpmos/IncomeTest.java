package com.example.kursrpmos;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class IncomeTest {
    @Rule
    public ActivityTestRule<Income> mActivityRule = new ActivityTestRule<>(Income.class);

    @Test
    public void onSelectIncomeDateClick() {
        Espresso.onView(withId(R.id.btnAddIncDate)).perform(click());
        Espresso.onView(withId(android.R.id.button1)).perform(click());
    }

    @Test
    public void onAddIncomesClick() {
        Espresso.onView(ViewMatchers.withId(R.id.etSumIncome)).perform(ViewActions.typeText("2500"));
        Espresso.onView(ViewMatchers.withId(R.id.btnAddIncomes)).perform(ViewActions.click());
    }
}