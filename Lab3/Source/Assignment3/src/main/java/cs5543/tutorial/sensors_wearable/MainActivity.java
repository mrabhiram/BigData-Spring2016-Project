package cs5543.tutorial.sensors_wearable;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

//    private TextView mTextView;
    SensorManager sensorManager;
    Sensor mStepCounter, mHeartActivity;
    TextView heart,step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_main);
/*
       final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
*/

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mStepCounter=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        //mHeartActivity=sensorManager.getDefaultSensor(65538);
        mHeartActivity=sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        sensorManager.registerListener(this,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,mHeartActivity,SensorManager.SENSOR_DELAY_NORMAL);

        heart=(TextView)findViewById(R.id.heart);
        step=(TextView)findViewById(R.id.steps);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getName()==mStepCounter.getName())
            step.setText(""+event.values[0]);
        if (event.sensor==mHeartActivity)
            heart.setText(""+event.values[0]);

        Log.d("Sensor",event.sensor+""+event.values[0]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, mStepCounter , SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mHeartActivity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
