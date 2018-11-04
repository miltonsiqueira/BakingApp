package br.com.titomilton.bakingapp.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import br.com.titomilton.bakingapp.R;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

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

    @Test
    public void mainActivityTest() {
        ViewInteraction textView = onView(
                allOf(withText("Baking Time"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Baking Time")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.content), withText("Nutella Pie"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_list),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Nutella Pie")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.content), withText("Cheesecake"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_list),
                                        3),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Cheesecake")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.content), withText("Brownies"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_list),
                                        1),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Brownies")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.content), withText("Yellow Cake"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_list),
                                        2),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("Yellow Cake")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.content), withText("Yellow Cake"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_list),
                                        2),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("Yellow Cake")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipe_list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.tv_ingredients), withText("- Bittersweet chocolate (60-70% cacao) (350.0 G)\n- unsalted butter (226.0 G)\n- granulated sugar (300.0 G)\n- light brown sugar (100.0 G)\n- large eggs (5.0 UNIT)\n- vanilla extract (1.0 TBLSP)\n- all purpose flour (140.0 G)\n- cocoa powder (40.0 G)\n- salt (1.5 TSP)\n- semisweet chocolate chips (350.0 G)\n"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredients_steps),
                                        0),
                                0),
                        isDisplayed()));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.content), withText("Recipe Introduction"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView8.check(matches(withText("Recipe Introduction")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.item_number), withText("0"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView9.check(matches(withText("0")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.item_number), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView10.check(matches(withText("1")));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.content), withText("Starting prep"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView11.check(matches(withText("Starting prep")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.item_number), withText("2"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView12.check(matches(withText("2")));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.content), withText("Melt butter and bittersweet chocolate."),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView13.check(matches(withText("Melt butter and bittersweet chocolate.")));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.item_number), withText("3"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView14.check(matches(withText("3")));

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.content), withText("Add sugars to wet mixture."),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView15.check(matches(withText("Add sugars to wet mixture.")));

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.content), withText("Add sugars to wet mixture."),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView16.check(matches(withText("Add sugars to wet mixture.")));

        ViewInteraction textView17 = onView(
                allOf(withId(R.id.content), withText("Add sugars to wet mixture."),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView17.check(matches(withText("Add sugars to wet mixture.")));

        ViewInteraction textView18 = onView(
                allOf(withId(R.id.item_number), withText("4"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView18.check(matches(withText("4")));

        ViewInteraction textView19 = onView(
                allOf(withId(R.id.content), withText("Mix together dry ingredients."),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView19.check(matches(withText("Mix together dry ingredients.")));


    }
}
