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

public class CostsTest {

    @Rule
    public ActivityTestRule<Costs> mActivityRule = new ActivityTestRule<>(Costs.class);

    @Test
    public void onSelectDateClick() {
        Espresso.onView(withId(R.id.btnAddCostDate)).perform(click());
        Espresso.onView(withId(android.R.id.button1)).perform(click());
    }

    @Test
    public void onAddCostsClick() {
        Espresso.onView(ViewMatchers.withId(R.id.etSumm)).perform(ViewActions.typeText("2500"));
        Espresso.onView(ViewMatchers.withId(R.id.btnAddCosts)).perform(ViewActions.click());
    }
}