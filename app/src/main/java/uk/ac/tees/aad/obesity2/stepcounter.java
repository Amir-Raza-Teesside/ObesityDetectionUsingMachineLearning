package uk.ac.tees.aad.obesity2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class stepcounter extends AppCompatActivity implements SensorEventListener {

    TextView tvdetector,tvcountter;
    SensorManager sensorManager;
    private Sensor sensorstepcounter;
    private boolean IscounterSensoverAvailable;
    int stepcounter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepcounter);
        tvcountter = findViewById(R.id.tvstepcounter);
        tvdetector = findViewById(R.id.tvstepcounter);
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){ //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }




        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            sensorstepcounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            IscounterSensoverAvailable=true;
            Toast.makeText(stepcounter.this,"Available",Toast.LENGTH_LONG).show();

        }
        else
            {
                Toast.makeText(stepcounter.this,"Not Available",Toast.LENGTH_LONG).show();
                IscounterSensoverAvailable=true;
            }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor == sensorstepcounter)
        {
            stepcounter = (int)event.values[0];
            tvcountter.setText(String.valueOf(stepcounter));
            if(stepcounter >= 10)
            {
                stepcounter=0;
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            sensorManager.registerListener(this, sensorstepcounter,SensorManager.SENSOR_DELAY_NORMAL);

        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null)
        {
            sensorManager.unregisterListener(this, sensorstepcounter);

        }
    }
}