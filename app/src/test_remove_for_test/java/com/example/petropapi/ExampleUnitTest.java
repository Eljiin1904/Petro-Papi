package com.example.petropapi;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test_remove_for_test.
        // The new instrumentation registry class:
        android.content.Context appContext =
                InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.petropapi", appContext.getPackageName());
    }
}
