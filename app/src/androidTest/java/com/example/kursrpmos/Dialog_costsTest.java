package com.example.kursrpmos;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.junit.Assert.*;

public class Dialog_costsTest {
    @Rule

    public ActivityTestRule<Dialog_costs> mActivityRule = new ActivityTestRule<>(Dialog_costs.class);

    @Test
    public void onAddNewCostClick() {
        Espresso.onView(ViewMatchers.withId(R.id.etAddCostDialog)).perform(ViewActions.typeText("Examle")).perform(closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.btnAddNewCost)).perform(ViewActions.click());
    }
}