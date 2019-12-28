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

public class Choose_costTest {

    @Rule
    public ActivityTestRule<Choose_cost> mActivityRule = new ActivityTestRule<>(Choose_cost.class);

    @Test
    public void onDeleteNewCostClick() {
        Espresso.onData(anything()).inAdapterView(withId(R.id.costCategoryList)).atPosition(0).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.btnDeleteNewCost)).perform(click());
        Espresso.onView(withText("При удалении категории удалятся все данные,связанные с ней. Продолжить?")).inRoot(isDialog()).check(matches(isDisplayed()));
        Espresso.onView(withId(android.R.id.button1)).perform(click());
    }
}