package com.example.kursrpmos;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class Statistics_incomeTest {

    @Rule
    public ActivityTestRule<Statistics_income> mActivityRule = new ActivityTestRule<>(Statistics_income.class);

    @Test
    public void onMakeStatIncomeClick() {
        Espresso.onView(withId(R.id.btnSetStartIncome)).perform(click());
        Espresso.onView(withId(android.R.id.button1)).perform(click());
        Espresso.onView(withId(R.id.btnSetEndDateIncome)).perform(click());
        Espresso.onView(withId(android.R.id.button1)).perform(click());
        Espresso.onView(withId(R.id.btnMakeStatIncome)).perform(click());
    }
}