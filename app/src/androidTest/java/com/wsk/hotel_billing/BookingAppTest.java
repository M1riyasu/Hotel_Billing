package com.wsk.hotel_billing;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.CoreMatchers.allOf;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicInteger;

@RunWith(AndroidJUnit4.class)
public class BookingAppTest {
    private static final String TAG = "MyActivity";
    private static final int time = 3000;
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testHomePage() {
        HomeFragment fragment = new HomeFragment();
        activityRule.getScenario().onActivity(activity -> {
            activity.setFragment(fragment);
        });
        onView(withId(R.id.fragment_home)).check(matches(isDisplayed()));
        onView(withId(R.id.RV)).check(matches(isDisplayed()));

        onView(withId(R.id.RV)).perform(RecyclerViewActions.scrollToPosition(0));

        onView(withId(R.id.RV)).check(matches(hasMinimumChildCount(1)));
        AtomicInteger size = new AtomicInteger(0);
        Log.d(TAG, "Step No: 1, Application startup");

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        size.set(fragment.getItemsSize());
        onView(withId(R.id.RV)).perform(RecyclerViewActions.scrollToPosition(size.get() - 1));
        Log.d(TAG, "Step No: 2, Scroll to the bottom of the \n" +
                "hotel list");

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.search)).perform(replaceText("Méribel"), closeSoftKeyboard());
        Log.d(TAG, "Step No: 3, Perform a search after \n" +
                "entering the search term in \n" +
                "the search bar");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.RV)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView RV = (RecyclerView) item;
                RecyclerView.Adapter adapter = RV.getAdapter();
                return ((HomeAdapter) adapter).performClickByName("Appartement Méribel");
            }
        }));
        Log.d(TAG, "Step No: 4, Click the listing item \n" +
                "\"Appartement Méribel\"");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.reviewsButton)).perform(click());
        Log.d(TAG, "Step No: 5, Click the Guest reviews \n" +
                "tab button");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.reviews)).check(matches(isDisplayed()));

        onView(withId(R.id.reviews)).perform(RecyclerViewActions.scrollToPosition(0));

        onView(withId(R.id.reviews)).check(matches(hasMinimumChildCount(1)));
        onView(withId(R.id.fragment)).check(matches(new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(View item) {
                FragmentManager fragmentManager = ((FragmentActivity) item.getContext()).getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment);
                if (currentFragment instanceof DetailsFragment) {
                    size.set(((DetailsFragment) currentFragment).getItemsCountReviews());
                    return true;
                }
                return false;
            }
        }));
        onView(withId(R.id.reviews)).perform(RecyclerViewActions.scrollToPosition(size.get() - 1));
        Log.d(TAG, "Step No: 6, Scroll down the Reviews" +
                "list to the 4th Review" +
                        "(published by \"Miles\")");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.roomButton)).perform(click());
        Log.d(TAG, "Step No: 7, Click the Room selection \n" +
                "label button");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.rooms)).check(matches(isDisplayed()));

        onView(withId(R.id.rooms)).perform(RecyclerViewActions.scrollToPosition(0));

        onView(withId(R.id.rooms)).check(matches(hasMinimumChildCount(1)));
        onView(withId(R.id.fragment)).check(matches(new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(View item) {
                FragmentManager fragmentManager = ((FragmentActivity) item.getContext()).getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment);
                if (currentFragment instanceof DetailsFragment) {
                    size.set(((DetailsFragment) currentFragment).getItemsCountRooms());
                    return true;
                }
                return false;
            }
        }));
        onView(withId(R.id.rooms)).perform(RecyclerViewActions.scrollToPosition(size.get() - 1));
        Log.d(TAG, "Step No: 8, Scroll down the room list \n" +
                "to the \"Great Family \n" +
                "Room\" item");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.rooms)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView RV = (RecyclerView) item;
                RecyclerView.Adapter adapter = RV.getAdapter();
                return ((RoomsAdapter) adapter).performClickByName("Great Family Room");
            }
        }));
        Log.d(TAG, "Step No: 9, Click the \"Great Family \n" +
                "Room\" item ");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.firstName)).perform(replaceText("Taylor"), closeSoftKeyboard());
        Log.d(TAG, "Step No: 10, Enter First Name ");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.lastName)).perform(replaceText("Hutchinson"), closeSoftKeyboard());
        Log.d(TAG, "Step No: 11, Enter Last Name");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.inDate)).perform(replaceText("Oct 15 2024"), closeSoftKeyboard());
        Log.d(TAG, "Step No: 12, Enter check-in date");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.outDate)).perform(replaceText("2024/2/10"), closeSoftKeyboard());
        Log.d(TAG, "Step No: 13, Enter check-out date ");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.adults)).perform(replaceText("4"), closeSoftKeyboard());
        Log.d(TAG, "Step No: 14, Enter the number of adults");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.children)).perform(replaceText("3"), closeSoftKeyboard());
        Log.d(TAG, "Step No: 15, Enter the number of \n" +
                "children");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.fragment)).check(matches(new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(View item) {
                FragmentManager fragmentManager = ((FragmentActivity) item.getContext()).getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment);
                if (currentFragment instanceof DetailsFragment) {
                    ((DetailsFragment) currentFragment).performClick("E-Pay");
                    return true;
                }
                return false;
            }
        }));
        Log.d(TAG, "Step No: 16, Click the E-Pay radio \n" +
                "button");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.fragment)).check(matches(new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(View item) {
                FragmentManager fragmentManager = ((FragmentActivity) item.getContext()).getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment);
                if (currentFragment instanceof DetailsFragment) {
                    ((DetailsFragment) currentFragment).bookNow();
                    return true;
                }
                return false;
            }
        }));
        Log.d(TAG, "Step No: 17, Click the \"Book now\" \n" +
                "button ");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.outDate)).perform(replaceText("10-19-2024"), closeSoftKeyboard());
        Log.d(TAG, "Step No: 18, Change check-out date");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.fragment)).check(matches(new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(View item) {
                FragmentManager fragmentManager = ((FragmentActivity) item.getContext()).getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment);
                if (currentFragment instanceof DetailsFragment) {
                    ((DetailsFragment) currentFragment).bookNow();
                    return true;
                }
                return false;
            }
        }));
        Log.d(TAG, "Step No: 19, Click the \"Book now\" \n" +
                "button ");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Are you sure you want to book this hotel?"))
                .check(matches(isDisplayed()));
        onView(withText("Yes"))
                .perform(ViewActions.click());
        Log.d(TAG, "Step No: 20, Click \"Yes\" in the pop-up \n" +
                "window to confirm your \n" +
                "reservation.");
        while (true) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
