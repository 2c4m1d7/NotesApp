package com.example.notesapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.notesapp.dataStorage.local.Storage;
import com.example.notesapp.noteController.TextNote;
import com.example.notesapp.notesAppGUI.MainMenu;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class IntegragtionTest {

    @Rule
    public ActivityScenarioRule<MainMenu> mActivityTestRule = new ActivityScenarioRule<>(MainMenu.class);

    String a;
    String b;
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    Storage storage = new Storage(appContext);

    @Before
    public void setup() {
        a = "a";
        b = "b";
    }

    private void delete() {
        onView(withContentDescription("More options")).perform(click());
        onView(allOf(withId(R.id.title), withText("Clear all"), childAtPosition(childAtPosition(withId(R.id.content), 0), 0))).perform(click());
        onView(withId(R.id.trash)).perform(click());
        onView(withContentDescription("More options")).perform(click());
        onView(allOf(withId(R.id.title), withText("Clear all"), childAtPosition(childAtPosition(withId(R.id.content), 0), 0))).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
    }


    @Test
    public void integrationTest() {
        delete();
//check storage size
        assertEquals(0, storage.getAllNotes().size());
//create note
        onView(withId(R.id.addNote)).perform(click());
        onView(withId(R.id.multilineEditText)).perform(typeText(a));
        onView(withContentDescription("Navigate up")).perform(click());
//check storage size and content
        assertEquals(1, storage.getAllNotes().size());
        assertEquals(a, storage.getAllNotes().get(0).getText());
        String date = storage.getAllNotes().get(0).getDate();
//open note, change text and save
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView((withId(R.id.multilineEditText))).check(matches(withText(a)));
        onView((withId(R.id.multilineEditText))).perform(replaceText(a + a));
        onView(withContentDescription("More options")).perform(click());
        onView(allOf(withId(R.id.title), withText("Save"), childAtPosition(childAtPosition(withId(R.id.content), 0), 0))).perform(click());
//check storage size and content
        assertEquals(1, storage.getAllNotes().size());
        assertEquals(a + a, storage.getAllNotes().get(0).getText());
        assertNotEquals(date, storage.getAllNotes().get(0).getDate());
//go back to the main menu
        onView(withContentDescription("Navigate up")).perform(click());
//check storage size and content
        assertEquals(1, storage.getAllNotes().size());
        assertEquals(a + a, storage.getAllNotes().get(0).getText());
//open note and check content
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView((withId(R.id.multilineEditText))).check(matches(withText(a + a)));
        onView(withContentDescription("Navigate up")).perform(click());
//create note
        onView(withId(R.id.addNote)).perform(click());
        onView(withId(R.id.multilineEditText)).perform(typeText(b));
        onView(withContentDescription("Navigate up")).perform(click());
//chek new note content and storage size
        assertEquals(2, storage.getAllNotes().size());
        assertEquals(b, storage.getAllNotes().get(1).getText());
//sort by name
        onView(withContentDescription("More options")).perform(click());
        onView(allOf(withId(R.id.title), withText("Sort"), childAtPosition(childAtPosition(withId(R.id.content), 0), 0))).perform(click());
        onView(allOf(withId(R.id.title), withText("By name"), childAtPosition(childAtPosition(withId(R.id.content), 0), 0))).perform(click());
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView((withId(R.id.multilineEditText))).check(matches(withText(a + a)));
        onView(withContentDescription("Navigate up")).perform(click());
//sort New to Old
        onView(withContentDescription("More options")).perform(click());
        onView(allOf(withId(R.id.title), withText("Sort"), childAtPosition(childAtPosition(withId(R.id.content), 0), 0))).perform(click());
        onView(allOf(withId(R.id.title), withText("New to Old"), childAtPosition(childAtPosition(withId(R.id.content), 0), 0))).perform(click());
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //  onView((withId(R.id.multilineEditText))).check(matches(withText(b)));
        onView(withContentDescription("Navigate up")).perform(click());
//sort Old to New
        onView(withContentDescription("More options")).perform(click());
        onView(allOf(withId(R.id.title), withText("Sort"), childAtPosition(childAtPosition(withId(R.id.content), 0), 0))).perform(click());
        onView(allOf(withId(R.id.title), withText("Old to New"), childAtPosition(childAtPosition(withId(R.id.content), 0), 0))).perform(click());
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // onView((withId(R.id.multilineEditText))).check(matches(withText(a+a)));
        onView(withContentDescription("Navigate up")).perform(click());
//delete with swipe from mainmenu and trash activity
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        assertEquals(1, storage.getAllNotes().size());
        onView(allOf(withId(R.id.snackbar_action), withText("Cancel"), childAtPosition(childAtPosition(withClassName(is("com.google.android.material.snackbar.Snackbar$SnackbarLayout")), 0), 1))).perform(click());
        assertEquals(2, storage.getAllNotes().size());
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        onView(withId(R.id.trash)).perform(click());
        assertEquals(1, storage.getAllNotes().size());
        assertEquals(a + a, storage.getAllNotes().get(0).getText());
        onView(withId(R.id.trashRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        //  onView(allOf(withId(R.id.snackbar_action), withText("Cancel"), childAtPosition(childAtPosition(withClassName(is("com.google.android.material.snackbar.Snackbar$SnackbarLayout")), 0), 1), isDisplayed())).perform(click());
        // onView(withId(R.id.trashRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        onView(withContentDescription("Navigate up")).perform(click());
//delete and restore
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        assertEquals(0, storage.getAllNotes().size());
        onView(withId(R.id.trash)).perform(click());
        onView(withId(R.id.trashRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
        assertEquals(1, storage.getAllNotes().size());
        // onView(allOf(withId(R.id.snackbar_action), withText("Cancel"), childAtPosition(childAtPosition(withClassName(is("com.google.android.material.snackbar.Snackbar$SnackbarLayout")), 0), 1))).perform(click());
        // onView(withId(R.id.trashRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
        assertEquals(1, storage.getAllNotes().size());
        assertEquals(0, ((TextNote) storage.getAllNotes().values().toArray()[0]).getId());
        assertEquals(a + a, storage.getAllNotes().get(0).getText());
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView((withId(R.id.multilineEditText))).check(matches(withText(a + a)));
        onView(withContentDescription("Navigate up")).perform(click());
//clear all and restore
        onView(withId(R.id.addNote)).perform(click());
        onView(withId(R.id.multilineEditText)).perform(typeText(a));
        onView(withContentDescription("Navigate up")).perform(click());
        assertEquals(2, storage.getAllNotes().size());
        onView(withContentDescription("More options")).perform(click());
        onView(allOf(withId(R.id.title), withText("Clear all"), childAtPosition(childAtPosition(withId(R.id.content), 0), 0))).perform(click());
        assertEquals(0, storage.getAllNotes().size());
        onView(withId(R.id.trash)).perform(click());
        onView(withId(R.id.trashRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
        onView(withId(R.id.trashRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeRight()));
        assertEquals(2, storage.getAllNotes().size());

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
