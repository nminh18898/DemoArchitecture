package com.nhatminh.example.architecture.demoarchitecture.robolectric;

import android.os.Build;

import com.nhatminh.example.architecture.demoarchitecture.search.view.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class MainActivityTest {

    MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create().resume().get();
    }

    @Test
    public void testActivityShouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }

    @After
    public void tearDown() throws Exception {

    }
}
