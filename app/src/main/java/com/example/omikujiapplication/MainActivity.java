package com.example.omikujiapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity {

    SensorManager sm;//センサーマネージャー
    Sensor accele;//加速度センサー
    ImageView img;//画像表示用
    int stat = 0;//現在のステイタス

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //センサーマネージャーの取得
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //加速度センサーの取得
        accele = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //ImageViewの取得
        img = (ImageView) findViewById(R.id.imageView01);

    }

    //リスナークラスの定義と生成
    private SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //X方向の加速速度を取得
            int x = (int) event.values[0];
            if (0 <= x && x < 2 && stat != 0) {
                stat = 0;
                img.setImageResource(R.drawable.omikuji1);

            } else if (2 <= x && x < 4 && stat != 1) {
                stat = 1;
                img.setImageResource(R.drawable.omikuji2);
            } else if (4 <= x && x < 6 && stat != 2) {
                stat = 2;
                img.setImageResource(R.drawable.omikuji3);
            } else if (6 <= x && x < 9 && stat != 3) {
                stat = 3;
                img.setImageResource(R.drawable.omikuji4);
            } else if (x == 9 && stat == 3) {
                stat = 4;
                Intent intent = new Intent(MainActivity.this, KujiActivity.class);
                startActivity(intent);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        sm.registerListener(sel, accele, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sm.unregisterListener(sel);
    }

}
