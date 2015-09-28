package com.gowarrior.demoofgpio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by GoWarrior on 2015/8/28.
 */
public class TestPlatform extends Activity {

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        Intent intent = new Intent(TestPlatform.this, TestForGPIO.class);
        startService(intent);
    }

}

