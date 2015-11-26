package dk.cngroup.m_calendar;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class ExampleUnitTest2 {

    @Test
    @Config(qualifiers = "pl")
    public void testRoboletric(){
        ShadowActivity shadow = shadowOf(Robolectric.buildActivity(MainActivity_.class).create().get());
        String strActual = shadow.getShadowApplication().getApplicationContext().getResources().getString(R.string.app_name);
     //   assertEquals("M-Calendar", strActual);
    }
}
