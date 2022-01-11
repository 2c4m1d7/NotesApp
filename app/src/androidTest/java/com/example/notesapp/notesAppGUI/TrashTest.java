package com.example.notesapp.notesAppGUI;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.notesapp.R;



@RunWith(AndroidJUnit4ClassRunner.class)
public class TrashTest {

    @Rule
    public ActivityScenarioRule<Trash> mActivityTestRule = new ActivityScenarioRule<>(Trash.class);

    @Test
    public void menuOptionsTest() {
        onView(withContentDescription("Navigate up")).check(matches(isDisplayed()));
        onView(withContentDescription("More options")).perform(click());
        onView(allOf(withId(R.id.title), withText("Clear all"), childAtPosition(childAtPosition(withId(R.id.content),0),0))).check(matches(isDisplayed()));
    }

    @Test
    public void recyclerViewTest(){
        onView(withId(R.id.trashRecyclerView)).check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
