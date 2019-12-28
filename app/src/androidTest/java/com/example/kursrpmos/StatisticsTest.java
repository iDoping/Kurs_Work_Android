package com.example.kursrpmos;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class StatisticsTest {

    @Rule
    public ActivityTestRule<Statistics> mActivityRule = new ActivityTestRule<>(Statistics.class);

    @Test
    public void onMakeStatClick() {
        Espresso.onView(withId(R.id.btnSetStartCosts)).perform(click());
        Espresso.onView(withId(android.R.id.button1)).perform(click());
        Espresso.onView(withId(R.id.btnSetEndDateCosts)).perform(click());
        Espresso.onView(withId(android.R.id.button1)).perform(click());
        Espresso.onView(withId(R.id.btnMakeStatCosts)).perform(click());
    }
}