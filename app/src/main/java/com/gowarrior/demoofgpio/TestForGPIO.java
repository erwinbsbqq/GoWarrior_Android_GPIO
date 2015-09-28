package com.gowarrior.demoofgpio;

import android.app.Service;
import android.content.Intent;
import android.gowarrior.GPIO;
import android.os.IBinder;

/**
 * Created by GoWarrior on 2015/8/28.
 */


public class TestForGPIO extends Service{

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        testGPIO();
    }

    private void testGPIO()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GPIO gpio = new GPIO();
                int flag = 0;
                boolean left = false;
                boolean right = false;
                int [] gpioPort = { 84, 83, 82, 76, 78, 80, 81, 79 };

                gpio.setmode(GPIO.BCM);
                for(int i = 0; i < 8; i++)
                {
                    gpio.setup(gpioPort[ i ], GPIO.OUTPUT);
                }
                gpio.setup( 10, GPIO.INPUT);
                gpio.setup( 11, GPIO.INPUT);

                while (true){

                    if(gpio.input(10) == 1 && gpio.input(11) == 0){
                        left = true;
                        right = false;
                    }
                    else if (gpio.input(10) == 0 && gpio.input(11) == 1){
                        left = false;
                        right = true;
                    }
                    else if (gpio.input(10) == gpio.input(11)){
                        try {
                            left = false;
                            right = false;
                            for(int i = 0; i < 8; i++)
                            {
                                gpio.setup(gpioPort[ i ], GPIO.OUTPUT);
                            }
                            flag = 0;
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    while (left){
                        try {
                            for(int i = 0; i < 8; i++)
                            {
                                gpio.output(gpioPort[ i ], (flag + i) % 8);
                            }
                            flag = flag % 8 + 1;
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(gpio.input(10) == 0){
                            left = false;
                            right = true;
                            flag = 9 - (flag % 8);
                        }
                    }

                    while (right){
                        try {
                            for(int i = 8; i > 0; i--)
                            {
                                gpio.output(gpioPort[ i - 1 ], (flag + 8 - i) % 8);
                            }
                            flag = flag % 8 + 1;
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(gpio.input(11) == 0){
                            left = true;
                            right = false;
                            flag = 9 - (flag % 8);
                        }
                    }
                }
            }
        });
    }
}
