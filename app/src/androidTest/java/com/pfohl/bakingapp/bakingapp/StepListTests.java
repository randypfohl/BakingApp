package com.pfohl.bakingapp.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.pfohl.bakingapp.bakingapp.RecipeList.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static org.hamcrest.core.IsNot.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StepListTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mIdlingResource);
        try {
            Thread.sleep(2000);
        }catch(Exception e){

        }
    }

    @Test
    public void TestThatVideoPlayerDisplays() {
        boolean isTablet = isTablet(mActivityTestRule.getActivity());
        if(!isTablet) {
            onView(withId(R.id.recipe_rv)).perform(actionOnItemAtPosition(0, click()));
            onView(withId(R.id.step_rv)).perform(actionOnItemAtPosition(3, click()));
            onView(withId(R.id.video_view)).check(matches(isDisplayed()));
        }
        else {
            onView(withId(R.id.recipe_tablet_rv)).perform(actionOnItemAtPosition(0, click()));
            onView(withId(R.id.step_rv)).perform(actionOnItemAtPosition(3, click()));
            onView(withId(R.id.video_view)).check(matches(isDisplayed()));
            onView(withId(R.id.step_rv)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void TestThatShowsMasterDetailFlow() {
        boolean isTablet = isTablet(mActivityTestRule.getActivity());
        if(!isTablet) {
            onView(withId(R.id.recipe_rv)).perform(actionOnItemAtPosition(0, click()));
            onView(withId(R.id.step_rv)).perform(actionOnItemAtPosition(4, click()));
            onView(withId(R.id.video_view)).check(matches(isDisplayed()));
        }
        else {
            onView(withId(R.id.recipe_tablet_rv)).perform(actionOnItemAtPosition(0, click()));
            onView(withId(R.id.step_rv)).perform(actionOnItemAtPosition(4, click()));
            onView(withId(R.id.video_view)).check(matches(isDisplayed()));
            onView(withId(R.id.step_rv)).check(matches(isDisplayed()));
        }

    }

    @Test
    public void TestThatNavigatesThroughDetails() {
        boolean isTablet = isTablet(mActivityTestRule.getActivity());
        if(!isTablet) {
            onView(withId(R.id.recipe_rv)).perform(actionOnItemAtPosition(0, click()));
            onView(withId(R.id.step_rv)).perform(actionOnItemAtPosition(4, click()));
            onView(withId(R.id.video_view)).check(matches(isDisplayed()));
            onView(withId(R.id.back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.back_button)).perform(click());
        }
        else {
            onView(withId(R.id.recipe_tablet_rv)).perform(actionOnItemAtPosition(0, click()));
            onView(withId(R.id.step_rv)).perform(actionOnItemAtPosition(4, click()));
            onView(withId(R.id.video_view)).check(matches(isDisplayed()));
            onView(withId(R.id.back_button)).check(matches(isDisplayed()));
            onView(withId(R.id.back_button)).perform(click());
        }

    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);

        }
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
