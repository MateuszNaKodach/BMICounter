package pl.edu.pwr.mateusznowak.lab1.swim_lab1;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import pl.edu.pwr.mateusznowak.lab1.swim_lab1.ui.BmiCounterActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Mateusz on 31.03.2017.
 */
//http://www.vogella.com/tutorials/AndroidTestingEspresso/article.html
@RunWith(AndroidJUnit4.class)
public class BmiCounterActivityTest {

    @Rule
    public ActivityTestRule<BmiCounterActivity> bmiCounterActivityActivityTestRule =
            new ActivityTestRule<>(BmiCounterActivity.class);

    @Test
    public void ensureBmiConditionCorrectMessageWorks(){
        onView(withId(R.id.editText_Height)).perform(typeText("1.77"));
        onView(withId(R.id.editText_Mass)).perform(typeText("69"));
        onView(withId(R.id.button_countBmi)).perform(click());
        onView(withId(R.id.textView_BmiCondition)).check(matches(withText(R.string.correct)));
    }

    @Test
    public void ensureBmiConditionOverweightMessageWorks(){
        onView(withId(R.id.editText_Height)).perform(typeText("1.77"));
        onView(withId(R.id.editText_Mass)).perform(typeText("140"));
        onView(withId(R.id.button_countBmi)).perform(click());
        onView(withId(R.id.textView_BmiCondition)).check(matches(withText(R.string.overweight)));
    }
}

