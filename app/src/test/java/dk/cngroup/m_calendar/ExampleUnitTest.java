package dk.cngroup.m_calendar;

import android.content.Context;
import android.content.pm.PackageManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PackageManager.class})
public class ExampleUnitTest {

    private Context mContext0;
    private Context mContext1;
    private Context mContext2;
    private  PackageManager pack;
    @Before
    public void init(){
        mContext0 = Mockito.mock(Context.class);
        mContext1 = mock(Context.class);
        mContext2 = PowerMockito.mock(Context.class);
        pack = PowerMockito.mock(PackageManager.class);
    }
    @Test
    public void textMock(){
        assertNotNull(mContext0);
        assertNotNull(mContext1);
        assertNotNull(mContext2);
        assertNotNull(pack);
    }
}