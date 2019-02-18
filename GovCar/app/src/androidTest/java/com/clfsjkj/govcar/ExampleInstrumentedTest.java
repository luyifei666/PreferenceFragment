package com.clfsjkj.govcar;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 * 必须在gradle里加 androidTestImplementation 'com.android.support.test:rules:1.0.2' // JUnit4 Rules
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.clfsjkj.govcar", appContext.getPackageName());

        // 执行操作
        onView(withId(R.id.et_mobile)).perform(replaceText("username"));
        onView(withId(R.id.et_password)).perform(replaceText("password"));
        onView(withId(R.id.et_password)).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.btn_login)).perform(click());

    }
}
