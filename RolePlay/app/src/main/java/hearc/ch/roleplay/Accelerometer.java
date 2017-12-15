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


public class Accelerometer extends Activity implements SensorEventListener {
    private Sensor accelerometer;
    private SensorManager sensorManager;

    public ArrayList<Double> lAcceleration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this,accelerometer);
        super.onPause();
    }

    @Override
    protected void onResume() {
        lAcceleration.clear();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = 0.0f;
        float z = 0.0f;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            x = event.values[0];
            z = event.values[2];

            lAcceleration.add(Math.sqrt(Math.pow(x,2)+Math.pow(z,2)));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
