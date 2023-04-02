package uk.ac.tees.aad.obesity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.ekn.gruzer.gaugelibrary.HalfGauge;

public class BMITracker extends AppCompatActivity {


    HalfGauge guage;
    long bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmitracker);

        guage = findViewById(R.id.guage);
        SharedPreferences sh = getSharedPreferences("Prefs",MODE_PRIVATE);
        long  bmr = sh.getLong("bmr",0);
        float activity = sh.getFloat("ActivityFactor",0f);

        bmi = (long) (bmr*activity);
        guage.setMaxValue(3500);
        guage.setMinValue(0);
        guage.setValue(bmi);

        Toast.makeText(BMITracker.this, String.valueOf(activity), Toast.LENGTH_SHORT).show();

    }
}