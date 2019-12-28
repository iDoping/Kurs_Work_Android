package com.example.kursrpmos;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.junit.Assert.*;

public class AddNewPlansTest {

    @Rule
    public ActivityTestRule<AddNewPlans> mActivityRule = new ActivityTestRule<>(AddNewPlans.class);

    @Test
    public void onAddNewPlanClick() {
        Espresso.onView(ViewMatchers.withId(R.id.etPlanAmount)).perform(ViewActions.typeText("2500")).perform(closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.btnAddNewPlan)).perform(ViewActions.click());
    }
}