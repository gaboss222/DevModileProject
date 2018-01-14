package hearc.ch.roleplay;

/**
 * Created by axel.bentodas on 15.12.2017.
 */

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;

import java.util.ArrayList;

import static android.content.Context.SENSOR_SERVICE;


public class Accelerometer implements SensorEventListener {

    private final SensorManager mSensorManager;
    private final Sensor mAccelerometer;
    public ArrayList<Double> lAcceleration;

    public Accelerometer(SensorManager sm){
        mSensorManager = sm;
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    protected void onResume() {
        lAcceleration = new ArrayList<>();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {
        float x = 0.0f;
        float z = 0.0f;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            x = event.values[0];
            z = event.values[2];

            lAcceleration.add(Math.sqrt(Math.pow(x,2)+Math.pow(z,2)));
        }
    }

}
