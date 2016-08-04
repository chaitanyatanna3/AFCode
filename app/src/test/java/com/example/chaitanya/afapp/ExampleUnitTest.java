package com.example.chaitanya.afapp;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import org.codehaus.plexus.context.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.annotation.Config;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExampleUnitTest {

    private MainActivity mainActivity;

    @Before
    public void setup() {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void displayImage() throws Exception {
        assertTrue(Robolectric.buildActivity(MainActivity.class).create().get() != null);
    }

    @Test
    public void checkActivityNotNull() throws  Exception {
        assertNotNull(mainActivity);
    }

    @Test
    public void textClickShouldStartNewActivity() throws Exception {
        TextView textView = (TextView) mainActivity.findViewById(R.id.textView);
        TextView textView1 = (TextView) mainActivity.findViewById(R.id.textView1);

        textView.performClick();
        textView1.performClick();
        Intent intent = (Intent) Robolectric.getShadowsAdapter();
        assertEquals(PromoCard.class.getCanonicalName(), intent.getComponent().getClassName());

    }

    @Test
    public void testTextClick() throws Exception {
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();

        TextView view = (TextView) mainActivity.findViewById(R.id.textView);
        TextView view1 = (TextView) mainActivity.findViewById(R.id.textView1);

        assertNotNull(view);
        assertNotNull(view1);
        view.performClick();
        view1.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("Running"));
    }
}