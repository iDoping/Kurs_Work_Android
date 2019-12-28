package com.example.kursrpmos;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;

public class PlanningOfCostsTest {

    @Rule
    public ActivityTestRule<PlanningOfCosts> mActivityRule = new ActivityTestRule<>(PlanningOfCosts.class);

    @Test
    public void onDeleteNewPlanClick() {
        Espresso.onData(anything()).inAdapterView(withId(R.id.PlansList)).atPosition(0).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.btnDeleteNewPlan)).perform(click());
        Espresso.onView(withText("Лимит выбранной категории будет удален и установлен в значение 0, продолжить?")).inRoot(isDialog()).check(matches(isDisplayed()));
        Espresso.onView(withId(android.R.id.button1)).perform(click());
    }
}